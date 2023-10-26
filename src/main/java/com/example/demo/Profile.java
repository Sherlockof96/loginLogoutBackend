package com.example.demo;

import org.springframework.data.annotation.Id;



public class Profile {
    @Id
    private Integer userhash;

    private String email;

    private String pass;

    private String username;

    private String urladdress;

    private String rol;

    public Profile() {
    }

    public Profile(Integer userhash, String email, String pass, String username, String urlAddress, String rol) {
        this.userhash = userhash;
        this.email = email;
        this.pass = pass;
        this.username = username;
        this.urladdress = urlAddress;
        this.rol = rol;
    }   

    // Getters and Setters
    public Integer getUserhash() {
        return userhash;
    }   

    public String getUrlAddress() {
        return urladdress;
    }

    public String getRol() {
        return rol;
    }

    public void setUrlAddress(String urlAddress) {
        this.urladdress = urlAddress;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setUserhash(Integer userhash) {
        this.userhash = userhash;
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
        return "Profile [userhash=" + userhash + ", email=" + email + ", pass=" + pass + ", username=" + username + " urlAddress:" + urladdress + " rol=" + rol + "]";
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