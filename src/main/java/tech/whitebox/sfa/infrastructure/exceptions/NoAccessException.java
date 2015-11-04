/**
 * 
 */
package tech.whitebox.sfa.infrastructure.exceptions;

/**
 * @author razvan.reff
 *
 */
public class NoAccessException extends RuntimeException {

	/**
	 * @param message
	 */
	public NoAccessException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoAccessException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoAccessException(String message, Throwable cause) {
		super(message, cause);		
	}

}
