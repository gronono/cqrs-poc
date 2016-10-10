package poc.demo.product.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import poc.demo.DemoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
public class ProductSearchTest {

	@Autowired
	private ProductSearch search;
	
	@Autowired
	private ProductRepository repo;
	
	@Before
	public void before() {
		repo.deleteAll();
	}
	
	/**
	 * Soio doit matcher avec soyo
	 */
	@Test
	public void testSoyo() {
		repo.save(new Product("soyo"));
		assertThat(search.searchProduct("soyo")).size().isEqualTo(1);
		assertThat(search.searchProduct("soio")).size().isEqualTo(1);
	}
	
	/**
	 * On ignore les majuscules
	 */
	@Test
	public void testMajuscules() {
		repo.save(new Product("soyo"));
		assertThat(search.searchProduct("SoYo")).size().isEqualTo(1);
	}
	
	/**
	 * On ignore les articles
	 */
	@Test
	public void testArticles() {
		repo.save(new Product("soyo"));
		repo.save(new Product("arbre"));

		assertThat(search.searchProduct("Le soyo")).size().isEqualTo(1);
		assertThat(search.searchProduct("l'arbre")).size().isEqualTo(1);
		assertThat(search.searchProduct("un arbre")).size().isEqualTo(1);
	}
	
	/**
	 * On ignore les accents
	 */
	@Test
	public void testAccents() {
		repo.save(new Product("phonétique"));
		assertThat(search.searchProduct("phonétique")).size().isEqualTo(1);
		assertThat(search.searchProduct("phonetique")).size().isEqualTo(1);
		assertThat(search.searchProduct("phonètique")).size().isEqualTo(1);
		assertThat(search.searchProduct("phonëtique")).size().isEqualTo(1);
		assertThat(search.searchProduct("phonÉtique")).size().isEqualTo(1);
		
		repo.save(new Product("fraîcheur"));
		assertThat(search.searchProduct("fraîcheur")).size().isEqualTo(1);
		assertThat(search.searchProduct("fraicheur")).size().isEqualTo(1);
		assertThat(search.searchProduct("fraïcheur")).size().isEqualTo(1);
		
		repo.save(new Product("hôtel"));
		assertThat(search.searchProduct("hôtel")).size().isEqualTo(1);
		assertThat(search.searchProduct("hotel")).size().isEqualTo(1);
		assertThat(search.searchProduct("hötel")).size().isEqualTo(1);
	}
	
	/**
	 * Les sons simulaires sont supportés o = au = eau; él = ell, ...
	 */
	@Test
	public void testSonsSimulaires() {
		repo.save(new Product("nutella"));
		assertThat(search.searchProduct("nutella")).size().isEqualTo(1);
		assertThat(search.searchProduct("nutélla")).size().isEqualTo(1);
		assertThat(search.searchProduct("nutéla")).size().isEqualTo(1);
		
		repo.save(new Product("chameau"));
		assertThat(search.searchProduct("chameau")).size().isEqualTo(1);
		assertThat(search.searchProduct("chamo")).size().isEqualTo(1);
		assertThat(search.searchProduct("chamau")).size().isEqualTo(1);
		
		repo.save(new Product("audio"));
		assertThat(search.searchProduct("audio")).size().isEqualTo(1);
		assertThat(search.searchProduct("odio")).size().isEqualTo(1);
		assertThat(search.searchProduct("eaudio")).size().isEqualTo(1);
		assertThat(search.searchProduct("eaudieau")).size().isEqualTo(1);
	}
}
