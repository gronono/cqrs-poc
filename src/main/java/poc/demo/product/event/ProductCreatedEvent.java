package poc.demo.product.event;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductCreatedEvent {

	private UUID id;
	private String name;
	
	@JsonCreator
	public ProductCreatedEvent(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("ProductCreatedEvent [id=%s, name=%s]", id, name);
	}
	
}
