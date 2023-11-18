package com.example.rentstate.forums.domain.model.entities;

import com.example.rentstate.forums.api.resource.forumAnswerResource.CreateForumAnswerResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="forum_answers")
public class ForumAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    private User author;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @NotNull
    private ForumQuestion question;

    @NotNull
    @NotBlank
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    public ForumAnswer(User author, ForumQuestion question, CreateForumAnswerResource forumAnswerResource){
        this.content = forumAnswerResource.getContent();
        this.author=author;
        this.question=question;
        this.createdAt = new Date();
    }
}
