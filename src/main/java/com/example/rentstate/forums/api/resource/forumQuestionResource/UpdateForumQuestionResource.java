package com.example.rentstate.forums.api.resource.forumQuestionResource;

import com.example.rentstate.forums.domain.model.aggregates.ForumQuestion;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdateForumQuestionResource {

    private String question;

    public UpdateForumQuestionResource(ForumQuestion forumQuestion) {
        this.question=forumQuestion.getQuestion();


    }
}
