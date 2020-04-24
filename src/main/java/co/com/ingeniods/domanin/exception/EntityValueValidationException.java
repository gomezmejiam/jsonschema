package co.com.ingeniods.domanin.exception;

public class EntityValueValidationException extends BusinessException {

	private static final long serialVersionUID = 355913957420778517L;

	public EntityValueValidationException(ExceptionCode code, String [] info) {
		super(code, info);
	}

}
