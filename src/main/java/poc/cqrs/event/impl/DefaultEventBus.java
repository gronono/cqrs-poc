package poc.cqrs.event.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.reflect.MethodUtils;

import poc.cqrs.command.Aggregate;
import poc.cqrs.event.EventBus;
import poc.cqrs.event.EventListener;
import poc.cqrs.event.EventStore;

public class DefaultEventBus implements EventBus {

	private Collection<EventListener<?>> listeners;

	private EventStoreLookup lookup;
	
	public DefaultEventBus(Collection<EventStore> stores, Collection<EventListener<?>> listeners) {
		this.listeners = Collections.unmodifiableCollection(listeners);
		this.lookup = new EventStoreLookup(stores);
	}
	
	@Override
	public void apply(Aggregate aggregate, Object event) {
		saveInStore(aggregate, event);
		notifyListeners(event);
	}
	
	private void notifyListeners(Object event) {
		this.listeners.stream()
			.filter(listener -> needNotity(listener, event))
			.forEach(listener -> invoke(listener, event));
	}

	private void invoke(EventListener<?> listener, Object event) {
		@SuppressWarnings("unchecked")
		EventListener<Object> generic = (EventListener<Object>) listener;
		generic.onEvent(event);
	}
	
	private boolean needNotity(EventListener<?> listener, Object event) {
		Method handleMethod = MethodUtils.getAccessibleMethod(listener.getClass(), EventListener.ON_EVENT_METHOD_NAME, event.getClass());
		return handleMethod != null;
	}
	
	private void saveInStore(Aggregate aggregate, Object event) {
		EventStore store = lookup.lookup(aggregate.getType());
		store.save(aggregate.getId(), event);
	}
}
