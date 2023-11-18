package com.example.rentstate.messages.shared.services.implementation;

import com.example.rentstate.messages.domain.model.entities.Message;
import com.example.rentstate.messages.domain.service.MessageService;
import com.example.rentstate.messages.infrastructure.persistence.jpa.repositories.MessageRepository;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> create(Message message) {
        return Optional.of(messageRepository.save(message));
    }

    @Override
    public List<Message> getConversationByRecipientAndAuthor(User recipient, User author) {
        List<Message> messageList1 = messageRepository.getAllByRecipientAndAuthor(recipient, author);
        List<Message> messageList2 = messageRepository.getAllByRecipientAndAuthor(author, recipient);

        List<Message> conversation = new ArrayList<>(messageList1);
        conversation.addAll(messageList2);

        conversation.sort(Comparator.comparing(Message::getCreatedAt));
        return conversation;

    }

    @Override
    public List<Message> getChats(User recipient) {

        List<Message> messages = messageRepository.getAllByRecipient(recipient);
        return messages;
    }

}