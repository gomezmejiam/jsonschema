package co.com.ingeniods.domanin.exception;

public abstract class TechnicalException extends BaseException {
	
	private static final long serialVersionUID = 2217141469439129942L;
	
	public TechnicalException(ExceptionCode exceptionCode, Throwable cause) {
		super(exceptionCode, cause);
	}
	
}
