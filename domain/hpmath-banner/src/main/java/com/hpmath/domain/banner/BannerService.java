package com.hpmath.domain.banner;

import com.hpmath.domain.banner.dto.AddBannerCommand;
import com.hpmath.domain.banner.dto.BannerResponse;
import com.hpmath.domain.banner.dto.ChangeBannerCommand;
import com.hpmath.domain.banner.dto.DeleteBannerCommand;
import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.ErrorCode;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@Validated
@CacheConfig(cacheNames = "banners")
public class BannerService {
    private final BannerRepository bannerRepository;

    @Transactional(readOnly = true)
    @Cacheable(key = "'allbanner'", sync = true)
    public List<BannerResponse> queryAllBanners() {
        log.debug("queryAllBanners called");
        return bannerRepository.queryAllBanners();
    }

    @CacheEvict(key = "'allbanner'")
    public void addBanner(final AddBannerCommand addBannerCommand) {
        log.debug("cache cleared");
        final Banner banner = new Banner(addBannerCommand.content());
        bannerRepository.save(banner);
    }

    @CacheEvict(key = "'allbanner'")
    public void changeBanner(@Valid final ChangeBannerCommand changeBannerCommand) {
        log.debug("cache cleared");
        final Banner banner = bannerRepository.findById(changeBannerCommand.bannerId())
                .orElseThrow(() -> new BusinessException("찾을 수 없는 배너", ErrorCode.BANNER_EXCEPTION));
        banner.changeContent(changeBannerCommand.content());
    }

    @CacheEvict(key = "'allbanner'")
    public void deleteBanner(@Valid final DeleteBannerCommand deleteBannerCommand) {
        log.debug("cache cleared");
        bannerRepository.deleteById(deleteBannerCommand.bannerId());
    }
}
