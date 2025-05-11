package com.hpmath.hpmathcoreapi.banner.controller;

public class Requests {
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
