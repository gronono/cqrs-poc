package poc.cqrs.command;

import java.util.UUID;

/**
 * <p>
 * 	Un CommandHandler permet de traiter une {@link Command}
 * 	en effectuant les actions représentées par celle-ci.
 * </p>
 * <em>Les implémentations doivent être annotées par {@link CommandHandlerFor}</em>.
 * 
 * @param <C> Le type de la commande.
 */
@FunctionalInterface
public interface CommandHandler<C extends Command> {
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
	 * @return L'identifiant de l'aggregat mis à jour. Non null.
	 * 
	 * @throws InvalidCommandException Si la commande ne peut pas être traitée.
	 */
	UUID handle(C command) throws InvalidCommandException;
}
