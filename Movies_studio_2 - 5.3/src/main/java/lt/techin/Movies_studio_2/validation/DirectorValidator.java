package lt.techin.Movies_studio_2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DirectorValidator implements ConstraintValidator<Director, String> {


  @Override
  public boolean isValid(String director, ConstraintValidatorContext constraintValidatorContext) {
    return director != null && director.matches("^[A-Z][a-z]+$");
  }
}
