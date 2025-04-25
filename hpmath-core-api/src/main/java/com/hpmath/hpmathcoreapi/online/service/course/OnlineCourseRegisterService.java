package com.hpmath.hpmathcoreapi.online.service.course;

import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourseRepository;
import com.hpmath.hpmathcoreapi.online.dao.OnlineStudent;
import com.hpmath.hpmathcoreapi.online.dao.OnlineStudentRepository;
import com.hpmath.hpmathcoreapi.online.domain.OnlineCourseDomain;
import com.hpmath.hpmathcoreapi.online.dto.AddOnlineCourseCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineCourseRegisterService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final OnlineStudentRepository onlineStudentRepository;
    private final OnlineCourseMapper onlineCourseMapper;
    private final MemberLoader memberLoader;

    @Transactional
    public void addOnlineCourse(final AddOnlineCourseCommand addOnlineCourseCommand) {
        final OnlineCourseDomain onlineCourseDomain = onlineCourseMapper.toCourseDomain(addOnlineCourseCommand);
        final OnlineCourse onlineCourse = onlineCourseMapper.toCourse(onlineCourseDomain);

        onlineCourseRepository.save(onlineCourse);
        saveOnlineCourseStudents(onlineCourseDomain.getStudentIds(), onlineCourse);

        log.info("online Course Created : " + addOnlineCourseCommand);
    }

    private void saveOnlineCourseStudents(final List<Long> studentIds, final OnlineCourse onlineCourse) {
        if (studentIds.isEmpty()) {
            return;
        }
        final List<OnlineStudent> onlineStudents = onlineCourseMapper
                .toOnlineStudents(
                        onlineCourse,
                        memberLoader.findStudents(studentIds)
                );
        onlineStudentRepository.saveAll(onlineStudents);
    }
}
