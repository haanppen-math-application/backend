package com.hanpyeon.academyapi.media.repository;

import com.hanpyeon.academyapi.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllBySrcIn(final List<String> src);
    Optional<Media> findBySrc(final String src);
}
