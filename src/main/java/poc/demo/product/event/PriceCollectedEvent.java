package poc.demo.product.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import poc.demo.product.query.Product;

public class PriceCollectedEvent {

	private Product product;
	private long price;
	
	@JsonCreator
	public PriceCollectedEvent(@JsonProperty("product") Product product, @JsonProperty("price") long price) {
		this.product = product;
		this.price = price;
	}
	
	public long getPrice() {
		return price;
	}

	public Product getProduct() {
		return product;
	}
	
	@Override
	public String toString() {
		return String.format("PriceCollectedEvent [price=%s]", price);
	}
}
