package com.hanpyeon.academyapi.media.entity;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.course.adapter.out.MemoMedia;
import jakarta.persistence.*;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_owner", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    public Media(String mediaName, String src, Member member) {
        this.mediaName = mediaName;
        this.src = src;
        this.member = member;
    }
}
