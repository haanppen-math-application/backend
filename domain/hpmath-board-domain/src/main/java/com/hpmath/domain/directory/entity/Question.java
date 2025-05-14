package com.hpmath.domain.directory.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String content;

    @Column(nullable = false)
    private Boolean solved = false;

    @Column(nullable = false)
    private Long viewCount = 0l;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ownerMember", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Column(name = "owner_member")
    private Long ownerMemberId;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "targetMember", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Column(name = "target_member")
    private Long targetMemberId;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<QuestionImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

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

    public void changeTitle(final String title) {
        this.title = title;
    }

    public void changeContent(final String content) {
        this.content = content;
    }

    public int addComment(final Comment comment) {
        this.comments.add(comment);
        comment.setQuestion(this);
        return comments.size();
    }

    public void changeTargetMember(final Long targetMember) {
        this.targetMemberId = targetMember;
    }

    public void changeImages(final List<String> imageSrcs) {
        this.images.clear();
        this.images.addAll(imageSrcs.stream()
                .map(src -> QuestionImage.of(this, src))
                .toList());
    }

    public void addViewCount() {
        this.viewCount++;
    }

    public void clearSolved() {
        this.solved = false;
        clearCommentsAdoptedStatus();
    }

    public void solved() {
        this.solved = true;
    }

    void clearCommentsAdoptedStatus() {
        comments.parallelStream()
                .forEach(comment -> comment.singleDeAdopt());
    }
}
