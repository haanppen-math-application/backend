package com.hanpyeon.academyapi.banner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bannerId;
    @Column
    private String content;
    private LocalDateTime lastModified;

    public Banner(String content) {
        this.content = content;
    }

    void changeContent(final String content) {
        this.content = content;
    }
}
