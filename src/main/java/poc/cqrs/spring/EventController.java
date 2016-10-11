package poc.cqrs.spring;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import poc.cqrs.event.EventStore;
import poc.cqrs.event.EventStoreEntry;
import poc.cqrs.event.impl.EventStoreLookup;
import poc.cqrs.event.impl.EventStoreLookup.NoSuchStoreException;

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
@RequestMapping(path = "/events", method = GET)
public class EventController {

	private EventStoreLookup lookup;
	
	public EventController(Collection<EventStore> stores) {
		this.lookup = new EventStoreLookup(stores);
	}

	@RequestMapping(path = "/{aggregateType}/{aggregateId}")
	public ResponseEntity<?> get(@PathVariable String aggregateType, @PathVariable String aggregateId) {
		try {
			EventStore store = lookup.lookup(aggregateType);
			List<EventStoreEntry> events = store.read(UUID.fromString(aggregateId));
			
			if (events.isEmpty()) {
				return new ResponseEntity<>(
						String.format("L'aggregat %s/%s n'existe pas", aggregateType, aggregateId), 
						HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<>(events, HttpStatus.OK);
		} catch (NoSuchStoreException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
