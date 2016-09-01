package poc.cqrs.command;

/**
 * Déclenchée lorsqu'une commande est invalide. 
 * C'est à dire que le {@link CommandHandler} associé n'a pas pu/voulu la traiter. 
 */
public class InvalidCommandException extends Exception {

	public InvalidCommandException() {
		super();
	}

	public InvalidCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCommandException(String message) {
		super(message);
	}

	public InvalidCommandException(Throwable cause) {
		super(cause);
	}
	
}
