package com.example.rentstate.messages.domain.model.entities;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name ="messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(nullable = false, length = 1000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    public Message(){}
    public Message(User author, User recipient, String content) {
        this.author = author;
        this.recipient = recipient;
        this.content = content;
        this.createdAt = new Date();
    }
}
