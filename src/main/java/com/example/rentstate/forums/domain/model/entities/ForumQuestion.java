package com.example.rentstate.forums.domain.model.entities;

import com.example.rentstate.forums.api.resource.forumQuestionResource.CreateForumQuestionResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="forum_questions")
public class ForumQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @NotNull
    @NotBlank
    private String question;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    public ForumQuestion(User author, CreateForumQuestionResource createForumQuestionResource){
        this.author = author;
        this.question = createForumQuestionResource.getQuestion();
        this.createdAt = new Date();
    }

}
