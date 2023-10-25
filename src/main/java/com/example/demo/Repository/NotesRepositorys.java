package com.example.demo.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Model.Notes;

public interface NotesRepositorys extends CrudRepository<Notes, Integer> {
}
