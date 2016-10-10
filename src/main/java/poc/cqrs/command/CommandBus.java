package poc.cqrs.command;

/**
 * <p>
 * 	Effectue les actions décrites par la commande
 * </p>
 */
@FunctionalInterface
public interface CommandBus {

	Aggregate send(Object command) throws InvalidCommandException;
}
