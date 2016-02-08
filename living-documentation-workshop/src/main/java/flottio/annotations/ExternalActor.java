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
 * Declares an external actor
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ ElementType.PACKAGE, ElementType.TYPE })
public @interface ExternalActor {

	String name();

	Direction direction();

	ActorType type() default ActorType.PEOPLE;

	String pictureLink() default "";

	String link() default "";

	/**
	 * The direction of who's calling who between the system and its external
	 * actors
	 */
	public enum Direction {

		/**
		 * Calls the system
		 */
		API,
		/**
		 * Is called by the syste
		 */
		SPI,

		/**
		 * Calls the system and is called by the system
		 */
		API_SPI;
	}

	public enum ActorType {

		PEOPLE, SYSTEM;
	}

}
