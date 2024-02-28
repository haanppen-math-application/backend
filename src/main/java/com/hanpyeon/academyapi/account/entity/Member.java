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
    private Long id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private Integer grade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime registeredDate;

    @Builder
    private Member(String phoneNumber, String name, String password, Integer grade, Role role, LocalDateTime registeredDate) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.grade = grade;
        this.role = role;
        this.registeredDate = registeredDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", memberName='" + name + '\'' +
                ", password='" + "****" + '\'' +
                ", grade=" + grade +
                ", userRole=" + role +
                ", localDateTime=" + registeredDate +
                '}';
    }

    public Member() {
    }
}
