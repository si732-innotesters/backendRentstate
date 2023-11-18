package com.example.rentstate.forums.api.resource.forumQuestionResource;

import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdateForumQuestionResource {

    private Long id;
    private String question;

}
