package poc.cqrs.command.impl;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;

import poc.cqrs.command.Command;
import poc.cqrs.command.CommandDispatcher;
import poc.cqrs.command.CommandHandler;

public class DefaultCommandDispatcher implements CommandDispatcher {
	
	private List<CommandHandler<?>> handlers;
	
	public DefaultCommandDispatcher(List<CommandHandler<?>> handlers) {
		this.handlers = Collections.unmodifiableList(handlers);
	}

	public <C extends Command> CommandHandler<C> dispatch(C command) throws NoHandlerFoundException {
		List<CommandHandler<?>> relevantHandlers = findRelevantHandlers(command);
		relevantHandlers = ensureOnlyOneHandler(relevantHandlers, command);
		
		@SuppressWarnings("unchecked")
		CommandHandler<C> handler = (CommandHandler<C>) relevantHandlers.get(0);
		return handler;
	}

	private List<CommandHandler<?>> ensureOnlyOneHandler(List<CommandHandler<?>> relevantHandlers, Command command) {
		if (relevantHandlers.isEmpty()) {
			throw new NoHandlerFoundException(format("Aucun handler ne peut traiter la commande %s", command));
		} else if (relevantHandlers.size() > 1) {
			throw new NoHandlerFoundException(format("Plusieurs handlers ne peuvent traiter la commande %s", command));
		}
		return relevantHandlers;
	}
	
	@SuppressWarnings("unchecked")
	private <C extends Command> List<CommandHandler<? extends Command>> findRelevantHandlers(C command) {
		return this.handlers.stream()
			// La méthode accept ne marche que pour le bon type de commande
			// et donc le compilateur n'aime pas qu'on appelle accept(? command) avec accept(C command)
			// en castant on revient à une méthode accept(Command command)
			// et la compilation passe avec un unchecked sur l'accept.s
			.map(handler -> CommandHandler.class.cast(handler))
			.filter(handler -> handler.accept(command))
			// si on en trouve plus de 1, on va planter
			// donc si on a 2, ça sert à rien de continuer
			.limit(2)
			.collect(toList());
	}
}
