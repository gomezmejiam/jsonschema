package co.com.ingeniods.domanin.exception;

public abstract class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = -2038617445917758261L;
	private final String message;
	private final String code;
	private final String id;
	private static ExceptionIdGenerator idGenerator;
	
	public static void setIdGenerator(ExceptionIdGenerator idGenerator) {
		BaseException.idGenerator = idGenerator;
	}
	
	public BaseException(ExceptionCode exceptionCode) {
		this.code = exceptionCode.getCode();
		this.message = exceptionCode.getMessage();
		this.id = idGenerator.generateId();
	}
	
	public BaseException(ExceptionCode exceptionCode, Throwable cause) {
		super(cause);
		this.code = exceptionCode.getCode();
		this.message = exceptionCode.getMessage();
		this.id = idGenerator.generateId();
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}
	public String getId() {
		return id; 
	}
	
}
