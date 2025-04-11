package com.hanpyeon.academyapi.account.dto;

public record StudentQueryCommand(Long cursorIndex,
                                  Integer pageSize,
                                  String name,
                                  Integer startGrade,
                                  Integer endGrade) {
}
