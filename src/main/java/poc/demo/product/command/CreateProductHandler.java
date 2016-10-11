package poc.demo.product.command;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.cqrs.command.Aggregate;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.InvalidCommandException;
import poc.cqrs.event.EventBus;
import poc.demo.Aggregates;
import poc.demo.product.event.ProductCreatedEvent;
import poc.demo.product.query.Product;
import poc.demo.product.query.ProductSearch;

@Service
public class CreateProductHandler implements CommandHandler<CreateProductCommand> {

	private EventBus eventBus;
	private ProductSearch search;
	
	@Autowired
	public CreateProductHandler(EventBus eventBus, ProductSearch search) {
		this.eventBus = eventBus;
		this.search = search;
	}
	
	@Override
	public Aggregate handle(CreateProductCommand command) throws InvalidCommandException {
		
		List<Product> products = search.searchProduct(command.getName());
		if (products.isEmpty()) {
			UUID id = UUID.randomUUID();
			System.err.println("Création du produit " + command.getName() + " " + id.toString());
			
			Aggregate aggregate = new Aggregate(Aggregates.PRODUCTS, id);
			
			eventBus.apply(aggregate, new ProductCreatedEvent(new Product(id, command.getName())));
			return aggregate;
		}

		throw new InvalidCommandException("Le produit " + command.getName() + " existe déja. Trouvé: " + products);
	}
}
