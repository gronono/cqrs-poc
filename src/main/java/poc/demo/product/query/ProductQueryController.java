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
	private ProductSearch search;
	
	@Autowired
	public ProductQueryController(ProductRepository repo, ProductSearch search) {
		this.repo = repo;
		this.search = search;
	}
	
	@RequestMapping
	public Iterable<Product> get() {
		return repo.findAll();
	}
	
	@RequestMapping(path = "/{id}")
	public Product get(@PathVariable UUID id) {
		return repo.findOne(id);
	}
	
	@RequestMapping(path = "/search/{name}")
	public Iterable<Product> get(@PathVariable String name) {
		return search.searchProduct(name);
	}
}
