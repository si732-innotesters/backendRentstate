package com.example.rentstate.messages.api.resource;

import com.example.rentstate.messages.domain.model.entities.Message;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
public class ResponseMessageResource {

    private Long authorId;
    private Long recipientId;
    private String content;
    private Date createdAt;

    public ResponseMessageResource(Message message){
        this.content = message.getContent();
        this.authorId = message.getAuthor().getId();
        this.recipientId = message.getRecipient().getId();
        this.createdAt = message.getCreatedAt();
    }
}