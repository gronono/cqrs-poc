package poc.cqrs.command;

import static java.lang.String.format;

/**
 * <p>
 * 	Un CommandHandler permet de traiter une {@link Command}
 * 	en effectuant les actions représentées par celle-ci.
 * </p>
 * <p>
 * 	Les implémentations doivent 
 * 	soit être annotées par {@link HandlerFor},
 * 	soit redéfinir la méthode {@link #accept(Command)}.
 * 
 * @param <C> Le type de la commande.
 */
@FunctionalInterface
public interface CommandHandler<C extends Command> {

	/**
	 * Levée lorsqu'un handler ne possède pas l'annotation HandlerFor.
	 */
	static class MissingHandlerForAnnotationException extends RuntimeException {
		public MissingHandlerForAnnotationException(String message) {
			super(message);
		}
	}
	
	/**
	 * <p>
	 * 	Traite la commande.
	 * </p>
	 * <p>
	 * 	Si la commande ne peut pas être traitée à cause d'une contrainte métier, 
	 * 	alors une {@link InvalidCommandException} sera déclenchée.
	 *	Les exceptions techniques doivent être remontées sous la forme de RuntimeException.  	 
	 * </p>
	 * 
	 * @param command La commande à traiter. Non null.
	 * @throws InvalidCommandException Si la commande ne peut pas être traitée.
	 */
	void handle(C command) throws InvalidCommandException;
	
	/**
	 * <p>
	 * 	Indique si ce handler peut traiter la commande spéicifiée.
	 * </p>
	 *  
	 * @param command La commande à tester. Non null.
	 * 
	 * @return <true> si ce handler peut traiter la commande, <code>false</code> sinon.
	 * 
	 * @throws MissingHandlerForAnnotationException Si ce handler ne possède pas l'annotation {@link HandlerFor}.
	 */
	default boolean accept(C command) {
		HandlerFor handlerFor = this.getClass().getAnnotation(HandlerFor.class);
		if (handlerFor == null) {
			throw new MissingHandlerForAnnotationException(format("L'annotation %s est absente sur la classe %s", HandlerFor.class.getSimpleName(), this.getClass().getName())); 
		}
		return handlerFor.value().isAssignableFrom(command.getClass());
	}
}
