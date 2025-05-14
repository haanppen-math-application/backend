package com.hpmath.domain.directory.dao;

import com.hpmath.hpmathmediadomain.media.entity.Media;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(indexes = @Index(name = "idx_path", columnList = "directory_path"))
@NoArgsConstructor
@Getter
@ToString
@Slf4j
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "directory_owner", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Column(name = "owner")
    private Long ownerId;

    @Column(name = "directory_path", nullable = false, unique = true)
    private String path;

    @Column(name = "directory_viewable", nullable = false)
    private Boolean canViewByEveryone;

    @Column(name = "directory_addable", nullable = false)
    private Boolean canAddByEveryone;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "directory", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<DirectoryMedia> medias = new ArrayList<>();

    public Directory(Long owner, String path, Boolean canModifyByEveryone, Boolean canViewByEveryone) {
        this.ownerId = owner;
        this.path = path;
        this.canAddByEveryone = canModifyByEveryone;
        this.canViewByEveryone = canViewByEveryone;
    }

    public void addMedia(final Media media) {
        this.medias.add(new DirectoryMedia(this, media));
    }

    public void setPath(final String newPath) {
        this.path = newPath;
    }
}
