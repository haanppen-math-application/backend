package com.hanpyeon.academyapi.online;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.online.domain.OnlineCourseDomain;
import com.hanpyeon.academyapi.online.domain.OnlineCourseName;
import com.hanpyeon.academyapi.online.domain.OnlineCourseStudents;
import com.hanpyeon.academyapi.online.domain.OnlineCourseTeacher;
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
    private final OnlineCourseMapper onlineCourseMapper;

    @Transactional
    public void addOnlineCourse(@Validated final AddOnlineCourseCommand addOnlineCourseCommand) {
        final OnlineCourseDomain onlineCourseDomain = toDomain(addOnlineCourseCommand);

        final OnlineCourse onlineCourse = new OnlineCourse(
                this.findTeacher(onlineCourseDomain.getOnlineCourseTeacherId()),
                onlineCourseDomain.getOnlineCourseName()
        );

        onlineCourseRepository.save(onlineCourse);
        saveOnlineCourseStudents(onlineCourseDomain.getStudentIds(), onlineCourse);

        log.info("online Course Created : " + addOnlineCourseCommand);
    }

    private OnlineCourseDomain toDomain(final AddOnlineCourseCommand addOnlineCourseCommand) {
        final OnlineCourseName onlineCourseName = onlineCourseMapper.toCourseName(
                addOnlineCourseCommand.onlineCourseName());
        final Member teacher = findTeacher(addOnlineCourseCommand.teacherId());
        final OnlineCourseTeacher onlineCourseTeacher = onlineCourseMapper.toOnlineCourseTeacher(teacher.getId());
        final List<Member> students = findStudents(addOnlineCourseCommand.students());
        final OnlineCourseStudents onlineCourseStudents = onlineCourseMapper.toCourseStudents(students.stream()
                .map(Member::getId)
                .toList()
        );

        return onlineCourseMapper.toOnlineCourse(onlineCourseName, onlineCourseStudents, onlineCourseTeacher);
    }

    private void saveOnlineCourseStudents(final List<Long> studentIds, final OnlineCourse onlineCourse) {
        if (studentIds.isEmpty()) {
            return;
        }
        final List<OnlineStudent> onlineStudents = mapToOnlineStudents(findStudents(studentIds), onlineCourse);
        onlineStudentRepository.saveAll(onlineStudents);
    }

    private Member findTeacher(final Long memberId) {
        return memberRepository.findMemberByIdAndRoleAndRemovedIsFalse(memberId, Role.TEACHER)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
    }

    private List<Member> findStudents(final List<Long> studentIds) {
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
