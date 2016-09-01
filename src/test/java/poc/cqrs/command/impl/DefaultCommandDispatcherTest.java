package poc.cqrs.command.impl;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import poc.cqrs.command.CommandDispatcher;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.Mocks;

public class DefaultCommandDispatcherTest {

	@Test
	public void shouldThrowExceptionWhenNoHandler() {
		// On n'inclut pas le handler de la commande AnotherCommand
		CommandDispatcher dispatcher = new DefaultCommandDispatcher(asList(new Mocks.ACommandHandler()));

		// et on test qu'on a bien une exception si on demande le handler pour cette commande
		Assertions.assertThatThrownBy(
				() -> dispatcher.dispatch(new Mocks.AnotherCommand())
		).isInstanceOf(CommandDispatcher.NoHandlerFoundException.class);
	}
	
	@Test
	public void shouldThrowExceptionWhenMultipleHandler() {
		CommandHandler<Mocks.ACommand> aHandler1 = new Mocks.ACommandHandler();
		CommandHandler<Mocks.ACommand> aHandler2 = new Mocks.ACommandHandler();
		// On inclus deux handlers capables de traiter la command ACommand
		CommandDispatcher dispatcher = new DefaultCommandDispatcher(asList(aHandler1, aHandler2));

		// et on test qu'on a bien une exception lorsqu'on demande un handler sur ACommand
		Assertions.assertThatThrownBy(
				() -> dispatcher.dispatch(new Mocks.ACommand())
		).isInstanceOf(CommandDispatcher.NoHandlerFoundException.class);

	}
	
	@Test
	public void shouldReturnsTheHandler() {
		CommandHandler<Mocks.ACommand> aHandler = new Mocks.ACommandHandler();
		CommandHandler<Mocks.AnotherCommand> anotherHandler = new Mocks.AnotherCommandHandler();
		CommandDispatcher dispatcher = new DefaultCommandDispatcher(asList(aHandler, anotherHandler));

		CommandHandler<Mocks.ACommand> actualHandler = dispatcher.dispatch(new Mocks.ACommand());
		assertThat(actualHandler).isSameAs(aHandler);
	}
}
