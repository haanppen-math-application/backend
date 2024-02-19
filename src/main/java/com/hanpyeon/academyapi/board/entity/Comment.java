package com.hanpyeon.academyapi.board.entity;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Boolean isSelected = false;
    private LocalDateTime registeredDateTime;
    @ManyToOne
    private Member registeredMember;
    @ManyToOne
    private Question question;
    @OneToMany
    private List<Image> images;

    @Builder
    public Comment(String content, LocalDateTime registeredDateTime, Member registeredMember, Question question, List<Image> images) {
        this.content = content;
        this.registeredDateTime = registeredDateTime;
        this.registeredMember = registeredMember;
        this.question = question;
        this.images = images;
    }
}
