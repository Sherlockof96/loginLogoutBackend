package com.auth.kavish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.kavish.model.Profile;
import com.auth.kavish.service.SignUp;


@RestController
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    SignUp signUp;

    @PostMapping("/")
    public ResponseEntity signUp(@RequestBody Profile user) {
        // Handle signup logic here
        boolean status = signUp.add(user);

        if(status)
        {
            return ResponseEntity.ok(HttpStatus.OK);
        }

        return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get")
    public ResponseEntity signUps() {
        // Handle signup logic here
        return ResponseEntity.ok(HttpStatus.OK);
    }
}


