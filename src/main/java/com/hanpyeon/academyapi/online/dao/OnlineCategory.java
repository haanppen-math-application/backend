package com.hanpyeon.academyapi.online.dao;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

@Entity
@NoArgsConstructor
@Getter
public class OnlineCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "onlineCategory", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<OnlineCourse> onlineCourses;
    @Column(nullable = false, unique = true)
    private String level1;
    @Column(nullable = true)
    private String level2;
    @Column(nullable = true)
    private String level3;
    @CreationTimestamp
    private LocalDateTime creationTime;

    public OnlineCategory(@NotNull final String level1, final String level2, final String level3) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
    }
}
