package com.hpmath.domain.board.comment.entity;

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
@Table(name = "comment_image", indexes = @Index(name = "commentId_imageSrc_idx", columnList = "comment_id, image_src"))
@SQLDelete(sql = "UPDATE comment_image SET comment_id = null WHERE id = ?")
@NoArgsConstructor
public class CommentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Comment comment;

    @Column(name = "image_src", nullable = false)
    private String imageSrc;

    private CommentImage(Comment comment, String imageSrc) {
        this.comment = comment;
        this.imageSrc = imageSrc;
    }

    public static CommentImage of(Comment comment, String imageSrc) {
        return new CommentImage(comment, imageSrc);
    }
}
