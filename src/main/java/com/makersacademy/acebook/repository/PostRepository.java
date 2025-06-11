package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAllByOrderByIdDesc();

    @Query("SELECT post FROM Post post WHERE post.user_id = :userId ORDER BY post.id DESC")
    List<Post> findByUserIdOrderByIdDesc(long userId);
}
