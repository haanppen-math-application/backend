package com.hpmath.academyapi.board.entity;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.media.entity.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "ownerMember", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member ownerMember;
    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "targetMember", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member targetMember;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();
    @OneToMany(mappedBy = "question")
    private List<Comment> comments = new ArrayList<>();


    @Builder
    private Question(LocalDateTime registeredDateTime, List<Image> images, String title, String content, Member ownerMember, Member targetMember, List<Comment> comments) {
        this.registeredDateTime = registeredDateTime;
        this.images = images;
        this.title = title;
        this.content = content;
        this.ownerMember = ownerMember;
        this.targetMember = targetMember;
        this.comments = comments;
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

    public void changeTargetMember(final Member targetMember) {
        this.targetMember = targetMember;
    }

    public void changeImages(final List<Image> images) {
        this.images = images;
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
