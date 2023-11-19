package com.example.rentstate.properties.domain.model.entities;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
@Entity
@Table(name ="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Reservation(User user, Property property) {
        this.user = user;
        this.property = property;
    }

}
