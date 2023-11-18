package com.example.rentstate.messages.domain.service;

import com.example.rentstate.messages.domain.model.entities.Message;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    Optional<Message> create(Message message);
    List<Message> getConversationByRecipientAndAuthor(User recipient, User author);

    List<Message> getChats(User recipient);

}