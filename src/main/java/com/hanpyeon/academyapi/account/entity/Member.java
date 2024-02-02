package com.hanpyeon.academyapi.account.entity;

import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String password;

    private Integer grade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Builder
    private Member(String phoneNumber, String memberName, String password, Integer grade, Role userRole, LocalDateTime localDateTime) {
        this.phoneNumber = phoneNumber;
        this.memberName = memberName;
        this.password = password;
        this.grade = grade;
        this.userRole = userRole;
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", memberName='" + memberName + '\'' +
                ", password='" + password + '\'' +
                ", grade=" + grade +
                ", userRole=" + userRole +
                ", localDateTime=" + localDateTime +
                '}';
    }

    public Member() {
    }
}
