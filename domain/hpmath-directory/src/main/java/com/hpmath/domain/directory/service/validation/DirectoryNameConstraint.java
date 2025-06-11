package com.hpmath.domain.directory.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@NotBlank
@Pattern(regexp = "^[가-힣a-zA-Z0-9]+$")
public @interface DirectoryNameConstraint {
    String message() default "디렉토리 이름이 잘못됐습니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
