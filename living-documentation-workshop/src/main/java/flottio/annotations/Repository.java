/**
 * 
 */
package flottio.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mediates between the domain and data mapping layers using a collection-like
 * interface for accessing domain objects.
 * 
 * @see @see <a
 *      href="http://martinfowler.com/eaaCatalog/repository.html">Repository
 *      pattern</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Repository {

	@SuppressWarnings("rawtypes")
	Class[] value() default {};

	Codex rationale() default Codex.NO_CLUE;
}
