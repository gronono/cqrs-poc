package poc.demo.product.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSearch {

	private EntityManager entityManager;

	@Autowired
	public ProductSearch(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = true)
	public List<Product> searchProduct(String name) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();

		Query query = fullTextEntityManager.createFullTextQuery(
				qb.keyword()
					.onFields("name")
					.matching(name)
					.createQuery(), 
				Product.class);

		List<Product> result = query.getResultList();
		return result;
	}
}
