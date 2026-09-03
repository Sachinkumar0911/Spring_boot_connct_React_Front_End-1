package com.react.sachin.RegistrationFolder.RegistrationRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

import com.react.sachin.RegistrationFolder.RegistrationEntity.RegistrationEntity;

@Repository
public interface RegistrationRepository  extends JpaRepository<RegistrationEntity,Long>{
     Optional<RegistrationEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
