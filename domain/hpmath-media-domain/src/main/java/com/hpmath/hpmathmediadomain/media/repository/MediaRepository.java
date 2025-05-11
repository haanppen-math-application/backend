package com.hpmath.hpmathmediadomain.media.repository;

import com.hpmath.hpmathmediadomain.media.entity.Media;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllBySrcIn(final List<String> src);
    Optional<Media> findBySrc(final String src);

    @Query("SELECT new java.lang.String(m.mediaName) FROM Media m where m.src = :src")
    Optional<String> findFileNameBySrc(@Param("src") final String src);

    @Query("SELECT new java.lang.Long(media.memberId) FROM Media media WHERE media.src = :src")
    Optional<Long> findOwnerId(@Param("src") final String src);

    Boolean existsBySrc(final String src);
}
