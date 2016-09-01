package poc.cqrs.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import poc.cqrs.command.CommandHandler.MissingHandlerForAnnotationException;

public class CommandHandlerTest {

	@Test
	public void shouldThrowsExceptionWhenMissingHandlerFor() {
		// Quand un handler n'a pas l'annotation @HandlerFor
		CommandHandler<Command> handler = new CommandHandler<Command>() {
			@Override
			public void handle(Command command) throws InvalidCommandException {
			}
		};
		// on devrait lever une exception
		assertThatThrownBy(() -> handler.accept(mock(Command.class)))
					.isInstanceOf(MissingHandlerForAnnotationException.class);
	}

	@Test
	public void shouldAccept() {
		// Quand un handler a l'annotation @HandlerFor avec la bonne classe
		CommandHandler<Mocks.ACommand> handler = new Mocks.ACommandHandler();
		
		// on doit l'accepter
		assertThat(handler.accept(new Mocks.ACommand())).isTrue();
	}
	
	@Test
	// Les commandes handlers étant génériques, il n'est pas possible de
	// paramétrer le CommandHandler et d'appeler la méthode accept avec autre chose.
	// On passe donc par du rawtypes et du coup on a des warning de unchecked
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void shouldReject() {
		// Quand un handler a l'annotation @HandlerFor avec la mauvaise classe
		CommandHandler handler = new Mocks.ACommandHandler();
		
		// on doit le rejeter
		assertThat(handler.accept(new Mocks.AnotherCommand())).isFalse();
	}
}
