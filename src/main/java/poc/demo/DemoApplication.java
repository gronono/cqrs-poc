package poc.demo;

import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandDispatcher;
import poc.cqrs.command.CommandHandler;
import poc.cqrs.command.InvalidCommandException;
import poc.cqrs.command.impl.DefaultCommandBus;
import poc.cqrs.command.impl.DefaultCommandDispatcher;
import poc.demo.product.command.CreateProductCommand;
import poc.demo.product.command.CreateProductHandler;

import java.util.Arrays;
import java.util.List;

public class DemoApplication {

	public static void main(String[] args) throws InvalidCommandException {
		CommandBus commandBus = createCommandBus(new CreateProductHandler());
		
		commandBus.send(new CreateProductCommand("Produit 1"));
	}
	
	private static CommandBus createCommandBus(CommandHandler<?> ... handlers) {
		List<CommandHandler<?>> commandHandlers = Arrays.asList(handlers);
		CommandDispatcher commandDispatcher = new DefaultCommandDispatcher(commandHandlers);
		CommandBus commandBus = new DefaultCommandBus(commandDispatcher);

		return commandBus;
	}

}
