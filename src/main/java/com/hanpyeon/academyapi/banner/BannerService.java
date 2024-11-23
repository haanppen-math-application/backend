package com.hanpyeon.academyapi.banner;

import com.hanpyeon.academyapi.banner.dto.AddBannerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;
    @Transactional
    public void addBanner(final AddBannerCommand addBannerCommand) {
        final Banner banner = new Banner(addBannerCommand.content());
        bannerRepository.save(banner);
    }
}
