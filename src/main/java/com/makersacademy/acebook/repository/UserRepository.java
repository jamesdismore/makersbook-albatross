package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findUserByUsername(String username);

    @Query(value="SELECT * FROM users u WHERE LOWER(u.username) LIKE %?1% OR LOWER(u.first_name) LIKE %?1% OR LOWER(u.last_name) LIKE %?1%",nativeQuery = true)
    ArrayList<User> approximateUserSearch(String input);

    @Query(value="SELECT * FROM users u WHERE LOWER(u.username) = ?1 OR LOWER(u.first_name) = ?1 OR LOWER(u.last_name) = ?1",nativeQuery = true)
    ArrayList<User> exactUserSearch(String input);
}
