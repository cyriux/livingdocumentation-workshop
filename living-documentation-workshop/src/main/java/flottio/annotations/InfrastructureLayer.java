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
 * Marks this package as the root of the infrastructure layer.
 * 
 * This layer typically provides implementations of technical concerns such as
 * persistence, transactions, and other aspects.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.PACKAGE)
public @interface InfrastructureLayer {

}
