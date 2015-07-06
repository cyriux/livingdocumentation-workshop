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
 * 
 * @author Cyrille.Martraire
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Entity {

    String brief() default "An Entity that acts as the root for a cluster of associated objects, all treated as a unit";

    String link() default "http://domaindrivendesign.org/node/88";
}
