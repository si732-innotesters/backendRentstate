package com.example.rentstate.messages.api.rest;

import com.example.rentstate.messages.api.resource.CreateMessageResource;
import com.example.rentstate.messages.api.resource.ResponseMessageResource;
import com.example.rentstate.messages.domain.model.entities.Message;
import com.example.rentstate.messages.domain.service.MessageService;
import com.example.rentstate.profiles.api.resource.userresource.ResourceUserResponse;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/api/v1/messages", produces = "application/json")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseMessageResource> createMessage(@RequestBody CreateMessageResource messageResource) {

        if(messageResource.getAuthorId() == messageResource.getRecipientId()){
            throw new IllegalArgumentException ("You can't send messages to yourself");
        }

        Optional<User> author = userService.getById(messageResource.getAuthorId());
        Optional<User> recipient = userService.getById(messageResource.getRecipientId());

        if (author.isEmpty() || recipient.isEmpty()) {
            throw new IllegalArgumentException ("The author or recipient does not exist");
        }

        Message newMessage = new Message(author.get(), recipient.get(),
                messageResource.getContent()
        );

        Optional<Message> createdMessage = messageService.create(newMessage);

        if (createdMessage.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body( new ResponseMessageResource(createdMessage.get()) );
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{recipientId}/{authorId}")
    public List<ResponseMessageResource> getAllMessagesByRecipientId(
            @PathVariable Long recipientId, @PathVariable Long authorId) {

        if(recipientId == authorId)
            throw new IllegalArgumentException("You don't have conversations with yourself");

        Optional<User> recipient = userService.getById(recipientId);
        Optional<User> author = userService.getById(authorId);
        if (recipient.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("the user was not found");
        }

        List<Message> conversation = messageService.getConversationByRecipientAndAuthor(
                recipient.get(),author.get());

        List<ResponseMessageResource> responseMessages = conversation.stream()
                .map(message -> new ResponseMessageResource(message))
                .collect(Collectors.toList());

        return responseMessages;
    }

    @GetMapping("/chats/{recipientId}")
    public List<ResourceUserResponse> getNameChats(@PathVariable Long recipientId){
        Optional<User> user = userService.getById(recipientId);
        if (user.isEmpty()){
            throw new IllegalArgumentException("This user does not exist");
        }
        List<Message> messages = messageService.getChats(user.get());

        List<ResourceUserResponse> chats = messages.stream()
                .map(message->new ResourceUserResponse(message.getAuthor()))
                .distinct().collect(Collectors.toList());

        return chats;
    }

}