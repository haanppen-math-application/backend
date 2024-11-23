package com.hanpyeon.academyapi.banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BannerRepository extends JpaRepository<Banner, Long> {
}
