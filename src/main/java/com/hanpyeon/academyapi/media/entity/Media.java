package com.hanpyeon.academyapi.media.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
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
}
