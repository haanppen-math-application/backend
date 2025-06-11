package com.hpmath.domain.board.service.question.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.validator.constraints.Length;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@Length(max = 500)
public @interface QuestionContentConstraint {
    String message() default "질문 길이는 500 이하여야합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
