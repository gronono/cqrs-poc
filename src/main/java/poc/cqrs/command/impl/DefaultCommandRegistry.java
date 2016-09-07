package poc.cqrs.command.impl;

import java.util.Collections;
import java.util.List;

import poc.cqrs.command.Command;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.CommandHandlerFor;
import poc.cqrs.command.CommandRegistry;

public class DefaultCommandRegistry implements CommandRegistry {

	private List<CommandHandler<?>> handlers;
	
	public DefaultCommandRegistry(List<CommandHandler<?>> handlers) {
		this.handlers = Collections.unmodifiableList(handlers);
	}

	@Override
	public Class<? extends Command> findCommandClass(String id) {
		return this.handlers.stream()
			.map(handler -> handler.getClass())
			.map(handlerClass -> handlerClass.getAnnotation(CommandHandlerFor.class))
			.filter(handlerFor -> handlerFor.path().equals(id))
			.findFirst()
			.get()
			.command();
	}
}
