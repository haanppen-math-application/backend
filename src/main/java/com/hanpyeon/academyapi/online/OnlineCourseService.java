package com.hanpyeon.academyapi.online;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.dto.AddOnlineCourseCommand;
import com.hanpyeon.academyapi.security.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineCourseService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final MemberRepository memberRepository;
    private final OnlineStudentRepository onlineStudentRepository;

    @Transactional
    public void addOnlineCourse(@Validated final AddOnlineCourseCommand addOnlineCourseCommand) {
        validateRequest(addOnlineCourseCommand.requestMemberRole(), addOnlineCourseCommand.requestMemberId(),
                addOnlineCourseCommand.teacherId());

        final Member teacher = getTeacher(addOnlineCourseCommand.teacherId());
        final OnlineCourse onlineCourse = new OnlineCourse(teacher, addOnlineCourseCommand.onlineCourseName());

        onlineCourseRepository.save(onlineCourse);
        addStudents(addOnlineCourseCommand.students(), onlineCourse);

        log.info("online Course Created : %s", addOnlineCourseCommand.toString());
    }

    private void addStudents(final List<Long> studentIds, final OnlineCourse onlineCourse) {
        if (studentIds.isEmpty()) {
            return;
        }
        final List<OnlineStudent> onlineStudents = mapToOnlineStudents(getStudents(studentIds), onlineCourse);
        onlineStudentRepository.saveAll(onlineStudents);
    }

    private void validateRequest(final Role role, final Long requestMemberId, final Long targetMemberId) {
        if (!role.equals(Role.TEACHER)) {
            return;
        }
        if (!requestMemberId.equals(targetMemberId)) {
            log.debug("Online Lecture Registeration Exception : WRONG %L , %L", targetMemberId, requestMemberId);
            throw new BusinessException("선생님은 본인 수업만 업로드 가능합니다.", ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
    }

    private Member getTeacher(final Long memberId) {
        return memberRepository.findMemberByIdAndRoleAndRemovedIsFalse(memberId, Role.TEACHER)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
    }

    private List<Member> getStudents(final List<Long> studentIds) {
        final List<Member> students = memberRepository.findMembersByIdIsInAndRoleAndRemovedIsFalse(studentIds, Role.STUDENT);
        if (students.size() != studentIds.size()) {
            throw new BusinessException(ErrorCode.CANNOT_FIND_USER);
        }
        return students;
    }

    private List<OnlineStudent> mapToOnlineStudents(final List<Member> members, final OnlineCourse onlineCourse) {
        return members.stream()
                .map(member -> new OnlineStudent(onlineCourse, member))
                .toList();
    }
}
