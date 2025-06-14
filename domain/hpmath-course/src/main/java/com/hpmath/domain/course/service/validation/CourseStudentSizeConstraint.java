package com.hpmath.domain.course.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@Size(max = 50)
public @interface CourseStudentSizeConstraint {
    String message() default "수업 학생 수 초과";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
