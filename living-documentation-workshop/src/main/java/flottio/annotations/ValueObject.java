/**
 * 
 */
package flottio.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents a value object (immutable and with no identity)
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ValueObject {

    String brief() default "Represents a Value Object";

    String[] link() default {
            "http://martinfowler.com/bliki/ValueObject.html",
            "http://stochastyk.blogspot.com/2008/05/value-objects-in-ddd-part-2-creating.html" };
}
