package lt.techin.Movies_studio_2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LastNameActorValidator implements ConstraintValidator<LastNameActor, String> {


  @Override
  public boolean isValid(String lastName, ConstraintValidatorContext constraintValidatorContext) {
    return lastName != null && lastName.matches("^[A-Z][a-z]+$");
  }
}
