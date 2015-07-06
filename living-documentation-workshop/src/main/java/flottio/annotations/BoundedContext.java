/**
 * 
 */
package flottio.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks this package as the root of a Bounded Context.
 * 
 * Bounded Context is a central pattern in Domain-Driven Design. DDD deals with
 * large models by dividing them into different Bounded Contexts and being
 * explicit about their interrelationships.
 * 
 * If you don't understand this well enough, please ask for explanation because
 * it's at the same time delicate to understand but also very important.
 * 
 * @see <a href="http://martinfowler.com/bliki/BoundedContext.html">Bounded
 *      Context</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.PACKAGE)
public @interface BoundedContext {

	String name();

	String domain() default "";

	String[] qualityAttributes() default {};

	String[] links() default {};
}
