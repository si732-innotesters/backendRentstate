package com.example.rentstate.forums.domain.model.aggregates;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="forumAnswers")
public class ForumAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long questionId;

    @NotNull
    private Long authorId;

    @NotNull
    private String content;

    public ForumAnswer(Long questionId, Long authorId, String content){
        this.questionId=questionId;
        this.authorId=authorId;
        this.content=content;
    }
}
