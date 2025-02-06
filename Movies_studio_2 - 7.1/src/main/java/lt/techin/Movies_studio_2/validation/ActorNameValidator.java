package lt.techin.Movies_studio_2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Size;

public class ActorNameValidator implements ConstraintValidator<ActorName, String> {

  @Override
  public boolean isValid(@Size(min = 2, max = 20) String nameActor, ConstraintValidatorContext constraintValidatorContext) {
    return nameActor != null && nameActor.matches("^[A-Z][a-z]+$");
  }
}
