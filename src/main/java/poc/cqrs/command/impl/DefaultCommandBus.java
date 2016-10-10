package poc.cqrs.command.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.reflect.MethodUtils;

import poc.cqrs.command.Aggregate;
import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.InvalidCommandException;

public class DefaultCommandBus implements CommandBus {

	private Collection<CommandHandler<?>> handlers;
	
	public DefaultCommandBus(Collection<CommandHandler<?>> handlers) {
		this.handlers = Collections.unmodifiableCollection(handlers);
	}
	
	@Override
	public Aggregate send(Object command) throws InvalidCommandException {
		return dispatch(command).handle(command);
	}
	
	private <C> CommandHandler<C> dispatch(Object command) {
		for (CommandHandler<?> handler : handlers) {
			Method handleMethod = MethodUtils.getAccessibleMethod(handler.getClass(), CommandHandler.HANDLE_METHOD_NAME, command.getClass());
			if (handleMethod != null) {
				@SuppressWarnings("unchecked")
				CommandHandler<C> result = (CommandHandler<C>) handler;
				return result;
			}
		}
		throw new IllegalStateException("Aucun handler ne peut traiter " + command);
	}
}
