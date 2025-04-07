package com.Yansb.homevet.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Enum<?>> {
  private Class<? extends Enum<?>> enumClass;

  @Override
  public void initialize(EnumValidator constraintAnnotation) {
    this.enumClass = constraintAnnotation.enumClass();
  }

  @Override
  public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }

    return enumClass.isInstance(value);
  }
}
