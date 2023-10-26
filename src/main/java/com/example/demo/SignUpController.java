package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

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

    @PostMapping("/checkLoggedIn")
    public String checkLoggedIn(@RequestBody String userId, HttpSession session)
    {
        String result = "false";

        try {
            boolean found = profileRepository.findById(Integer.parseInt(userId)).isPresent();
            if (found) {
                    result = profileRepository.findById(Integer.parseInt(userId)).get().getRol();
            }
        }
        catch (Exception e)
        {
            result = "false";
        }

        return result;
    }

    @PostMapping("/api/logout")
    public void logout(HttpSession session) {
        session.invalidate();
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
    public String login(@RequestBody Profile user, HttpSession session) {

        // Handle signup logic here
        Integer id = user.getUserhash();
        try {
                profileRepository.findAll().forEach(profile -> {
                    if (profile.getUsername().equals(user.getUsername()) && profile.getPass().equals(user.getPass()))
                    {
                        if (!profile.getRol().equals("admin"))
                        {
                            if(user.getUrlAddress().startsWith("https://kavishdoshi.com"))
                            {
                                user.setUserhash(profile.getUserhash());
                                user.setEmail(profile.getEmail());
                                session.setAttribute("currentUser", profile.getUserhash());
                            }
                        }
                        else
                        {
                            user.setUserhash(profile.getUserhash());
                            user.setEmail(profile.getEmail());
                            session.setAttribute("currentUser", profile.getUserhash());
                        }
                    }
                });

        }catch (Exception e)
        {
            return "invalid";
        }
        
        if (user.getUserhash() == id)
        {
            return "invalid";
        }

        return user.getUserhash().toString();
        
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody Profile user, HttpSession session) {
        String result = "signup";
        // Handle signup logic here
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "invalid";
        }
        
        try {
                ArrayList<String> userArray = new ArrayList<String>();
                userArray.add(user.getUsername());
                userArray.add(user.getEmail());
                userArray.add(user.getPass());
                Integer hash = hashup(userArray);
                Boolean temp = false;
                do{
                    if(profileRepository.findById(hash).isPresent())
                    {
                        hash++;
                    }
                    else
                    {
                        temp = true;
                    }

                }while (temp != true);
                

                Connection con = DriverManager.getConnection(url, "userData@userdataforloginlogout", "LoginLogout@1");
                PreparedStatement ps = con.prepareStatement("INSERT INTO profile (userhash, username, email, pass, rol, urlAddress) values (?,?,?,?,?,?)");
                ps.setInt(1, hash);
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPass());
                ps.setString(5, user.getRol()!=null ? user.getRol():"user");
                ps.setString(6, user.getUrlAddress()!=null? user.getUrlAddress():"https://kavishdoshi.com");
                ps.executeUpdate();
                ps.close();
                con.close();
        }catch (SQLException e)
        {
            return "invalid";
        }
        
        return result;
    }

    @GetMapping("/get")
    public Iterable<Profile> signUps() {
        // Handle signup logic here
        return profileRepository.findAll();
    }

    
    private Integer hashup(ArrayList<String> user) {
        // Handle signup logic here
        Integer hash = 0;
        for (String string : user) {
            hash += string.hashCode();
        }

        return hash;
    }
}
