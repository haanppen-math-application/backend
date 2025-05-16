package com.hpmath.app.api.banner.controller;

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
