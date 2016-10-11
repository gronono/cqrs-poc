package poc.demo.product.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.cqrs.event.EventListener;
import poc.demo.product.query.Product;
import poc.demo.product.query.ProductRepository;

@Service
public class PriceCollectedEventListener implements EventListener<PriceCollectedEvent> {

	private ProductRepository repo;

	@Autowired
	public PriceCollectedEventListener(ProductRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public void onEvent(PriceCollectedEvent event) {
		Product product = event.getProduct();
		product.setPrice(event.getPrice());
		repo.save(product);
	}

}
