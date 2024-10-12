package com.hanpyeon.academyapi.account.entity;

import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
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
    private Boolean removed = false;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registeredDate;

    @Column(name = "verify_code", nullable = true)
    private String verificationCode;

    @Column(name = "verify_message_send_count", nullable = true)
    private Integer verifyTryCount = 0;

    @Column(name = "verify_message_request_time", nullable = true)
    private LocalDateTime verifyDateTime;

    public void setName(final String name) {
        this.name = name;
    }
    public void setGrade(final Integer grade) {
        this.grade = grade;
    }

    public void setRemoved(final Boolean removeStatus) {
        this.removed = removeStatus;
    }
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(final String password) {
        this.password = password;
    }
    public void setVerificationCode(final String verificationCode) {
        this.verificationCode = verificationCode;
        this.verifyDateTime = LocalDateTime.now();
        if (Objects.isNull(verifyTryCount)) {
            verifyTryCount = 0;
        }
        verifyTryCount ++;
    }

    @Builder
    private Member(String phoneNumber, String name, String encryptedPassword, Integer grade, Role role, LocalDateTime registeredDate) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = encryptedPassword;
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
}
