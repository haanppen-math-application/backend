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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime registeredDateTime;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Boolean solved = false;
    @Column(nullable = false)
    private Long viewCount = 0l;
    @ManyToOne(targetEntity = Member.class)
    private Member ownerMember;
    @ManyToOne(targetEntity = Member.class)
    private Member targetMember;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;
    @OneToMany
    @JoinColumn(name = "question_id")
    private List<Comment> comments;


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
}
