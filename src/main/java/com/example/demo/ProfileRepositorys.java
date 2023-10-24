package com.example.demo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProfileRepositorys extends CrudRepository<Profile, Integer> {

    @Query("SELECT user FROM Profile user WHERE user.username=:username AND user.pass=:pass")
    public Profile findUserByCredentials(@Param("username") String u,
                                        @Param("pass") String p);
}
