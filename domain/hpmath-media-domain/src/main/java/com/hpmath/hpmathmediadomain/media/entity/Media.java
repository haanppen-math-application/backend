package com.hpmath.hpmathmediadomain.media.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = @Index(name = "idx_media_source", columnList = "src"))
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long id;

    @Column(nullable = false)
    private String mediaName;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(nullable = false, unique = true)
    private String src;

    @Column(name = "media_owner", nullable = true)
    private Long memberId;

    @Column(name = "duration", nullable = true)
    private Long duration;

    @Column(name = "size", nullable = true)
    private Long size;

    public Media(String mediaName, String src, Long memberId) {
        this.mediaName = mediaName;
        this.src = src;
        this.memberId = memberId;
    }

    public Media(String mediaName, String src, Long memberId, Long duration, Long size) {
        this.mediaName = mediaName;
        this.src = src;
        this.memberId = memberId;
        this.duration = duration;
        this.size = size;
    }
}
