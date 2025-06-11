package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.PostLike;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostLikeRepository extends CrudRepository<PostLike, Long> {
    Optional<PostLike> findByUserIdAndPostId(Long userId, Long postId);
    int countByPostId(Long postId);
}
