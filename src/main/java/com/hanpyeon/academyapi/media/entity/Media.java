package com.hanpyeon.academyapi.media.entity;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue
    @Column(name = "media_id")
    private Long id;

    @Column(nullable = false)
    private String mediaName;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(nullable = false, unique = true)
    private String src;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_owner", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(name = "duration", nullable = true)
    private Long duration;

    @Column(name = "size", nullable = true)
    private Long size;

    public Media(String mediaName, String src, Member member) {
        this.mediaName = mediaName;
        this.src = src;
        this.member = member;
    }

    public Media(String mediaName, String src, Member member, Long duration, Long size) {
        this.mediaName = mediaName;
        this.src = src;
        this.member = member;
        this.duration = duration;
        this.size = size;
    }
}
