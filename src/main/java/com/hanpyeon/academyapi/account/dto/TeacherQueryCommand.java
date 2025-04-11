package com.hanpyeon.academyapi.account.dto;

public record TeacherQueryCommand(Long cursorIndex,
                                  Integer pageSize,
                                  String name)
{
}
