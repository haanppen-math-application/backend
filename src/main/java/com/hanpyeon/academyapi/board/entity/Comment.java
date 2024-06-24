package com.hanpyeon.academyapi.board.entity;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.media.entity.Image;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
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
    @ManyToOne
    private Member registeredMember;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @OneToMany
    private List<Image> images;

    @Builder
    public Comment(String content, Member registeredMember, Question question, List<Image> images) {
        this.content = content;
        this.registeredMember = registeredMember;
        this.question = question;
        this.images = images;
    }
    void singleAdopt() {
        this.adopted = true;
    }
    void singleDeAdopt() {
        this.adopted = false;
    }
    public void adopt() {
        question.clearCommentsAdoptedStatus();
        question.singleSolved();
        singleAdopt();
    }
    public void deAdopt() {
        question.singleUnsolved();
        question.clearCommentsAdoptedStatus();
    }
    public void setContent(final String content) {
        this.content = content;
    }
    public void changeImagesTo(final List<Image> images) {
        this.images = images;
    }
}
