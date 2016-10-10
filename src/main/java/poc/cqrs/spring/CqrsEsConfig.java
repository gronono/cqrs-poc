package poc.cqrs.spring;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.CommandRegistry;
import poc.cqrs.command.impl.DefaultCommandBus;
import poc.cqrs.event.EventBus;
import poc.cqrs.event.EventListener;
import poc.cqrs.event.EventStore;
import poc.cqrs.event.impl.DefaultEventBus;

@Configuration
@Import(CommandRegistrar.class)
public class CqrsEsConfig {

	@Bean
	public CommandBus commandBus(Collection<CommandHandler<?>> handlers) {
		return new DefaultCommandBus(handlers);
	}
	
	@Bean
	public CommandController commandController(CommandRegistry registry, CommandBus bus) {
		return new CommandController(registry, bus);
	}
	
	@Bean
	public EventBus eventBus(Collection<EventStore> stores, Collection<EventListener<?>> listeners) {
		return new DefaultEventBus(stores, listeners);
	}
	
	@Bean
	public EventController eventController(Collection<EventStore> stores) {
		return new EventController(stores);
	}
}
