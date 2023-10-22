package com.auth.kavish.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auth.kavish.model.Profile;

@Repository
public interface ProfileRepository  extends CrudRepository<Profile, Integer> {
}
