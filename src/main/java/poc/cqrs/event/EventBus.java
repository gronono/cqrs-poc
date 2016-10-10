package poc.cqrs.event;

import poc.cqrs.command.Aggregate;

@FunctionalInterface
public interface EventBus {

	void apply(Aggregate aggregate, Object event);
}
