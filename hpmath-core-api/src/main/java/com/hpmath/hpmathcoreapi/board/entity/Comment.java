package com.hpmath.hpmathcoreapi.board.entity;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.media.entity.Image;
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
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = true)
    private String content;
    private Boolean adopted = false;
    @CreationTimestamp
    private LocalDateTime registeredDateTime;
    @ManyToOne
    @JoinColumn(name = "registeredMember", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member registeredMember;
    @ManyToOne(targetEntity = Question.class, optional = false)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Question question;
    @OneToMany
    private List<Image> images;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", adopted=" + adopted +
                ", registeredDateTime=" + registeredDateTime +
                ", registeredMember=" + registeredMember +
                ", question=" + question +
                ", images=" + images +
                '}';
    }

    @Builder
    public Comment(String content, Member registeredMember, Question question, List<Image> images) {
        this.content = content;
        this.registeredMember = registeredMember;
        this.question = question;
        this.images = images;
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
        this.images.clear();
    }
    public void setContent(final String content) {
        this.content = content;
    }
    public void changeImagesTo(final List<Image> images) {
        this.images = images;
    }
}
