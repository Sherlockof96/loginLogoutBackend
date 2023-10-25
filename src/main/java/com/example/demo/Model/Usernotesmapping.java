package com.example.demo.Model;

import java.util.ArrayList;

import javax.persistence.Entity;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;

@Entity
public class Usernotesmapping {

     @Id
    private String userhash;

    private String noteids;

    public Usernotesmapping() {
    }

    public Usernotesmapping(String userhash, String noteids) {
        this.userhash = userhash;
        this.noteids = noteids;
    }

    // Getters and Setters
    public String getUserhash() {
        return userhash;
    }

    public String getNoteids() {
        return noteids;
    }

    public void setUserhash(String userhash) {
        this.userhash = userhash;
    }

    public void setNoteids(String noteids) {
        this.noteids = noteids;
    }

    public String toString()
    {
        return "Userhash: " + this.userhash + " Noteids: " + this.noteids;
    }

    public String deleteNoteId(String noteids, Integer noteId)
    {
        JSONObject json = new JSONObject(noteids); 
        json.remove(noteId.toString());
        
        return json.toString();
    }

    public String addNoteId(String noteids, Integer noteId)
    {
        JSONObject json = new JSONObject(noteids); 
        json.put(noteId.toString(), "nodeId");
        return json.toString();
    }

    public ArrayList<Integer> getNoteIds(String noteIds)
    {
        JSONObject json = new JSONObject(noteIds);
        ArrayList<Integer> noteIdsList = new ArrayList<Integer>();
        for(String key : json.keySet())
        {
            noteIdsList.add(Integer.parseInt(key));
        }
        return noteIdsList;
    }

    
}
