package com.hanpyeon.academyapi.online.service;

import com.hanpyeon.academyapi.course.application.dto.TeacherPreview;
import com.hanpyeon.academyapi.online.dao.OnlineCourseRepository;
import com.hanpyeon.academyapi.online.dto.OnlineCoursePreview;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryOnlineCourseService {
    private final OnlineCourseRepository onlineCourseRepository;

    @Transactional(readOnly = true)
    public List<OnlineCoursePreview> queryAll() {
        return onlineCourseRepository.findAll().stream()
                .map(onlineCourse -> new OnlineCoursePreview(onlineCourse.getCourseName(), onlineCourse.getId(), onlineCourse.getOnlineStudents().size(), new TeacherPreview(onlineCourse.getTeacher().getName(), onlineCourse.getTeacher().getId())))
                .toList();
    }
}
