package com.hanpyeon.academyapi.entity;

import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName;
    private String phoneNumber;

    private String password;

    private Integer grade;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Builder
    public Member(String userName, String phoneNumber, Integer grade, Role userRole) {
        this.memberName = userName;
        this.phoneNumber = phoneNumber;
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
        this.password = "0000";
        if (this.userRole == null) {
            this.userRole = Role.ROLE_STUDENT;
        }
    }

    public Member() {

    }
}
