package poc.cqrs.event;

import java.time.Instant;
import java.util.UUID;

public class EventStoreEntry {

	private UUID id;
	private UUID aggregateId;
	private Instant created;
	private Object payload;
	
	public EventStoreEntry(UUID id, UUID aggregateId, Instant created, Object payload) {
		this.id = id;
		this.aggregateId = aggregateId;
		this.created = created;
		this.payload = payload;
	}
	
	public UUID getId() {
		return id;
	}
	
	public UUID getAggregateId() {
		return aggregateId;
	}
	
	public Instant getCreated() {
		return created;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getPayload() {
		return (T) payload;
	}

	
}
