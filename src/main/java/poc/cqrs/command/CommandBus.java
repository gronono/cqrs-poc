package poc.cqrs.command;

/**
 * <p>
 * 	Effectue les actions décrites dans la {@link Command}.
 * </p>
 * <p>
 * 	Le choix du {@link CommandHandler} est délégué à une {@link CommandDispatcher}.
 * </p>
 */
@FunctionalInterface
public interface CommandBus {

	void send(Command command) throws InvalidCommandException;
}
