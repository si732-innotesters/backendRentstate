package com.example.rentstate.forums.api.resource.forumAnswerResource;

import com.example.rentstate.forums.domain.model.entities.ForumAnswer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Getter
@NoArgsConstructor
public class ResourceForumAnswerResponse {

    private Long questionId;
    private String authorName;
    private String content;
    private Date createdAt;

    public ResourceForumAnswerResponse(ForumAnswer forumAnswer) {
        this.questionId=forumAnswer.getQuestion().getId();
        this.authorName=forumAnswer.getAuthor().getName()+ " "+forumAnswer.getAuthor().getLastName();
        this.content=forumAnswer.getContent();
        this.createdAt=forumAnswer.getCreatedAt();
    }

}
