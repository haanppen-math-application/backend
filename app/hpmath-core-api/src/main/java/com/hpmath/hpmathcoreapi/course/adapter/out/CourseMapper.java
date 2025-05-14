package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.client.member.MemberClient;
import com.hpmath.hpmathcoreapi.course.domain.Student;
import com.hpmath.hpmathcoreapi.course.domain.Teacher;
import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcoreapi.course.entity.CourseStudent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CourseMapper {
    private final MemberClient memberClient;
    com.hpmath.hpmathcoreapi.course.domain.Course mapToCourseDomain(final Course courseEntity) {
        return com.hpmath.hpmathcoreapi.course.domain.Course.loadByEntity(
                courseEntity.getId(),
                courseEntity.getCourseName(),
                mapToStudent(mapToStudentIds(courseEntity.getCourseStudents())),
                mapToTeacher(courseEntity.getTeacherId()));
    }

    private Teacher mapToTeacher(final Long teacherId) {
        return Teacher.from(memberClient.getMemberDetail(teacherId));
    }

    private List<Student> mapToStudent(final List<Long> studentIds) {
        return studentIds.parallelStream()
                .map(memberClient::getMemberDetail)
                .map(Student::from)
                .toList();
    }

    private List<Long> mapToStudentIds(final List<CourseStudent> courseStudents) {
        return courseStudents.stream()
                .map(CourseStudent::getStudentId)
                .toList();

    }
}
