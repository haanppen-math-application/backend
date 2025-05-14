package com.hpmath.domain.directory.dao;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

@Entity
@ToString
@Getter
@NoArgsConstructor
@Table(name = "directory_media", indexes = @Index(name = "idx_directoryId_mediaSrc", columnList = "directory_id, media_src"))
@SQLDelete(sql = "UPDATE directory_media SET directory_id = null WHERE id = ?")
public class DirectoryMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directory_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Directory directory;

    @Column(name = "media_src")
    private String mediaSrc;

    public DirectoryMedia(Directory directory, String mediaSrc) {
        this.directory = directory;
        this.mediaSrc = mediaSrc;
    }
}
