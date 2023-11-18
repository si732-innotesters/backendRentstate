package com.example.rentstate.forums.api.resource.forumQuestionResource;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class CreateForumQuestionResource {
    private Long authorId;
    private String question;
}
