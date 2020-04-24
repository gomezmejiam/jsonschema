package co.com.ingeniods.domanin.exception;

public class InvalidJsonDocumentException extends TechnicalException {

	private static final long serialVersionUID = -3377082330602754557L;

	public InvalidJsonDocumentException(ExceptionCode ec, Throwable cause) {
		super(ec, cause);
	}

}
