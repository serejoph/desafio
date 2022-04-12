package pedro.serejo.desafio.exceptions;

public class TransactionsAlreadySavedException extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public TransactionsAlreadySavedException(String e) {
		super(e);
	}
	
}
