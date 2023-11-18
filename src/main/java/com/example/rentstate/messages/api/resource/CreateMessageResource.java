package com.example.rentstate.messages.api.resource;

import lombok.Data;
import lombok.Getter;


@Data
@Getter
public class CreateMessageResource {

    private Long authorId;
    private Long recipientId;
    private String content;

}