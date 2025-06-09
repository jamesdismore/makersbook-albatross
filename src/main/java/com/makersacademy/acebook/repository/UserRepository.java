package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findUserByUsername(String username);

    @Query(value="SELECT * FROM users u WHERE u.username LIKE %?1% OR u.first_name LIKE %?1% OR u.last_name LIKE %?1%",nativeQuery = true)
    ArrayList<User> testFunction(String input);
}
