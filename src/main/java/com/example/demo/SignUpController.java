package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

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
            
            boolean found = StreamSupport.stream(profileRepository.findAll().spliterator(), false).anyMatch(profile -> {
                            return (profile.getId().toString().hashCode() + profile.getUsername().hashCode() + profile.getPass().hashCode()) == Integer.parseInt(userId);
                            });

            if (found) {
                    result = "true";
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
        String userFound = "NotFound";
        try
        {
            Integer currentUserId = (Integer) session.getAttribute("currentUser");
        
            if (currentUserId == null)
            {
                userFound = "Does not exist";
            }
            else
            {
                userFound = "Exists";
                session.setAttribute("currentUser", currentUserId);
            }
        }
        catch (Exception e)
        {
            userFound = "ExceptionOccured";
        }
        

        // Handle signup logic here
        Integer id = user.getId();
        try {
                profileRepository.findAll().forEach(profile -> {
                    if (profile.getUsername().equals(user.getUsername()) && profile.getPass().equals(user.getPass()))
                    {
                        if (!profile.getRol().equals("admin"))
                        {
                            if(user.getUrlAddress().startsWith("https://kavishdoshi.com"))
                            {
                                user.setId(profile.getId());
                                user.setEmail(profile.getEmail());
                                session.setAttribute("currentUser", profile.getId());
                            }
                        }
                        else
                        {
                            user.setId(profile.getId());
                            user.setEmail(profile.getEmail());
                            session.setAttribute("currentUser", profile.getId());
                        }
                    }
                });

        }catch (Exception e)
        {
            return "invalid";
        }
        
        if (user.getId() == id)
        {
            return "invalid";
        }

        ArrayList<String> userArray = new ArrayList<String>();
        userArray.add(user.getId().toString());
        userArray.add(user.getUsername());
        userArray.add(user.getPass());

        Integer hash = hashup(userArray);

        return hash.toString();
        
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody Profile user, HttpSession session) {
        String result = "signup";
        // Handle signup logic here
        Long count = 0L;
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
                count = profileRepository.count();

                Connection con = DriverManager.getConnection(url, "userData@userdataforloginlogout", "LoginLogout@1");
                PreparedStatement ps = con.prepareStatement("INSERT INTO profile (id, username, email, pass, rol, urlAddress) values (?,?,?,?,?,?)");
                ps.setInt(1, count.intValue() + 1);
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPass());
                ps.setString(5, "user");
                ps.setString(6, "https://kavishdoshi.com");
                ps.executeUpdate();
                ps.close();
                user.setId(count.intValue() + 1);
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
