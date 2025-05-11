package com.hpmath.hpmathcoreapi.dir.dao;

import com.hpmath.domain.member.Member;
import com.hpmath.hpmathmediadomain.media.entity.Media;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
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
    @ManyToOne
    @JoinColumn(name = "directory_owner", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member owner;
    @Column(name = "directory_path", nullable = false, unique = true)
    private String path;
    @Column(name = "directory_viewable", nullable = false)
    private Boolean canViewByEveryone;
    @Column(name = "directory_addable", nullable = false)
    private Boolean canAddByEveryone;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @OneToMany
    private List<Media> medias;

    public Directory(Member owner, String path, Boolean canModifyByEveryone, Boolean canViewByEveryone) {
        this.owner = owner;
        this.path = path;
        this.canAddByEveryone = canModifyByEveryone;
        this.canViewByEveryone = canViewByEveryone;
    }

    public void add(final Media media) {
        medias.add(media);
    }

    public void setPath(final String newPath) {
        this.path = newPath;
    }
}
