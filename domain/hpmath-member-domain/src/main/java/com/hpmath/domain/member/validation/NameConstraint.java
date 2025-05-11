package com.hpmath.domain.member.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.validator.constraints.Length;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Length(min = 1, max = 10)
@NotNull
public @interface NameConstraint {
    String message() default "이름은 1 이상 10 이하";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

