package poc.cqrs.event;

@FunctionalInterface
public interface EventListener<E> {

	static String ON_EVENT_METHOD_NAME = "onEvent";
	
	public void onEvent(E event);
}
