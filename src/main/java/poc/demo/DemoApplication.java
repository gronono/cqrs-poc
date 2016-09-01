package poc.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandDispatcher;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.InvalidCommandException;
import poc.cqrs.command.impl.DefaultCommandBus;
import poc.cqrs.command.impl.DefaultCommandDispatcher;
import poc.demo.product.command.CreateProductCommand;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws InvalidCommandException {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner run(CommandBus commandBus) {
		return (args) -> commandBus.send(new CreateProductCommand("Produit 1")); 
	}
	
	@Bean
	public CommandBus createCommandBus(List<CommandHandler<?>> handlers) {
		CommandDispatcher commandDispatcher = new DefaultCommandDispatcher(handlers);
		CommandBus commandBus = new DefaultCommandBus(commandDispatcher);

		return commandBus;
	}

}
