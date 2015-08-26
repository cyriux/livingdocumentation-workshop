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
 * Marks this element as a site to be part of the guided tour
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface GuidedTour {

	String name();

	String description() default "";

	int rank() default 0;
}
