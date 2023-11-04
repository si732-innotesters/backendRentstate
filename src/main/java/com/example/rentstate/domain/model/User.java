package com.example.rentstate.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2, max=50)
    private String name;

    @NotNull
    @Size(min=2, max=50)
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Integer age = 18;

    @NotNull
    private String gender="";

    @NotNull
    private String description = "";

    @Max(value = 5)
    private Float rankPoints = 5.0f;
}
