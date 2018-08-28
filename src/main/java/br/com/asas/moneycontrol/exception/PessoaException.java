package br.com.asas.moneycontrol.exception;

public class PessoaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5081556107534447214L;
	
	public PessoaException() {}
	
	public PessoaException(String message) {
		super(message);
	}
	
	public PessoaException(Throwable cause) {
		super(cause);
	}

	public PessoaException(String message, Throwable cause) {
		super(message, cause);
	}
}
