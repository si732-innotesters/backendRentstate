package com.example.rentstate.forums.api.rest;

import com.example.rentstate.forums.api.resource.forumAnswerResource.ResourceForumAnswerResponse;
import com.example.rentstate.forums.api.resource.forumQuestionResource.CreateForumQuestionResource;
import com.example.rentstate.forums.api.resource.forumQuestionResource.ResourceForumQuestionResponse;
import com.example.rentstate.forums.domain.model.entities.ForumQuestion;
import com.example.rentstate.forums.domain.service.ForumQuestionService;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/forum-questions", produces = "application/json")
public class ForumQuestionController {

    private final ForumQuestionService forumQuestionService;
    private final UserService userService;

    public ForumQuestionController(ForumQuestionService forumQuestionService, ModelMapper modelMapper, UserService userService) {
        this.forumQuestionService = forumQuestionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResourceForumQuestionResponse> addQuestion(
            @RequestBody CreateForumQuestionResource createForumQuestionResource){

        Optional<User> author = userService.getById(createForumQuestionResource.getAuthorId());

        if(author.isEmpty())
            throw new IllegalArgumentException("The author user does not exist");

        ForumQuestion newForumQuestion = new ForumQuestion(author.get(), createForumQuestionResource);

        Optional<ForumQuestion> forumQuestion= forumQuestionService.create(newForumQuestion);

        if (forumQuestion.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body( new ResourceForumQuestionResponse(forumQuestion.get()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public List<ResourceForumQuestionResponse> getAllQuestion(){
        List<ForumQuestion>questions = forumQuestionService.getAll();

        List<ResourceForumQuestionResponse> questionResponses = questions.stream()
                .map(question -> new ResourceForumQuestionResponse(question))
                .collect(Collectors.toList());
        return questionResponses;
    }

    @GetMapping("{forumQuestionId}")
    public ResponseEntity<ResourceForumQuestionResponse>getQuestionById(@PathVariable Long forumQuestionId){
        Optional<ForumQuestion> forumQuestion = forumQuestionService.getById(forumQuestionId);

        if(forumQuestion.isEmpty())
            throw new IllegalArgumentException("Question with id " +forumQuestionId+" does not exist");

        ResourceForumQuestionResponse forumQuestionResponse = new ResourceForumQuestionResponse(
                forumQuestion.get());

        return ResponseEntity.ok(forumQuestionResponse);

    }


    @DeleteMapping("{forumQuestionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long forumQuestionId){
        forumQuestionService.delete(forumQuestionId);
        return ResponseEntity.noContent().build();
    }

}
