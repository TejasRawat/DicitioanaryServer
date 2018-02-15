package co.dictionary.exception;

/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class ServerException extends Exception {

	private static final long serialVersionUID = -6179336829990938205L;

	private int errorCode;
	private String errorMessage;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
