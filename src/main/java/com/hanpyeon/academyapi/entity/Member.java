package com.hanpyeon.academyapi.entity;

import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Entity
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

    @Builder
    public Member(String phoneNumber, String memberName, String password, Integer grade, Role userRole) {
        this.phoneNumber = phoneNumber;
        this.memberName = memberName;
        this.password = password;
        this.grade = grade;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userId=" + memberId +
                ", userName='" + memberName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", grade=" + grade +
                ", userRole=" + userRole +
                '}';
    }

    @PrePersist
    public void setDefaultValue(){
        if (this.userRole == null) {
            this.userRole = Role.ROLE_STUDENT;
        }
    }

    public Member() {
    }
}
