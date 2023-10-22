package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/")
public class SignUpController {

    private String url = "jdbc:sqlserver://userdataforloginlogout.database.windows.net:1433;database=userData;user=userData@userdataforloginlogout;password=LoginLogout@1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    ProfileRepositorys profileRepository;

    public SignUpController(ProfileRepositorys profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PostMapping("/modify")
    public HttpStatus modify(@RequestBody Profile changedProfile) {
        // Handle signup logic here
        try {
                profileRepository.save(changedProfile);
        }catch (Exception e)
        {
            return HttpStatus.BAD_REQUEST;
        }
        
        return HttpStatus.CREATED;
    }

    @PostMapping("/login")
    public Integer login(@RequestBody Profile user) {
        // Handle signup logic here
        Integer id = user.getId();
        try {
                profileRepository.findAll().forEach(profile -> {
                    if(profile.getEmail().equals(user.getEmail()) && profile.getPass().equals(user.getPass()))
                    {
                        user.setId(profile.getId());
                    } 
                    else if (profile.getUsername().equals(user.getUsername()) && profile.getPass().equals(user.getPass()))
                    {
                        user.setId(profile.getId());
                    }
                });

        }catch (Exception e)
        {
            return 0;
        }
        
        if (user.getId() == id)
        {
            return -1;
        }
        return user.getId();
    }

    @PostMapping("/signup")
    public int signUp(@RequestBody Profile user) {
        // Handle signup logic here
        Long count = 0L;
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return count.intValue();
        }
        
        try {
                count = profileRepository.count();

                Connection con = DriverManager.getConnection(url, "userData@userdataforloginlogout", "LoginLogout@1");
                PreparedStatement ps = con.prepareStatement("INSERT INTO profile (id, username, email, pass) values (?,?,?,?)");
                ps.setInt(1, count.intValue() + 1);
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPass());
                ps.executeUpdate();
                ps.close();
        }catch (SQLException e)
        {
            return 0;
        }
        
        return count.intValue() + 1;
    }

    @GetMapping("/get")
    public Iterable<Profile> signUps() {
        // Handle signup logic here
        return profileRepository.findAll();
    }
}
