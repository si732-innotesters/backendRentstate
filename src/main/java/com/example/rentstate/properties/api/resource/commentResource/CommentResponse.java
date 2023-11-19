package com.example.rentstate.properties.api.resource.commentResource;

import com.example.rentstate.properties.domain.model.entities.Comment;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long userId;
    private Long postId;
    private String content;
    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
    }
}
