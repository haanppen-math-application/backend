package com.hanpyeon.academyapi.online.dao;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
public class OnlineCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<OnlineCategory> childCategories = new ArrayList<>();

    @Column(name = "categoryName", nullable = true)
    private String name;

    @CreationTimestamp
    private LocalDateTime creationTime;

    public OnlineCategory(final OnlineCategory parentCategory, final String name) {
        if (Objects.nonNull(parentCategory)) {
            parentCategory.childCategories.add(this);
        }
        this.parentCategory = parentCategory;
        this.name = name;
    }
}
