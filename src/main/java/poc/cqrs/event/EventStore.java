package poc.cqrs.event;

import java.util.List;
import java.util.UUID;

public interface EventStore {

	String applyOn();
	
	void save(UUID aggregateId, Object event);
	
	List<Object> read(UUID aggregateId);
}
