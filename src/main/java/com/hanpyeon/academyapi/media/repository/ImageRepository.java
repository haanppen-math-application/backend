package com.hanpyeon.academyapi.media.repository;

import com.hanpyeon.academyapi.media.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Boolean existsBySrc(final String src);
}
