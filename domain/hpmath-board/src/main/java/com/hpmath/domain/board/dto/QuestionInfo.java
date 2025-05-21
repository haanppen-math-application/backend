package com.hpmath.domain.board.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class QuestionInfo {
    private final Long questionId;
    private final String title;
    private final String content;
    private final Boolean solved;
    private final Timestamp registeredDateTime;
    private final Long ownerId;
    private final Long targetId;
    private final String imageSrc;

    public LocalDateTime registeredDateTime() {
        return registeredDateTime.toLocalDateTime();
    }
}
