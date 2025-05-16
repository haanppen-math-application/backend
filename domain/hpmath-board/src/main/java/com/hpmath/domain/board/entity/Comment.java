package com.hpmath.domain.board.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@ToString
@Table
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String content;

    private Boolean adopted = false;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "registeredMember", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Column(name = "registered_member")
    private Long registeredMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Question question;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CommentImage> images = new ArrayList<>();

    @Builder
    public Comment(String content, Long registeredMemberId, Question question) {
        this.content = content;
        this.registeredMember = registeredMemberId;
        this.question = question;
    }

    public static Comment createComment(final String content, final Long registeredMember, final Question question, final List<String> imageSrcs) {
        final Comment comment = new Comment(
                content,
                registeredMember,
                question
        );

        comment.changeImagesTo(imageSrcs);
        return comment;
    }

    void setQuestion(final Question question) {
        this.question = question;
    }

    void singleDeAdopt() {
        this.adopted = false;
    }

    public void delete() {
        this.question = null;
        this.registeredMember = null;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void changeImagesTo(final List<String> imageSrcs) {
        this.images.clear();
        this.images.addAll(imageSrcs.stream()
                .map(src -> CommentImage.of(this, src))
                .toList());
    }
}
