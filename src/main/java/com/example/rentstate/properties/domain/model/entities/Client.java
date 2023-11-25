package com.example.rentstate.properties.domain.model.entities;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.properties.domain.model.entities.Property;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property rentedProperty;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @ManyToOne
    @JoinColumn(name = "lessor_id", nullable = false) //dueño
    private User lessor;

    private Boolean isAsset = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    public Client(Property rentedProperty, User tenant) {
        this.rentedProperty = rentedProperty;
        this.tenant = tenant;
        this.lessor = rentedProperty.getAuthor();
        this.isAsset=true;
        this.createdAt=new Date();
    }

}
