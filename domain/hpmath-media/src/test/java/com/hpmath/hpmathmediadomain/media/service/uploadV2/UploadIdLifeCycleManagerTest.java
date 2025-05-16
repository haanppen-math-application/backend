package com.hpmath.hpmathmediadomain.media.service.uploadV2;

import com.hpmath.domain.media.service.uploadV2.UploadIdLifeCycleManager;
import com.hpmath.common.TimeProvider;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UploadIdLifeCycleManagerTest {
    private final LocalDateTime testTime = LocalDateTime.of(2020, 1, 1, 0, 0);
    private final Long expireTime = 4L;
    private final TimeProvider testTimeProvider = new TimeProvider() {
        @Override
        public LocalDateTime getCurrentTime() {
            return testTime;
        }
    };

    @Test
    void 중복_id_추가_에러처리_테스트() {
        final UploadIdLifeCycleManager uploadIdManager = new UploadIdLifeCycleManager(expireTime, testTimeProvider);
        uploadIdManager.addUniqueId("1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> uploadIdManager.addUniqueId("1"));
    }

    @Test
    void 시간_초과_경계_테스트() {
        final UploadIdLifeCycleManager uploadIdManager = new UploadIdLifeCycleManager(expireTime, testTimeProvider);

        uploadIdManager.addUniqueId("12");

        Assertions.assertAll(
                () -> Assertions.assertEquals(uploadIdManager.getExpiredIds(getAddedTime(testTime, expireTime)).size(), 0),
                () -> Assertions.assertEquals(uploadIdManager.getExpiredIds(getAddedTime(testTime, expireTime + 1L)).size(), 1)
        );
    }

    @Test
    void 없는_ID_시간_업데이트_시도_에러_테스트() {
        final UploadIdLifeCycleManager uploadIdManager = new UploadIdLifeCycleManager(expireTime, testTimeProvider);
        Assertions.assertThrows(IllegalArgumentException.class, () -> uploadIdManager.updateLastAccessTime("1"));
    }

    @Test
    void 시간_업데이트_테스트() {
        final UploadIdLifeCycleManager uploadIdManager = new UploadIdLifeCycleManager(expireTime, testTimeProvider);
        Assertions.assertThrows(IllegalArgumentException.class, () -> uploadIdManager.updateLastAccessTime("1"));
    }

    private LocalDateTime getAddedTime(LocalDateTime time, long minutes) {
        return time.plus(minutes, TimeUnit.MINUTES.toChronoUnit());
    }
}