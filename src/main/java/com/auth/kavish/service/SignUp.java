package com.auth.kavish.service;

import com.auth.kavish.model.Profile;
import org.springframework.stereotype.Service;

@Service
public interface SignUp {

    public boolean add (Profile user);
}
