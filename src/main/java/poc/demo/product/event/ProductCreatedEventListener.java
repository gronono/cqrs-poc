package poc.demo.product.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.cqrs.event.EventListener;
import poc.demo.product.query.ProductRepository;

@Service
public class ProductCreatedEventListener implements EventListener<ProductCreatedEvent> {

	private ProductRepository repo;
	
	@Autowired
	public ProductCreatedEventListener(ProductRepository repo) {
		this.repo = repo;
	}

	@Override
	public void onEvent(ProductCreatedEvent event) {
		repo.save(event.getProduct());
	}
}
