package com.example.rentstate.messages.infrastructure.persistence.jpa.repositories;

import com.example.rentstate.messages.domain.model.entities.Message;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> getAllByRecipientAndAuthor(User recipient, User author);
    List<Message> getAllByRecipient(User recipient);

}