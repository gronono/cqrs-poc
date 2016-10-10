package poc.cqrs.command;

import java.util.Map;

@FunctionalInterface
public interface CommandRegistry {
	
	/**
	 * @return L'ensemble des commandes connues du système. Non null, non modifiable.
	 */
	public Map<String, Class<?>> getCommands();
}
