package com.hanpyeon.academyapi.dir.dao;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(indexes = @Index(name = "idx_path", columnList = "directory_path"))
@NoArgsConstructor
@Getter
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "directory_onwer", nullable = false)
    private Member owner;
    @Column(name = "directory_path", nullable = false, unique = true)
    private String path;
    @Column(name = "directory_opened", nullable = false)
    private Boolean canViewByEveryone;
    @CreationTimestamp
    private LocalDateTime createdTime;

    public Directory(Member owner, String path, Boolean canViewByEveryone) {
        this.owner = owner;
        this.path = path;
        this.canViewByEveryone = canViewByEveryone;
    }
}
