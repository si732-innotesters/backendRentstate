package com.example.rentstate.forums.api.resource.forumAnswerResource;

import com.example.rentstate.forums.domain.model.entities.ForumAnswer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdateForumAnswerResource {

    private String content;

    public UpdateForumAnswerResource(ForumAnswer forumAnswer) {
        this.content=forumAnswer.getContent();

    }
}
