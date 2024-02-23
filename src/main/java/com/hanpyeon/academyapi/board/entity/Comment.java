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
    @Column(nullable = false)
    private String content;
    private Boolean isSelected = false;
    @CreationTimestamp
    private LocalDateTime registeredDateTime;
    @ManyToOne
    private Member registeredMember;
    @ManyToOne
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
    public void isSelected() {
        this.isSelected = true;
    }
}
