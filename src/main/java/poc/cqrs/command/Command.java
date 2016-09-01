package poc.cqrs.command;

/**
 * <p>
 * 	Une commande est un objet décrivant les actions à entreprendre dans le système.
 * </p>
 * <p>
 * 	<em>Le nom de la classe d'une commande devrait toujours commencer par un verbe.</em>
 * </p>
 * <p>
 * 	La commande doit être transmise par le {@link CommandBus} 
 * 	qui choisira le {@link CommandHandler} pouvant traiter les actions. 
 * </p>
 * <p>
 * 	<em>Les implémentations devraient être immutable.</em>
 * </p>
 */
public interface Command {
}
