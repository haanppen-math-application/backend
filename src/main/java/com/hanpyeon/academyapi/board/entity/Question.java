package com.hanpyeon.academyapi.board.entity;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime registeredDate;
    private String imagePath;
    private String content;
    @Column(columnDefinition = "boolean default false")
    private Boolean solved;
    private Long viewCount;
    @ManyToOne(targetEntity = Member.class)
    private Member member;

}
