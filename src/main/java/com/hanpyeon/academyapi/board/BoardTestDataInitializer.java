package com.hanpyeon.academyapi.board;

import com.hanpyeon.academyapi.account.MemberInitializer;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dao.CommentRepository;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.service.comment.register.CommentRegisterManager;
import com.hanpyeon.academyapi.security.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardTestDataInitializer {
    private final MemberInitializer memberInitializer;
    private final CommentRegisterManager commentRegisterManager;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;

    @PostConstruct
    void init() {
        List<Member> students = memberRepository.findMembersByRoleAndRemovedIsFalse(Role.STUDENT);
        List<Member> teachers = memberRepository.findMembersByRoleAndRemovedIsFalse(Role.TEACHER);
        List<Question> questions = new ArrayList<>();

        questions.add(Question.builder().ownerMember(students.get(0)).targetMember(teachers.get(0)).title("test").build());
        for (Member student : students) {
            for (Member teacher : teachers) {
                final Question question = Question.builder().ownerMember(student).targetMember(teacher).title("test").build();
                questions.add(question);
            }
        }
        questionRepository.saveAllAndFlush(questions);
    }
}
