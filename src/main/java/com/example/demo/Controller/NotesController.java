package com.example.demo.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ProfileRepositorys;
import com.example.demo.Model.Notes;
import com.example.demo.Model.Usernotesmapping;
import com.example.demo.Repository.NotesRepositorys;
import com.example.demo.Repository.UserNotesMapRepositorys;

@RestController
@RequestMapping("/notes")
public class NotesController {
    private String url = "jdbc:sqlserver://userdataforloginlogout.database.windows.net:1433;database=userData;user=userData@userdataforloginlogout;password=LoginLogout@1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    NotesRepositorys notesRepository;
    ProfileRepositorys profileRepository;
    UserNotesMapRepositorys userNotesMapRepository;

    public NotesController(ProfileRepositorys profileRepository, NotesRepositorys notesRepository, UserNotesMapRepositorys userNotesMapRepository) {
        this.profileRepository = profileRepository;
        this.notesRepository = notesRepository;
        this.userNotesMapRepository = userNotesMapRepository;
    }

    @PostMapping("/get")
    public Iterable<Notes> getNotes(@RequestBody String userhash, HttpSession session)
    {
        Iterable<Notes> result = null;

        try{
            Usernotesmapping usermap = userNotesMapRepository.findById(userhash).get();
            ArrayList<Integer> noteIds = usermap.getNoteIds(usermap.getNoteids());
            result = notesRepository.findAllById(noteIds);
        }
        catch(Exception e)
        {
            return result;
        }

        return result;
    }

    @PostMapping("/delete")
    public String deleteNotes(@RequestBody Notes note, HttpSession session)
    {
        try {
                Usernotesmapping usermap = userNotesMapRepository.findById(note.getUserhash()).get();
                String tempIds = usermap.deleteNoteId(usermap.getNoteids(), note.getId());
                usermap.setNoteids(tempIds);
                userNotesMapRepository.save(usermap);
                notesRepository.deleteById(note.getId());
        }catch (Exception e)
        {
            return e.toString();
        }
        return "deleted";
    }

    @PostMapping("/update")
    public String updateNotes(@RequestBody Notes note, HttpSession session)
    {
        try {
                notesRepository.save(note);
        }catch (Exception e)
        {
            return e.toString();
        }
        return "updated";
    }

    @PostMapping("/add")
    public String addNotes(@RequestBody Notes newNotes, HttpSession session) {

        try{
            ArrayList<String> newNoteArray = new ArrayList<String>();
            newNoteArray.add(newNotes.getNote());
            newNoteArray.add(newNotes.getDate());
            newNoteArray.add(newNotes.getUserhash());
            Integer hash = hashup(newNoteArray);
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
            
            if(userNotesMapRepository.findById(newNotes.getUserhash()).isEmpty())
            {
                JSONObject nodeIds = new JSONObject();
                Usernotesmapping userNotesMap = new Usernotesmapping(newNotes.getUserhash(), nodeIds.toString());
                String tempIds = userNotesMap.addNoteId(userNotesMap.getNoteids(), hash);
                userNotesMap.setNoteids(tempIds);
                insertQueryOfUserNoteMapping(getConnection(), userNotesMap);
            }
            else{
                Usernotesmapping userNotesMap = userNotesMapRepository.findById(newNotes.getUserhash()).get();
                String tempIds = userNotesMap.addNoteId(userNotesMap.getNoteids(), hash);
                userNotesMap.setNoteids(tempIds);
                userNotesMapRepository.save(userNotesMap);
            }  
            Connection con = getConnection();
            insertQuery(con, newNotes, hash);
            
        } 
        catch (Exception e) {
            return e.getMessage();
        }catch (Throwable e) {
            return e.getMessage();
        }

        return "addNotes";
    }

    private Connection getConnection() throws SQLException
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new SQLException();
        }

        return DriverManager.getConnection(url, "userData@userdataforloginlogout", "LoginLogout@1");
    }

    private Integer totalRowsQuery(Connection con, Notes notes) throws SQLException {
        String sql = "SELECT COUNT(*) FROM notes";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Integer count = resultSet.getInt(1);
        resultSet.close();
        statement.close();
        return count;
    }

    private void insertQuery(Connection con, Notes notes, Integer count) throws SQLException {
        String sql = "INSERT INTO  notes (id, note, colour, userhash, date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, count + 1);
        statement.setString(2, notes.getNote());
        statement.setString(3, notes.getColour());
        statement.setString(4, notes.getUserhash());
        statement.setString(5, notes.getDate());
        statement.executeUpdate();
        statement.close();
    }

    private void insertQueryOfUserNoteMapping(Connection con, Usernotesmapping map) throws SQLException {
        String sql = "INSERT INTO usernotesmapping (userhash, noteids) VALUES (?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, map.getUserhash());
        statement.setString(2, map.getNoteids());
        statement.executeUpdate();
        statement.close();
    }

    private void createTableQuery(Connection con, Notes notes) throws SQLException {
        String sql = "CREATE TABLE notes (id INTEGER PRIMARY KEY, note VARCHAR(2000), colour VARCHAR(255), userhash VARCHAR(255))";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.executeUpdate();
        statement.close();
    }

    private Integer hashup(ArrayList<String> user) {
        Integer hash = 0;
        for (String string : user) {
            hash += string.hashCode();
        }

        return hash;
    }
}
