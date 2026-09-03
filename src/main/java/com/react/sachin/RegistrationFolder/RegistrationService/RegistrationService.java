package com.react.sachin.RegistrationFolder.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.react.sachin.RegistrationFolder.RegistrationEntity.RegistrationEntity;
import com.react.sachin.RegistrationFolder.RegistrationModel.RegisterRequest;
import com.react.sachin.RegistrationFolder.RegistrationRepo.RegistrationRepository;

@Service
public class RegistrationService {
   
    @Autowired
    private RegistrationRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegistrationEntity registerUser(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        RegistrationEntity user = new RegistrationEntity();

        user.setUsername(request.getUsername());
        user.setPassword(
            passwordEncoder.encode(request.getPassword())
        );
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole("USER");
        user.setEnabled(true);

        return userRepository.save(user);
    } 
}
