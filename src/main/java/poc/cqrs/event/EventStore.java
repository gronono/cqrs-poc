package poc.cqrs.event;

import java.util.List;
import java.util.UUID;

public interface EventStore {

	String applyOn();
	
	void save(EventStoreEntry entry);
	
	List<EventStoreEntry> read(UUID aggregateId);
}
