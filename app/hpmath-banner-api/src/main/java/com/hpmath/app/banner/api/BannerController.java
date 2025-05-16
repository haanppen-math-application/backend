package com.hpmath.app.banner.api;

import com.hpmath.app.banner.api.Requests.AddBannerRequest;
import com.hpmath.app.banner.api.Requests.ChangeBannerRequest;
import com.hpmath.domain.banner.BannerService;
import com.hpmath.domain.banner.dto.AddBannerCommand;
import com.hpmath.domain.banner.dto.BannerResponse;
import com.hpmath.domain.banner.dto.ChangeBannerCommand;
import com.hpmath.domain.banner.dto.DeleteBannerCommand;
import com.hpmath.common.Role;
import com.hpmath.common.web.authenticationV2.Authorization;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class BannerController {
    private final BannerService bannerService;

    @GetMapping("/api/banners")
    public ResponseEntity<List<BannerResponse>> queryAllBanners() {
        return ResponseEntity.ok(bannerService.queryAllBanners());
    }

    @PostMapping("/api/banners")
    @Authorization(values = Role.ADMIN)
    public ResponseEntity<Void> addBanner(
            @RequestBody AddBannerRequest addBannerRequest
    ) {
        final AddBannerCommand addBannerCommand = new AddBannerCommand(addBannerRequest.content());
        bannerService.addBanner(addBannerCommand);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/banners")
    @Authorization(values = Role.ADMIN)
    public ResponseEntity<Void> modifyBanner(
            @RequestBody ChangeBannerRequest changeBannerRequest
    ) {
        final ChangeBannerCommand changeBannerCommand = new ChangeBannerCommand(
                changeBannerRequest.bannerId(),
                changeBannerRequest.content()
        );
        bannerService.changeBanner(changeBannerCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/banners/{bannerId}")
    @Authorization(values = Role.ADMIN)
    public ResponseEntity<Void> deleteBanner(
            @PathVariable(required = true) final Long bannerId
    ) {
        final DeleteBannerCommand deleteBannerCommand = new DeleteBannerCommand(bannerId);
        bannerService.deleteBanner(deleteBannerCommand);
        return ResponseEntity.noContent().build();
    }
}
