package com.hanpyeon.academyapi.course.adapter.out;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "MEMO")
@NoArgsConstructor
@Getter
class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MEMO_ID")
    private Long id;
    @CreationTimestamp
    @Column(name = "REGISTERED_DATE")
    private LocalDateTime registeredDateTime;
    @Column(name = "TARGET_DATE")
    private LocalDate targetDate;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
    @Column(name = "PROGRESSED")
    private String progressed;
    @Column(name = "HOMEWORK")
    private String homework;

/*
    // 영상 로직
    @OneToMany(mappedBy = "")
    private List<Image> conceptMedia;
    @OneToMany(mappedBy = "")
    private List<Image> courseMedia;
*/

    Memo(final Course course, final LocalDate targetDate, final String progressed, final String homework) {
        this.course = course;
        this.targetDate = targetDate;
        this.progressed = progressed;
        this.homework = homework;
    }
}
