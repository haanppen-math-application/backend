package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.domain.member.Member;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.board.exception.InvalidTargetException;
import com.hpmath.hpmathcoreapi.course.domain.Student;
import com.hpmath.hpmathcoreapi.course.domain.Teacher;
import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcore.ErrorCode;
import org.springframework.stereotype.Component;

@Component
class CourseMapper {
    Teacher mapToTeacher(final Member member) {
        if (member.getRole().equals(Role.STUDENT)) {
            throw new InvalidTargetException("학생은 반을 관리할 수 없습니다",ErrorCode.INVALID_MEMBER_TARGET);
        }
        return new Teacher(member.getId(), member.getName());
    }

    Student mapToStudent(final Member member) {
        if (member.getRole().equals(Role.STUDENT)) {
            return new Student(member.getId(), member.getName(), member.getGrade());
        }
        throw new InvalidTargetException("학생만 반에 등록될 수 있습니다", ErrorCode.INVALID_MEMBER_TARGET);
    }

    com.hpmath.hpmathcoreapi.course.domain.Course mapToCourseDomain(final Course courseEntity) {
        return com.hpmath.hpmathcoreapi.course.domain.Course.loadByEntity(
                courseEntity.getId(),
                courseEntity.getCourseName(),
                courseEntity.getStudents().stream()
                        .map(courseStudent -> mapToStudent(courseStudent.getMember()))
                        .toList(),
                mapToTeacher(courseEntity.getTeacher()));
    }
}
