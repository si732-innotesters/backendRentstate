package com.example.rentstate.forums.api.rest;

import com.example.rentstate.forums.api.resource.forumAnswerResource.CreateForumAnswerResource;
import com.example.rentstate.forums.api.resource.forumAnswerResource.ResourceForumAnswerResponse;
import com.example.rentstate.forums.domain.model.entities.ForumAnswer;
import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import com.example.rentstate.forums.domain.service.ForumAnswerService;
import com.example.rentstate.forums.domain.service.ForumQuestionService;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/forum-answers", produces = "application/json")
public class ForumAnswerController {

    private final ForumAnswerService answerService;
    private final UserService userService;
    private final ForumQuestionService forumQuestionService;


    public ForumAnswerController(ForumAnswerService answerService,
                                 UserService userService, ForumQuestionService forumQuestionService) {
        this.answerService = answerService;
        this.userService = userService;
        this.forumQuestionService = forumQuestionService;
    }


    @PostMapping
    public ResponseEntity<ResourceForumAnswerResponse> addAnswer(
            @RequestBody CreateForumAnswerResource createForumAnswerResource)
    {
        Optional<User>author = userService.getById(createForumAnswerResource.getAuthorId());
        Optional<ForumQuestion>question = forumQuestionService.getById(createForumAnswerResource.getQuestionId());

        if(author.isEmpty() || question.isEmpty())
            throw new IllegalArgumentException("The question or user does not exist");

        ForumAnswer newForumAnswer = new ForumAnswer(author.get(), question.get(), createForumAnswerResource);

        Optional<ForumAnswer> answer = answerService.create(newForumAnswer);

        if (answer.isPresent()) {
            ResourceForumAnswerResponse answerResponse = new ResourceForumAnswerResponse (answer.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(answerResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{questionId}")
    public List<ResourceForumAnswerResponse> getAllAnswersByQuestionId(@PathVariable Long questionId) {
        Optional<ForumQuestion> question = forumQuestionService.getById(questionId);
        if (question.isEmpty()) {
            throw new IllegalArgumentException("The question does not exist");
        }

        List<ForumAnswer> answers = answerService.findAllByQuestion(question.get());

        List<ResourceForumAnswerResponse> responseList = answers.stream()
                .map(answer -> new ResourceForumAnswerResponse(answer))
                .collect(Collectors.toList());

        return responseList;
    }

}
