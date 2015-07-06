/**
 * 
 */
package flottio.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents a domain service, stateless.
 * 
 * @author Cyrille.Martraire
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DomainService {

    String brief() default "A Domain Service, i.e. a service that belongs to the domain and the domain language";

    String[] link() default { "http://stochastyk.blogspot.com/2008/05/domain-services-in-domain-driven-design.html" };
}
