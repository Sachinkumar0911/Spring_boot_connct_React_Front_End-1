package com.react.sachin.RegistrationFolder.RegistrationController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.sachin.RegistrationFolder.RegistrationEntity.RegistrationEntity;
import com.react.sachin.RegistrationFolder.RegistrationModel.RegisterRequest;
import com.react.sachin.RegistrationFolder.RegistrationService.RegistrationService;

@RestController
@RequestMapping("/Registration")
@CrossOrigin(origins = "http://localhost:5173")
public class RegistrationController {

    @Autowired
    private RegistrationService registartionService;

   @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        try {

            RegistrationEntity user = registartionService.registerUser(request);
System.out.println("user "+user);
            return ResponseEntity.ok(
                "Employee registered successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
