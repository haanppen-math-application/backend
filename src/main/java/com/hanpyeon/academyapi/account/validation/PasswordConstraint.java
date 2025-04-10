package com.hanpyeon.academyapi.account.validation;

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

@Length(min = 4, max = 20)
@NotNull
public @interface PasswordConstraint {
    String message() default "비밀번호는 4 와 20 사이입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
