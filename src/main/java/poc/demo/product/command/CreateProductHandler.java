package poc.demo.product.command;

import java.util.UUID;

import org.springframework.stereotype.Service;

import poc.cqrs.command.Aggregate;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.InvalidCommandException;
import poc.cqrs.event.EventBus;
import poc.demo.Aggregates;
import poc.demo.product.event.ProductCreatedEvent;

@Service
public class CreateProductHandler implements CommandHandler<CreateProductCommand> {

	private EventBus eventBus;
	
	public CreateProductHandler(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public Aggregate handle(CreateProductCommand command) throws InvalidCommandException {
		System.err.println("Création du produit " + command.getName());
		
		UUID id = UUID.randomUUID();
		Aggregate aggregate = new Aggregate(id, Aggregates.PRODUCTS);
		eventBus.apply(aggregate, new ProductCreatedEvent(id, command.getName()));
		
		return aggregate;
	}
}
