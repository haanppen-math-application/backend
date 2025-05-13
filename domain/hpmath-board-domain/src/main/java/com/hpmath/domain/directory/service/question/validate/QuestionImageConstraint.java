package com.hpmath.domain.directory.service.question.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@Size(max = 3)
public @interface QuestionImageConstraint {
    String message() default "이미지는 최대 3개 등록 가능";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
