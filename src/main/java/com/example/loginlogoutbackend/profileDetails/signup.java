package com.example.loginlogoutbackend.profileDetails;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class signup {

    @GetMapping("/signup/{username}/{password}/{email}")
    public Boolean index(@PathVariable String username, @PathVariable String password, @PathVariable String email) {
        return true;
    }
    
}
