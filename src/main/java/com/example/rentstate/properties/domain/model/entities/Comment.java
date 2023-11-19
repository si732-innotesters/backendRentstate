package com.example.rentstate.properties.domain.model.entities;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.api.resource.commentResource.CommentResource;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
@Entity
@Table(name ="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 200, message = "max 200 characters")
    @NotNull
    @NotBlank
    private String content;

    public Comment(User user, Post post, CommentResource commentResource) {
        this.user = user;
        this.post = post;
        this.content = commentResource.getContent();
    }
}
