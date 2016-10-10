package poc.demo.product.query;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/products", method = GET)
public class ProductQueryController {

	private ProductRepository repo;
	
	@Autowired
	public ProductQueryController(ProductRepository repo) {
		this.repo = repo;
	}
	
	@RequestMapping
	public Iterable<Product> get() {
		return repo.findAll();
	}
	
	@RequestMapping(path = "/{id}")
	public Product get(@PathVariable UUID id) {
		return repo.findOne(id);
	}
}
