package com.hanpyeon.academyapi.media.repository;

import com.hanpyeon.academyapi.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllBySrcIn(final List<String> src);
}
