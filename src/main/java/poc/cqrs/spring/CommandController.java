package poc.cqrs.spring;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import poc.cqrs.command.Aggregate;
import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandRegistry;
import poc.cqrs.command.InvalidCommandException;

/**
 * <p>
 * 	Controlleur générique gérant l'ensemble des commandes.
 * 	Le matching entre la commande et l'url est fait par rapport au nom de la command {@link Command#name()}.
 * </p>
 * <p>
 * 	Une commande ne peut faire l'objet que d'une création et donc ce controlleur n'accepte que les méthodes {@link RequestMethod#POST}.
 * </p>
 */
@RestController
@RequestMapping(path = "/commands", method = POST)
public class CommandController {

	private CommandRegistry registry;
	private CommandBus bus;

	public CommandController(CommandRegistry registry, CommandBus bus) {
		this.registry = registry;
		this.bus = bus;
	}

	@RequestMapping(path = "/{commandName}")
	public ResponseEntity<?> post(@PathVariable String commandName, @RequestBody Map<String, ?> commandData) {
		Object command = createCommand(commandName, commandData);
		try {
			Aggregate aggregate = bus.send(command);
			URI location = UriComponentsBuilder
				.fromPath("/events/{aggregateType}/{aggregateId}")
				.buildAndExpand(aggregate.getType(), aggregate.getId().toString())
				.toUri();
			return ResponseEntity.created(location).build();
			
		} catch (InvalidCommandException e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

	private Object createCommand(String commandName, Map<String, ?> data) {
		Class<?> commandClass = registry.getCommands().get(commandName);
		ObjectMapper mapper = new ObjectMapper();
		Object command = mapper.convertValue(data, commandClass);
		return command;
	}
}
