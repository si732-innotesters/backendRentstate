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
    private String nameAuthor;
    private String content;
    public CommentResponse(Comment comment) {
        this.nameAuthor = comment.getAuthor().getName()+" "+comment.getAuthor().getLastName();
        this.content = comment.getContent();
    }
}
