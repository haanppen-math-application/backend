package com.hanpyeon.academyapi.banner.controller;

import com.hanpyeon.academyapi.banner.BannerService;
import com.hanpyeon.academyapi.banner.dto.AddBannerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class BannerController {

    private final BannerService bannerService;

    @PostMapping("/api/banner")
    public ResponseEntity<?> addBanner(
            @RequestBody AddBannerRequest addBannerRequest
    ) {
        final AddBannerCommand addBannerCommand = new AddBannerCommand(addBannerRequest.content);
        bannerService.addBanner(addBannerCommand);
        return ResponseEntity.ok().build();
    }

    record AddBannerRequest(
            String content
    ) {
    }
}
