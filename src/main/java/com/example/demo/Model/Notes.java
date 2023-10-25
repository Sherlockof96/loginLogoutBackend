package com.example.demo.Model;

import org.springframework.data.annotation.Id;

public class Notes {

    @Id
    private Integer id;

    private String userhash;

    private String note;

    private String colour;

    public Notes() {
    }

    public Notes(Integer id, String note, String colour, String userhash) {
        this.id = id;
        this.note = note;
        this.colour = colour;
        this.userhash = userhash;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getColour() {
        return colour;
    }

    public String getUserhash() {
        return userhash;
    }

    public void setUserid(Integer id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setUserhash(String userhash) {
        this.userhash = userhash;
    }

    public String toString() {
        return "Notes [id=" + id + " userhash=" + userhash +", note=" + note + ", colour=" + colour + "]";
    }

    
}
