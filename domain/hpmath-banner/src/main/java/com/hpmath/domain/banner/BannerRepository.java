package com.hpmath.domain.banner;

import com.hpmath.domain.banner.dto.BannerResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface BannerRepository extends JpaRepository<Banner, Long> {
    @Query("SELECT new com.hpmath.domain.banner.dto.BannerResponse(b.bannerId, b.content, b.lastModified) FROM Banner b")
    List<BannerResponse> queryAllBanners();
}
