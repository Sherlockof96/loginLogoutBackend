package com.example.demo.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Model.Usernotesmapping;

public interface UserNotesMapRepositorys extends CrudRepository<Usernotesmapping, String> {
}