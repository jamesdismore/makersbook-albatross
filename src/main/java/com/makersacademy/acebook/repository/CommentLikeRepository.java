package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.CommentLike;
import com.makersacademy.acebook.model.PostLike;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);
    int countByCommentId(Long commentId);
    void deleteByCommentId(Long commentId);
}
