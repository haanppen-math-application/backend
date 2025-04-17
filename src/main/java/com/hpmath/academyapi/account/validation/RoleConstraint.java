package com.hpmath.academyapi.account.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@NotNull
public @interface RoleConstraint {
    String message() default "Role 입력은 필수입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
