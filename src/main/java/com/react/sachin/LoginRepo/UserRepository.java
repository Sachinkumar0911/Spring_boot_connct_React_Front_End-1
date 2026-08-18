package com.react.sachin.LoginRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.react.sachin.LoginModel.UserModel;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByUsername(String username);
}
