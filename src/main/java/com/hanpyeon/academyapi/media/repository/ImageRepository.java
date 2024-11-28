package com.hanpyeon.academyapi.media.repository;

import com.hanpyeon.academyapi.media.entity.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Boolean existsBySrc(final String src);
    Boolean existsAllBySrcIn(final List<String> src);
    List<Image> findAllBySrcIn(final List<String> src);
}
