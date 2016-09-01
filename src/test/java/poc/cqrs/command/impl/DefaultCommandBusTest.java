package poc.cqrs.command.impl;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import poc.cqrs.command.Command;
import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandDispatcher;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.InvalidCommandException;

public class DefaultCommandBusTest {

	@Test
	public void shouldHandle() throws InvalidCommandException {
		// Quand cette commande sera envoyée dans le bus,
		Command command = mock(Command.class);
		// elle sera pris en charge ce handler
		@SuppressWarnings("unchecked")
		CommandHandler<Command> handler = mock(CommandHandler.class);
		// c'est le dispatcher qui dit quel handler sera appelé
		CommandDispatcher dispatcher = mock(CommandDispatcher.class);
		when(dispatcher.dispatch(command)).thenReturn(handler);
		
		// Envoi de la commande dans le bus
		CommandBus bus = new DefaultCommandBus(dispatcher);
		bus.send(command);
		
		// Et on vérifie que le handler a été appelé
		verify(handler, times(1)).handle(command);
	}
	
	@Test
	public void shouldPropagateExceptionWhenHandlerThrowsIt() throws InvalidCommandException {
		// Quand cette commande sera envoyée dans le bus,
		Command command = mock(Command.class);
		// elle sera pris en charge ce handler
		@SuppressWarnings("unchecked")
		CommandHandler<Command> handler = mock(CommandHandler.class);
		// qui levra une InvalidCommandException
		InvalidCommandException exception = new InvalidCommandException();
		doThrow(exception).when(handler).handle(command);
		// c'est le dispatcher qui dit quel handler sera appelé
		CommandDispatcher dispatcher = mock(CommandDispatcher.class);
		when(dispatcher.dispatch(command)).thenReturn(handler);
		
		
		// Envoi de la commande dans le bus
		CommandBus bus = new DefaultCommandBus(dispatcher);
		Assertions.assertThatThrownBy(() -> bus.send(command))
				.isEqualTo(exception);
	}

}
