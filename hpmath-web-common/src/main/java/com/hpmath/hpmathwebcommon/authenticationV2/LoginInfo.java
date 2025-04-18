package com.hpmath.hpmathwebcommon.authenticationV2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link com.hpmath.hpmathwebcommon.authentication.MemberPrincipal} 을 리턴하도록 하는 어노테이션입니다.
 * {@link LoginIdMethodArgumentResolver} 를 통해 실행됩니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface LoginInfo {
}
