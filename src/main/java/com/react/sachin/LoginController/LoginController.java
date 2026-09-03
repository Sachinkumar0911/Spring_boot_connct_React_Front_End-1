package com.react.sachin.LoginController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.sachin.JWTFeature.JwtService;
import com.react.sachin.JWTFeature.LoginResponse;
import com.react.sachin.LoginModel.LoginModel;
import com.react.sachin.LoginModel.UserModel;
import com.react.sachin.LoginRepo.UserRepository;

@RestController
@RequestMapping("/login")
//@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
   
    @Autowired
    private UserRepository userRepository; 

    @Autowired
private PasswordEncoder passwordEncoder;
@Autowired
private JwtService jwtService;

@PostMapping
    public ResponseEntity<?> login(@RequestBody LoginModel request) {

        System.out.println("Username: " + request.getUsername());
        System.out.println("Password: " + request.getPassword());

         Optional<UserModel> userOptional =
                userRepository.findByUsername(request.getUsername());

            //System.out.println("userOptional -"+userOptional.get().getName());   
            
             // Username not found
            // System.out.println("userOptional-----"+userOptional);
        if (userOptional.isEmpty()) {
System.out.println("------------------------");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
        UserModel user = userOptional.get(); 
       System.out.println("user.getPassword() -"+user.getPassword()+ " 3-- "+request.getPassword()); 
//if (!user.getPassword().equals(request.getPassword())) {
if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
System.out.println("-------------11111111111111111-----------");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
String token = jwtService.generateToken(user.getUsername(),"User");

LoginResponse response = new LoginResponse("Login successful",token,user.getUsername(),user.getName(),user.getRole());

System.out.println("1 "+ResponseEntity.ok(response));
        return ResponseEntity.ok(response);

        //return ResponseEntity.ok(response);
    }

}
