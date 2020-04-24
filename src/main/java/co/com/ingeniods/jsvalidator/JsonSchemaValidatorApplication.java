package co.com.ingeniods.jsvalidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ingeniods.domanin.exception.EntityValueValidationException;
import co.com.ingeniods.domanin.exception.ExceptionCode;
import co.com.ingeniods.domanin.exception.InvalidJsonDocumentException;
import co.com.ingeniods.domanin.exception.BaseException;

@SpringBootApplication
public class JsonSchemaValidatorApplication {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		BaseException.setIdGenerator(() -> "JSV-" + UUID.randomUUID().toString());
	}

	public static void main(String[] args) throws IOException {
		String personaStr = loadFile("persona.json");
		Object object = getObject(personaStr);
		String personaSchemaStr = loadFile("persona.schema.json");
		exceuteValidation(personaSchemaStr, object);
		System.out.println("");
		SpringApplication.run(JsonSchemaValidatorApplication.class, args);
	}

	private static Person getObject(String personStr) {
		try {
			return MAPPER.readValue(personStr, Person.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String loadFile(String fileName) throws IOException {
		ClassLoader classLoader = new JsonSchemaValidatorApplication().getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		InputStream inputStream = new FileInputStream(file);
		StringBuilder textBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(
				new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
		}
		return textBuilder.toString();
	}

	private static void exceuteValidation(String schemaName, Object object) throws JsonGenerationException, JsonMappingException, IOException {
		JSONObject jsonSchema = getJSONObject(schemaName);
		JSONObject jsonEntity = getJSONObject(MAPPER.writeValueAsString(object));

		SchemaLoader loader = SchemaLoader.builder().schemaJson(jsonSchema).draftV7Support().build();

		Schema schema = loader.load().build();

		try {
			schema.validate(jsonEntity);
		} catch (ValidationException e) {
			String[] message = e.getCausingExceptions().stream().map(ValidationException::getMessage)
					.toArray(String[]::new);
			if (message.length == 0) {
				message = new String[] { e.getMessage() };
			}

			for (String validationError : message) {
				System.out.println(validationError);
			}
			throw new EntityValueValidationException(ExceptionCode.BUSINESS_SCHEMA_VALIDATION_ERROR, message);
		}
	}

	private static JSONObject getJSONObject(String jsonString) {
		try {
			return new JSONObject(jsonString);
		} catch (JSONException e) {
			throw new InvalidJsonDocumentException(ExceptionCode.TECHNICAL_INVALID_JSON_DOCUMENT, e);
		}
	}

}
