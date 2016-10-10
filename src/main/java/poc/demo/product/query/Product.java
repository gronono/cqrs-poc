package poc.demo.product.query;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import lombok.Data;
import lombok.NoArgsConstructor;
import poc.demo.lucene.ApproximatifAnalyser;

@Entity
@Data
@NoArgsConstructor
@Indexed
@Analyzer(impl = ApproximatifAnalyser.class)
public class Product {

	@Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String name;
	
	public Product(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Product(String name) {
		this(UUID.randomUUID(), name);
	}
}
