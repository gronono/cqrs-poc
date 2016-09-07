package poc.cqrs.command;

public interface CommandRegistry {

	/**
	 * Donne la classe de la commande à partir de l'identifiant de la commande.
	 * 
	 * @param id L'identifiant de la commande.
	 * 
	 * @return La classe de la commande
	 */
	Class<? extends Command> findCommandClass(String path);
}
