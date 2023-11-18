package com.example.rentstate.forums.api.resource.forumAnswerResource;

import com.example.rentstate.forums.domain.model.aggregates.ForumAnswer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ResourceForumAnswerResponse {

    private Long id;
    private Long questionId;
    private Long authorId;
    private String content;

    public ResourceForumAnswerResponse(ForumAnswer forumAnswer) {
        this.id = forumAnswer.getId();
        this.questionId=forumAnswer.getQuestionId();
        this.authorId=forumAnswer.getAuthorId();
        this.content=forumAnswer.getContent();

    }

}
