package poc.cqrs.command.impl;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import poc.cqrs.command.Command;
import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandController;
import poc.cqrs.command.CommandHandlerFor;
import poc.cqrs.command.CommandRegistry;
import poc.cqrs.command.InvalidCommandException;

/**
 * <p>
 * 	Controlleur générique gérant l'ensemble des commandes.
 * </p>
 * <p>
 * 	Le matching entre la commande et l'url est fait par rapport à L'annotation {@link CommandHandlerFor}
 * 	qui associe la commande à son chemin.
 * </p>
 * <p>
 * 	Une commande ne peut faire l'objet que d'une création et donc ce controlleur n'accepte que les méthodes {@link RequestMethod#POST}.
 * </p>
 */
@RestController
@RequestMapping(path = "/command", method = POST)
public class DefaultCommandController implements CommandController {

	private CommandRegistry commandRegistry;
	private CommandBus commandBus;

	public DefaultCommandController(CommandRegistry registry, CommandBus bus) {
		this.commandRegistry = registry;
		this.commandBus = bus;
	}

	@Override
	@RequestMapping(path = "/{commandPath}")
	public ResponseEntity<?> post(@PathVariable("commandPath") String commandPath, @RequestBody Map<String, ?> commandData) {
		Command command = createCommand(commandPath, commandData);
		try {
			commandBus.send(command);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (InvalidCommandException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	private Command createCommand(String commandPath, Map<String, ?> data) {
		Class<? extends Command> commandClass = this.commandRegistry.findCommandClass(commandPath);
		ObjectMapper mapper = new ObjectMapper();
		Command command = mapper.convertValue(data, commandClass);
		return command;
	}
}
