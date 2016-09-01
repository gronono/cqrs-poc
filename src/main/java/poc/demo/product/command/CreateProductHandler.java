package poc.demo.product.command;

import org.springframework.stereotype.Service;

import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.HandlerFor;
import poc.cqrs.command.InvalidCommandException;

@Service
@HandlerFor(CreateProductCommand.class)
public class CreateProductHandler implements CommandHandler<CreateProductCommand> {

	@Override
	public void handle(CreateProductCommand command) throws InvalidCommandException {
		System.err.println("Création du produit " + command.getName());
	}
}
