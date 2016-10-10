package poc.demo.product.event;

import org.springframework.stereotype.Service;

import poc.cqrs.event.EventListener;

@Service
public class OnProductCreatedListener implements EventListener<ProductCreatedEvent> {

	@Override
	public void onEvent(ProductCreatedEvent event) {
		System.err.println("OnProductCreatedListener " + event);
	}

}
