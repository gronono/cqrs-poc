package poc.demo.product.command;

import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.HandlerFor;
import poc.cqrs.command.InvalidCommandException;

@HandlerFor(CreateProductCommand.class)
public class CreateProductHandler implements CommandHandler<CreateProductCommand> {

	@Override
	public void handle(CreateProductCommand command) throws InvalidCommandException {
		System.out.println("Création du produit " + command.getName());
	}

}
