package poc.demo.product.command;

import java.util.UUID;

import org.springframework.stereotype.Service;

import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.CommandHandlerFor;
import poc.cqrs.command.InvalidCommandException;
import poc.demo.product.Product;
import poc.demo.product.ProductRepository;

@Service
@CommandHandlerFor(command = CreateProductCommand.class, path = "create-product")
public class CreateProductHandler implements CommandHandler<CreateProductCommand> {

	private ProductRepository repo;

	public CreateProductHandler(ProductRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public UUID handle(CreateProductCommand command) throws InvalidCommandException {
		System.err.println("Création du produit " + command.getName());
		Product saved = this.repo.save(new Product(command.getName()));
		return saved.getUuid();
	}
}
