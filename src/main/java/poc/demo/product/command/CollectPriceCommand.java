package poc.demo.product.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import poc.cqrs.spring.Command;

@Command(name = "collect-price")
public class CollectPriceCommand {

	private String productId;
	private long price;
	
	@JsonCreator
	public CollectPriceCommand(@JsonProperty("productId") String productId, @JsonProperty("price") long price) {
		this.productId = productId;
		this.price = price;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public long getPrice() {
		return price;
	}
}
