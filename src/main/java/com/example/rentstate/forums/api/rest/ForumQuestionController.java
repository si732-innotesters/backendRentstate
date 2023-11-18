package com.example.rentstate.forums.api.rest;

import com.example.rentstate.forums.domain.service.ForumQuestionService;
import com.example.rentstate.profiles.domain.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/ForumQuestion", produces = "application/json")
public class ForumQuestionController {

    private final ForumQuestionService forumQuestionService;
    private final ModelMapper modelMapper;

    public ForumQuestionController(ForumQuestionService forumQuestionService, ModelMapper modelMapper) {
        this.forumQuestionService = forumQuestionService;
        this.modelMapper = modelMapper;
    }
}
