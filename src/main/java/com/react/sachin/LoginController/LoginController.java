package com.react.sachin.LoginController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.sachin.LoginModel.LoginModel;
import com.react.sachin.LoginModel.UserModel;
import com.react.sachin.LoginRepo.UserRepository;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
   
    @Autowired
    private UserRepository userRepository; 

@PostMapping
    public ResponseEntity<?> login(@RequestBody LoginModel request) {

        System.out.println("Username: " + request.getUsername());
        System.out.println("Password: " + request.getPassword());

         Optional<UserModel> userOptional =
                userRepository.findByUsername(request.getUsername());

            //System.out.println("userOptional -"+userOptional.get().getName());   
            
             // Username not found
        if (userOptional.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
        UserModel user = userOptional.get(); 
if (!user.getPassword().equals(request.getPassword())) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
System.out.println("1 "+ResponseEntity.ok("Login successful"));
        return ResponseEntity.ok("Login successful");

        //return ResponseEntity.ok("Login successful");
    }

}
