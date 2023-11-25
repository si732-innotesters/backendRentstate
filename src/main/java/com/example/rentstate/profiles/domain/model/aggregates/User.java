package com.example.rentstate.profiles.domain.model.aggregates;

import com.example.rentstate.profiles.api.resource.userresource.UpdateUserResource;
import com.example.rentstate.profiles.domain.model.valueobjects.Role;
import com.example.rentstate.security.auth.resource.RegisterRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2, max=50, message = "The name must have at least 2 characters and a maximum of 50")
    private String name;

    @NotNull
    @Size(min=2, max=50, message = "The last name must have at least 2 characters and a maximum of 50")
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Integer age = 18;

    @NotNull
    private String gender="reserved";

    @NotNull
    private String description = "Here you can write a description about yourself.";

    @NotNull
    private Boolean isPremium = false;

    private String photoUrl = "";

    private Role role;

   public User(RegisterRequest registerRequest){
        name = registerRequest.getName();
        lastName = registerRequest.getLastName();
        email = registerRequest.getUsername();
        username = registerRequest.getUsername();
        password = registerRequest.getPassword();
        role=Role.USER;
    }

    public void updateUser(UpdateUserResource resource){
        this.name = resource.getName();
        this.lastName = resource.getLastName();
        this.age = resource.getAge();
        this.gender = resource.getGender();
        this.description = resource.getDescription();
        this.isPremium = resource.getIsPremium();
        this.photoUrl = resource.getPhotoUrl();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}