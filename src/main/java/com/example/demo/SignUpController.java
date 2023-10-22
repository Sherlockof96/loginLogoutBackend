package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SignUpController {

    private String url = "jdbc:sqlserver://userdataforloginlogout.database.windows.net:1433;database=userData;user=userData@userdataforloginlogout;password=LoginLogout@1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    ProfileRepositorys profileRepository;

    public SignUpController(ProfileRepositorys profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping("/modify")
    public HttpStatus modify(@RequestBody Profile user) {
        // Handle signup logic here
        try {
                profileRepository.save(user);
        }catch (Exception e)
        {
            return HttpStatus.BAD_REQUEST;
        }
        
        return HttpStatus.CREATED;
    }

    @PostMapping("/signup")
    public HttpStatus signUp(@RequestBody Profile user) {
        // Handle signup logic here
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        try {
                Long count = profileRepository.count();

                Connection con = DriverManager.getConnection(url, "userData@userdataforloginlogout", "LoginLogout@1");
                PreparedStatement ps = con.prepareStatement("INSERT INTO profile (id, username, email, pass) values (?,?,?,?)");
                ps.setInt(1, count.intValue() + 1);
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getUsername());
                ps.setString(4, user.getPass());
                ps.executeUpdate();
                ps.close();
        }catch (SQLException e)
        {
            return HttpStatus.BAD_REQUEST;
        }
        
        return HttpStatus.CREATED;
    }

    @GetMapping("/get")
    public Iterable<Profile> signUps() {
        // Handle signup logic here
        return profileRepository.findAll();
    }
}
