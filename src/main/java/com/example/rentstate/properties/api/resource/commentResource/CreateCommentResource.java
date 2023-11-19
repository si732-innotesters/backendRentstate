package com.example.rentstate.properties.api.resource.commentResource;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CreateCommentResource {
    private Long authorId;
    private Long postId;
    private String content;
}
