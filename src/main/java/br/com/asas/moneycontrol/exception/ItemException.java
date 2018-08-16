package br.com.asas.moneycontrol.exception;

public class ItemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3054605195888782613L;
	
	public ItemException() {}
	
	public ItemException(String message) {
		super(message);
	}
	
	public ItemException(Throwable cause) {
		super(cause);
	}
	
	public ItemException(String message, Throwable cause) {
		super(message, cause);
	}
}
