package com.hanpyeon.academyapi.media.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * {@link MediaStorage}의 {@link Async}기능을 추가하기 위해 도입된 {@code Decorator} 클래스입니다.
 */
@Component
@RequiredArgsConstructor
public class StorageAsyncDecorator {
    private final MediaStorage mediaStorage;
    /**
     *
     * {@link AsyncUploadFile} 구현체를 argument로 받습니다.
     * @param asyncUploadFile
     */
    @Async
    public void store(final AsyncUploadFile asyncUploadFile) {
        mediaStorage.store(asyncUploadFile);
    }
}
