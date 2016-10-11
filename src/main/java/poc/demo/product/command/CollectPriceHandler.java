package poc.demo.product.command;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.cqrs.command.Aggregate;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.InvalidCommandException;
import poc.cqrs.event.EventBus;
import poc.demo.Aggregates;
import poc.demo.product.event.PriceCollectedEvent;
import poc.demo.product.query.Product;
import poc.demo.product.query.ProductRepository;

@Service
public class CollectPriceHandler implements CommandHandler<CollectPriceCommand> {

	private ProductRepository repo;
	private EventBus eventBus;
	
	@Autowired
	public CollectPriceHandler(EventBus eventBus, ProductRepository repo) {
		this.eventBus = eventBus;
		this.repo = repo;
	}
	
	@Override
	public Aggregate handle(CollectPriceCommand command) throws InvalidCommandException {
		Aggregate aggregate = new Aggregate(Aggregates.PRODUCTS, UUID.fromString(command.getProductId()));
		Product product = repo.findOne(aggregate.getId());
		if (product == null) {
			throw new InvalidCommandException(String.format("Le produit d'identifiant %s n'existe pas", aggregate.getId())); 
		}
		
		if (command.getPrice() <= 0) {
			throw new InvalidCommandException(String.format("Le prix doit être strictement positif", aggregate.getId()));
		}
		
		eventBus.apply(aggregate, new PriceCollectedEvent(product, command.getPrice()));
		return aggregate;
	}
}
