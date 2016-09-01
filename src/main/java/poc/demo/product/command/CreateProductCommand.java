package poc.demo.product.command;

import poc.cqrs.command.Command;

/**
 * Permet de créer des nouveaux produits.
 */
public class CreateProductCommand implements Command {

	private String name;
	
	public CreateProductCommand(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("NewProductCommand [name=%s]", name);
	}
}
