package poc.cqrs.command;

public class Mocks {
	public static class ACommand implements Command {
	}
	
	@HandlerFor(ACommand.class)
	public static class ACommandHandler implements CommandHandler<ACommand> {
		@Override
		public void handle(ACommand command) throws InvalidCommandException {
		}
	}

	public static class AnotherCommand implements Command {
	}
	
	@HandlerFor(AnotherCommand.class)
	public static class AnotherCommandHandler implements CommandHandler<AnotherCommand> {
		@Override
		public void handle(AnotherCommand command) throws InvalidCommandException {
		}
	}
}
