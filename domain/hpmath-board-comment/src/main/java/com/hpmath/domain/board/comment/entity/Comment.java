package com.hpmath.domain.board.comment.entity;

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
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(
        name = "comment",
        indexes = {
                @Index(name = "idx_questionId", columnList = "question_id"),
                @Index(name = "idx_registeredMemberId", columnList = "registered_member_id")})
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "registered_member_id")
    private Long ownerId;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<CommentImage> images = new ArrayList<>();

    public static Comment of(final Long questionId, final Long registeredMemberId, final String content, @NotNull final List<String> mediaSrcs) {
        final Comment comment = new Comment();
        comment.questionId = questionId;
        comment.ownerId = registeredMemberId;
        comment.content = content;
        comment.addMedias(mediaSrcs);

        return comment;
    }

    public void addMedias(final List<String> mediaSrcs) {
        this.images.addAll(mediaSrcs.stream()
                .map(src -> CommentImage.of(this, src))
                .toList());
    }

    /**
     * @param mediaSrcs
     * @return oldImageSrcs
     */
    public List<String> changeImages(final List<String> mediaSrcs) {
        final List<String> oldImages = new ArrayList<>(this.images.stream()
                .map(CommentImage::getImageSrc)
                .toList());
        this.images.clear();
        this.addMedias(mediaSrcs);

        return oldImages;
    }
}
