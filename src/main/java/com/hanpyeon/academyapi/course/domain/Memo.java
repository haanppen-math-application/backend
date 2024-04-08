package com.hanpyeon.academyapi.course.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Memo {
    private Course course;
    private LocalDateTime date;
    private String progressed;
    private String homework;
}
