package com.hanpyeon.academyapi.media.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = @Index(name = "idx_media_source", columnList = "src"))
public class Media {
    @Id
    @GeneratedValue
    @Column(name = "media_id")
    private Long id;

    @Column(nullable = false)
    private String mediaName;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(nullable = false, unique = true)
    private String src;

    public Media(String mediaName, String src) {
        this.mediaName = mediaName;
        this.src = src;
    }
}
