package co.com.ingeniods.domanin.exception;

public abstract class BusinessException extends BaseException {

	private static final long serialVersionUID = 8136684476363450693L;
	private final String[] info;

	public BusinessException(ExceptionCode exceptionCode, String[] info) {
		super(exceptionCode);
		this.info = info;
	}

	public String[] getInfo() {
		return info;
	}

}
