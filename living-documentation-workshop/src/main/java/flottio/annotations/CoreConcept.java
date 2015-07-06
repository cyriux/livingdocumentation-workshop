package flottio.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks this type as a core concept, i.e. a concept that is primarily
 * important.
 * 
 * Flag the elements of the core domain within the primary repository of the
 * model, without particularly trying to elucidate its role. Make it effortless
 * for a developer to know what is in or out of the core domain.
 * 
 * p. 419 of the Blue Book.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface CoreConcept {

}
