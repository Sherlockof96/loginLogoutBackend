package com.auth.kavish.service.impl;

import com.auth.kavish.model.Profile;
import com.auth.kavish.repository.ProfileRepository;
import com.auth.kavish.service.SignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpImpl implements SignUp {

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public boolean add (Profile user){
        profileRepository.save(user);
        return true;
    }
}
