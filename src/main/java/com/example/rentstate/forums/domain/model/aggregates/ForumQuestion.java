package com.example.rentstate.forums.domain.model.aggregates;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="forumQuestions")
public class ForumQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long authorId;

    @NotNull
    private String question;

    public ForumQuestion(Long authorId, String question){
        this.authorId=authorId;
        this.question=question;
    }
}
