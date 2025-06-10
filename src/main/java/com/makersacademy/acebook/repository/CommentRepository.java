package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CommentRepository extends CrudRepository<Comment, Long>  {
    public ArrayList<Comment> findCommentByPostId(Long postId);
}
