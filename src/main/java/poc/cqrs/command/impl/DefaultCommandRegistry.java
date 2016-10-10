package poc.cqrs.command.impl;

import java.util.Collections;
import java.util.Map;

import poc.cqrs.command.CommandRegistry;

/**
 * Implémenetation de {@link CommandRegistry}.
 */
public class DefaultCommandRegistry implements CommandRegistry {

	private Map<String, Class<?>> commands;

	/**
	 * @param commands L'ensemble des commandes connues du système. Non null.
	 */
	public DefaultCommandRegistry(Map<String, Class<?>> commands) {
		this.commands = Collections.unmodifiableMap(commands);
	}
	
	@Override
	public Map<String, Class<?>> getCommands() {
		return commands;
	}
}
