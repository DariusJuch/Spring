package lt.techin.Movies_studio_2.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DirectorValidator.class)
public @interface Director {

  String message() default "Director cannot be null, and must start with uppercase letters";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
