package co.com.ingeniods.domanin.exception;

public enum ExceptionCode {
	TECHNICAL_NO_CONTROLLED("JSV-000", "UNCONTROLLED_ERROR"),
	TECHNICAL_NO_DOCUMENT("JSV-001", "TECHNICAL_NO_DOCUMENT"), 
	BUSINESS_SCHEMA_VALIDATION_ERROR("JSV-002", "SCHEMA_VALIDATION_ERROR"), 
	TECHNICAL_INVALID_JSON_DOCUMENT("JSV-003", "INVALID_JSON_DOCUMENT");

		  private String code;
		  private String message;

		  private ExceptionCode(String code, String message) {
		    this.code = code;
		    this.message = message;
		  }

		  public String getCode() {
		    return code;
		  }

		  public String getMessage() {
		    return message;
		  }
}
