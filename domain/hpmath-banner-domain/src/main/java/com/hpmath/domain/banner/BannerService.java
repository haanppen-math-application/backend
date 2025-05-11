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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class BannerService {
    private final BannerRepository bannerRepository;

    @Transactional(readOnly = true)
    public List<BannerResponse> queryAllBanners() {
        return bannerRepository.queryAllBanners();
    }

    public void addBanner(final AddBannerCommand addBannerCommand) {
        final Banner banner = new Banner(addBannerCommand.content());
        bannerRepository.save(banner);
    }

    public void changeBanner(@Valid final ChangeBannerCommand changeBannerCommand) {
        final Banner banner = bannerRepository.findById(changeBannerCommand.bannerId())
                .orElseThrow(() -> new BusinessException("찾을 수 없는 배너", ErrorCode.BANNER_EXCEPTION));
        banner.changeContent(changeBannerCommand.content());
    }

    public void deleteBanner(@Valid final DeleteBannerCommand deleteBannerCommand) {
        bannerRepository.deleteById(deleteBannerCommand.bannerId());
    }
}
