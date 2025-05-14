package com.hpmath.domain.banner;

import com.hpmath.domain.banner.dto.AddBannerCommand;
import com.hpmath.domain.banner.dto.ChangeBannerCommand;
import com.hpmath.domain.banner.dto.DeleteBannerCommand;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
class BannerServiceTest {
    @MockitoSpyBean
    private BannerService bannerService;

    @Test
    void 배너_조회는_동시조회에서_한번만_실행된다() {
        bannerService.addBanner(new AddBannerCommand("test"));

        queryFor(10);

        Mockito.verify(bannerService, Mockito.times(1)).queryAllBanners();
    }

    @Test
    void 배너_변경시_캐시_초기화() {
        Mockito.doNothing().when(bannerService).changeBanner(Mockito.any());

        queryFor(10);

        bannerService.changeBanner(new ChangeBannerCommand(1L, "test"));

        queryFor(10);

        Mockito.verify(bannerService, Mockito.times(2)).queryAllBanners();
    }

    @Test
    void 배너_일부_삭제시_캐시_초기화() {
        Mockito.doNothing().when(bannerService).deleteBanner(Mockito.any());

        queryFor(10);

        bannerService.deleteBanner(new DeleteBannerCommand(1L));

        queryFor(10);

        Mockito.verify(bannerService, Mockito.times(2)).queryAllBanners();
    }

    @Test
    void 배너_추가시_캐시_초기화() {
        Mockito.doNothing().when(bannerService).addBanner(Mockito.any());

        queryFor(10);

        bannerService.addBanner(new AddBannerCommand("test"));

        queryFor(10);

        Mockito.verify(bannerService, Mockito.times(2)).queryAllBanners();
    }

    private void queryFor(final int times) {
        Executor executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < times; i++) {
            executor.execute(() -> {
                bannerService.queryAllBanners();
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}