package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

public class DisplayComment {

        private Long id;
        private String content;
        private Long commentAuthorId;
        private Long postId;
        private String commentAuthorFullName;

        public DisplayComment() {
        }

        public DisplayComment(Long id, String content, Long commentAuthorId, Long postId, String commentAuthorFullName) {
            this.id = id;
            this.content = content;
            this.commentAuthorId = commentAuthorId;
            this.postId = postId;
            this.commentAuthorFullName = commentAuthorFullName;
        }

}
