package poc.cqrs.command;

/**
 * <p>
 * 	Choisit le {@link CommandHandler} permettant de traiter une {@link Command}.
 * </p>
 * <p>
 * 	<em>Les implémentations doivent s'appuyer sur la méthode {@link CommandHandler#accept(Command)} pour savoir si un handler peut traiter la commande.</em>
 * </p>
 */
@FunctionalInterface
public interface CommandDispatcher {

	static class NoHandlerFoundException extends RuntimeException {

		public NoHandlerFoundException() {
			super();
		}

		public NoHandlerFoundException(String message, Throwable cause) {
			super(message, cause);
		}

		public NoHandlerFoundException(String message) {
			super(message);
		}

		public NoHandlerFoundException(Throwable cause) {
			super(cause);
		}
	}

	/**
	 * Trouve le "bon" {@link CommandHandler} permettant de traiter la commande.
	 * 
	 * @param command La commande pour laquelle on souhaite trouver le {@link CommandHandler}.
	 * 
	 * @return Le {@link CommandHandler} trouvé.
	 * 
	 * @throws NoHandlerFoundException Si le système ne permet pas de traiter la commande.
	 */
	<C extends Command> CommandHandler<C> dispatch(C command) throws NoHandlerFoundException;
}
