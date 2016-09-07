package poc.cqrs.command;

import java.util.UUID;

/**
 * <p>
 * 	Effectue les actions décrites dans la {@link Command}.
 * </p>
 * <p>
 * 	Le choix du {@link CommandHandler} est délégué à une {@link CommandDispatcher}.
 * </p>
 */
public interface CommandBus {

	UUID send(Command command) throws InvalidCommandException;
}
