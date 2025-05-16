package com.hpmath.domain.board.service.question.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@NotBlank
public @interface QuestionTitleConstraint {
    String message() default "현재 질문에는 제목이 포함되야 합니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
