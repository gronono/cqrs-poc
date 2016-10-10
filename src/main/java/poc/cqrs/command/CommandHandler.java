package poc.cqrs.command;

/**
 * <p>
 * 	Un CommandHandler permet de traiter une commande
 * 	en effectuant les actions représentées par celle-ci.
 * </p>
 * 
 * @param <C> Le type de la commande.
 */
@FunctionalInterface
public interface CommandHandler<C> {
	static String HANDLE_METHOD_NAME = "handle";
	
	/**
	 * <p>
	 * 	Traite la commande.
	 * </p>
	 * <p>
	 * 	Si la commande ne peut pas être traitée à cause d'une contrainte métier, 
	 * 	alors une {@link InvalidCommandException} doit être déclenchée.
	 *	Les exceptions techniques doivent être remontées sous la forme de RuntimeException.  	 
	 * </p>
	 * 
	 * @param command La commande à traiter. Non null.
	 * @return L'aggregat mis à jour. Non null.
	 * 
	 * @throws InvalidCommandException Si la commande ne peut pas être traitée.
	 */
	Aggregate handle(C command) throws InvalidCommandException;
}
