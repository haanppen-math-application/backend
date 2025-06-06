package com.hpmath.domain.board.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "question",
        indexes = {
                @Index(name = "idx_ownerId_title", columnList = "owner_member, title"),
                @Index(name = "idx_dateTime", columnList = "registered_date_time desc")
        })
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "solved", nullable = false)
    private Boolean solved = false;

    @Column(name = "owner_member")
    private Long ownerMemberId;

    @Column(name = "target_member")
    private Long targetMemberId;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @BatchSize(size = 100)
    private final List<QuestionImage> images = new ArrayList<>();

    private Question(String title, String content, Long ownerMember, Long targetMember) {
        this.title = title;
        this.content = content;
        this.ownerMemberId = ownerMember;
        this.targetMemberId = targetMember;
    }

    public static Question of(@NotNull final List<String> images, final String title, final String content,
                              final Long ownerMember, final Long targetMember) {
        final Question question = new Question(title, content, ownerMember, targetMember);
        question.changeImages(images);
        return question;
    }

    public void changeImages(final List<String> imageSrcs) {
        this.images.clear();
        this.images.addAll(imageSrcs.stream()
                .map(src -> QuestionImage.of(this, src))
                .toList());
    }

    public void solved() {
        this.solved = true;
    }
}
