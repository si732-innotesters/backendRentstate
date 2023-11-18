package com.example.rentstate.profiles.infrastructure.persistence.jpa.repositories;

import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.model.valueobjects.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByAccount_Email(String email);

    Optional<User>findByAccount(Account account);

}