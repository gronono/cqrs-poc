package poc.cqrs.command.impl;

import java.util.UUID;

import poc.cqrs.command.Command;
import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandDispatcher;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.InvalidCommandException;

/**
 * Implémenation de {@link CommandBus} déléguant 
 * le choix du {@link CommandHandler} à un {@link CommandDispatcher}.
 */
public class DefaultCommandBus implements CommandBus {

	private CommandDispatcher dispatcher;

	/**
	 * Construit le {@link CommandBus}.
	 * 
	 * @param dispatcher Le dispatcher permettant de choisir le {@link CommandHandler} traitant la commande.
	 */
	public DefaultCommandBus(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public UUID send(Command command) throws InvalidCommandException {
		CommandHandler<Command> handler = dispatcher.dispatch(command);
		return handler.handle(command);
	}
}
