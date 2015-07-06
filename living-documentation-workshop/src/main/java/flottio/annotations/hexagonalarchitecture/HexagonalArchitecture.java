package flottio.annotations.hexagonalarchitecture;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO + link
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.PACKAGE)
public @interface HexagonalArchitecture {

	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface DomainModel {

		String rationale() default "";

		String[] alternatives() default {};
	}

}
