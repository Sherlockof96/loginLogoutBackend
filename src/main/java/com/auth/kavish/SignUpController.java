package com.auth.kavish;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/signup")
public class SignUpController {

    @PostMapping("/")
    public ResponseEntity signUp(@RequestBody String user) {
        // Handle signup logic here
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity signUps() {
        // Handle signup logic here
        return ResponseEntity.ok(HttpStatus.OK);
    }
}


