package poc.cqrs.spring;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import poc.cqrs.command.CommandBus;
import poc.cqrs.command.CommandHandler;

/**
 * <p>
 * 	Indique cette classe est une commande.
 * 	C'est à dire un ordre donné à l'application de faire quelque chose.
 * 	Les commandes devraient immutables.
 * </p>
 * <p>
 * 	Les commandes sont envoyées à l'application via le {@link CommandBus}.
 * 	Elles sont traitées par les {@link CommandHandler}.
 * </p>
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface Command {

	/**
	 * <p>
	 * 	Nom de la commande.
	 * </p>
	 */
	String name(); 
}
