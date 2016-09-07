package poc.cqrs.command;

import java.util.UUID;

public class Mocks {
	public static class ACommand implements Command {
	}
	
	@CommandHandlerFor(command = ACommand.class, path = "/a-command")
	public static class ACommandHandler implements CommandHandler<ACommand> {
		@Override
		public UUID handle(ACommand command) throws InvalidCommandException {
			return null;
		}
	}

	public static class AnotherCommand implements Command {
	}
	
	@CommandHandlerFor(command = AnotherCommand.class, path = "/another-command")
	public static class AnotherCommandHandler implements CommandHandler<AnotherCommand> {
		@Override
		public UUID handle(AnotherCommand command) throws InvalidCommandException {
			return null;
		}
	}
}
