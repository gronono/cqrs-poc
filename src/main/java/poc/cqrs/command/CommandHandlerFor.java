package poc.cqrs.command;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import poc.cqrs.command.impl.DefaultCommandController;

/**
 * <p>
 * 	Indique le type de {@link Command} que peut traiter un {@link CommandHandler}.
 * </p>
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface CommandHandlerFor {

	/**
	 * @return Le type de commande que peut traiter le handler. 
	 */
	Class<? extends Command> command();
	
	/**
	 * @return Est utilisé pour construire l'URL permettant l'envoi de la commande sur le bus.
	 * @see DefaultCommandController
	 */
	String path();
}
