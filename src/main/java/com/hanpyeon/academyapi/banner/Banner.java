package com.hanpyeon.academyapi.banner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bannerId;
    @Column
    private String content;
    private LocalDateTime registeredDateTime;

    public Banner(String content) {
        this.content = content;
    }

    void changeContent(final String content) {
        this.content = content;
    }
}
