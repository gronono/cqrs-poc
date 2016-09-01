package poc.cqrs.command;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * 	Indique le type de {@link Command} que peut traiter un {@link CommandHandler}.
 * </p>
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface HandlerFor {

	/**
	 * @return Le type de commande que peut traiter le handler. 
	 */
	Class<? extends Command> value();
}
