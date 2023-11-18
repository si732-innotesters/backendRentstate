package com.example.rentstate.forums.api.rest;

import com.example.rentstate.forums.domain.model.aggregates.ForumAnswer;
import com.example.rentstate.forums.domain.service.ForumAnswerService;
import com.example.rentstate.profiles.domain.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/ForumAnswer", produces = "application/json")
public class ForumAnswerController {

    private final ForumAnswerService forumAnswerService;
    private final ModelMapper modelMapper;

    public ForumAnswerController(ForumAnswerService forumAnswerService, ModelMapper modelMapper) {
        this.forumAnswerService = forumAnswerService;
        this.modelMapper = modelMapper;
    }

}
