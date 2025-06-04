drop table likes;

create table comment_likes (
  id bigserial PRIMARY KEY,
    user_id int,
    comment_id int,
      FOREIGN KEY (user_id) REFERENCES users(id),
      FOREIGN KEY (comment_id) REFERENCES comments(id),
      like_timestamp TIMESTAMP
);

create table post_likes (
  id bigserial PRIMARY KEY,
    user_id int,
    post_id int,
      FOREIGN KEY (user_id) REFERENCES users(id),
      FOREIGN KEY (post_id) REFERENCES posts(id),
      like_timestamp TIMESTAMP
);