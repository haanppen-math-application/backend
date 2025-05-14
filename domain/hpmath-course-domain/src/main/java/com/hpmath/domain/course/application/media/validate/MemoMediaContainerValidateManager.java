package com.hpmath.domain.course.application.media.validate;

import com.hpmath.domain.course.domain.MemoMediaContainer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoMediaContainerValidateManager {
    private final List<MemoMediaContainerValidator> validators;

    public void validate(final MemoMediaContainer container) {
        validators.stream()
                .forEach(validator -> validator.validate(container));
    }
}
