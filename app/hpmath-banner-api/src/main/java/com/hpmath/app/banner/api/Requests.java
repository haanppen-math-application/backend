package com.hpmath.app.banner.api;

class Requests {
    record AddBannerRequest(
            String content
    ) {
    }
    record ChangeBannerRequest(
            Long bannerId,
            String content
    ) {
    }
}
