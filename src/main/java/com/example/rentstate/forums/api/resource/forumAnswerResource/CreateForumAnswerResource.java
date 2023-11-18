package com.example.rentstate.forums.api.resource.forumAnswerResource;

import lombok.*;

@Data
@Getter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateForumAnswerResource {
    private Long authorId;
    private Long questionId;
    private String content;
}
