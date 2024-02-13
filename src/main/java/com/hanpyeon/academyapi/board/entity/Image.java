package com.hanpyeon.academyapi.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String src;
    @NotBlank
    private String contentType;

    public Image(String src, String contentType) {
        this.src = src;
        this.contentType = contentType;
    }
}
