package com.hanpyeon.academyapi.board.mapper;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterRequestDto;
import com.hanpyeon.academyapi.board.entity.Image;
import com.hanpyeon.academyapi.board.entity.Question;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class QuestionMapper {
    public QuestionRegisterDto createRegisterDto(final QuestionRegisterRequestDto questionRegisterRequestDto, final List<MultipartFile> multipartFiles, Long userId) {
        return QuestionRegisterDto.builder()
                .title(questionRegisterRequestDto.title())
                .content(questionRegisterRequestDto.content())
                .requestMemberId(userId)
                .targetMemberId(questionRegisterRequestDto.targetMemberId())
                .images(multipartFiles)
                .build();
    }

    public Question createEntity(final QuestionRegisterDto questionRegisterDto, final Member requestMember, final Member targetMember, final List<Image> images) {
        return Question.builder()
                .title(questionRegisterDto.title())
                .content(questionRegisterDto.content())
                .ownerMember(requestMember)
                .targetMember(targetMember)
                .images(images)
                .build();
    }
}
