package com.hpmath.domain.board.entity;

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
@Table(name = "question_image", indexes = @Index(name = "questionId_imageSrc_idx", columnList = "question_id, image_src"))
@SQLDelete(sql = "UPDATE question_image SET question_id = null WHERE id = ?")
@NoArgsConstructor
public class QuestionImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Question question;

    @Column(name = "image_src",nullable = false)
    private String imageSrc;

    private QuestionImage(Question question, String imageSrc) {
        this.question = question;
        this.imageSrc = imageSrc;
    }

    public static QuestionImage of(Question question, String imageSrc) {
        return new QuestionImage(question, imageSrc);
    }
}
