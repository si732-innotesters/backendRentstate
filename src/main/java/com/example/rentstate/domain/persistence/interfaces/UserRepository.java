package com.example.rentstate.domain.persistence.interfaces;

import com.example.rentstate.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

}
