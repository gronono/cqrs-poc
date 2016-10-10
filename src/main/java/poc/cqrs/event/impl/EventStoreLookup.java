package poc.cqrs.event.impl;

import java.util.Collection;

import poc.cqrs.event.EventStore;

public class EventStoreLookup {

	public static class NoSuchStoreException extends RuntimeException {
		public NoSuchStoreException(String message) {
			super(message);
		}
	}
	
	private Collection<EventStore> stores;
	
	public EventStoreLookup(Collection<EventStore> stores) {
		this.stores = stores;
	}
	
	public EventStore lookup(String aggregateType) {
		for (EventStore store : stores) {
			if (store.applyOn().equals(aggregateType)) {
				return store;
			}
		}
		throw new NoSuchStoreException("Aucun store ne peut traiter " + aggregateType);
	}
}
