package com.hpmath.domain.member.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@Pattern(regexp = "^010[0-9]+$")
@NotNull
public @interface PhoneNumberConstraint {
    String message() default "전화번호 입력 오류. 010XXXXXXXX 형식으로 입력해주세요";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
