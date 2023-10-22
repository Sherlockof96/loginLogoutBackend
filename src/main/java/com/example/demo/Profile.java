package com.example.demo;

import org.springframework.data.annotation.Id;



public class Profile {
    @Id
    private Integer id;

    private String email;

    private String pass;

    private String username;

    public Profile() {
    }

    public Profile(Integer id, String email, String pass, String username) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.username = username;
    }   

    // Getters and Setters
    public Integer getId() {
        return id;
    }   

    public void setId(Integer id) {
        this.id = id;
    }   

    public String getEmail() {
        return email;
    }   

    public String getPass() {
        return pass;
    }   

    public String getUsername() {
        return username;
    }   

    public String toString() {
        return "Profile [id=" + id + ", email=" + email + ", pass=" + pass + ", username=" + username + "]";
    }

    public String setUsername(String username) {
        return this.username = username;
    }

    public String setPass(String pass) {
        return this.pass = pass;
    }

    public String setEmail(String email) {
        return this.email = email;
    }
}