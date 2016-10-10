package poc.demo.product.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import poc.cqrs.spring.Command;

/**
 * Permet de créer des nouveaux produits.
 */
@Command(name = "create-product")
public class CreateProductCommand {

	private String name;
	
	@JsonCreator
	public CreateProductCommand(@JsonProperty("name") String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("CreateProductCommand [name=%s]", name);
	}
}
