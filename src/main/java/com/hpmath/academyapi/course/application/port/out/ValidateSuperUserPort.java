package com.hpmath.academyapi.course.application.port.out;

public interface ValidateSuperUserPort {
    Boolean isSuperUser(final Long memberId);
}
