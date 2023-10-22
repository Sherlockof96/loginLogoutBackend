package com.auth.kavish.model;


import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Profile {

    @Id
    private Integer id;
    private String email;
    private String password;
    private String username;
}
