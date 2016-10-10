package poc.demo;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import poc.cqrs.command.CommandBus;
import poc.cqrs.event.EventStore;
import poc.cqrs.event.impl.JdbcEventStore;
import poc.cqrs.spring.CqrsConfig;
import poc.demo.product.command.CreateProductCommand;

@SpringBootApplication
@Import(CqrsConfig.class)
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		for (String s : context.getBeanDefinitionNames()) {
			System.out.println(s);
		}
	}

	@Bean
	public EventStore productStore(DataSource datasource) {
		return new JdbcEventStore(Aggregates.PRODUCTS, datasource);
	}
	
	@Bean
	public CommandLineRunner run(CommandBus commandBus) {
		return (args) -> commandBus.send(new CreateProductCommand("Produit 1")); 
	}
	
}
