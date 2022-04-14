package pedro.serejo.desafio.exceptions;

public class TransactionValidationException extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public TransactionValidationException(String e) {
		super(e);
	}
	
}
