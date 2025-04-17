package com.hpmath.academyapi.online.dto;

import com.hpmath.academyapi.exception.BusinessException;
import com.hpmath.academyapi.security.Role;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AddOnlineCourseCommandTest {
    @Test
    void 선생이_다른_선생_수업등록시_에러처리() {
        Assertions.assertThatThrownBy(() -> new AddOnlineCourseCommand(
                1L,
                Role.TEACHER,
                "test",
                List.of(2L, 3L),
                2L
        )).isInstanceOf(BusinessException.class);
    }
}