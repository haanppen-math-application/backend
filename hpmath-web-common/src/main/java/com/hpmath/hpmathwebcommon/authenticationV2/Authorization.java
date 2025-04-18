package com.hpmath.hpmathwebcommon.authenticationV2;

import com.hpmath.hpmathcore.Role;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorization {
    Role[] values() default {};
    boolean opened() default false;
}
