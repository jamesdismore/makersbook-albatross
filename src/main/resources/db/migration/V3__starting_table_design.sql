ALTER table users
add email varchar(50),
add avatar varchar(100);

ALTER table posts
add photo varchar(100),
add user_id int;

ALTER TABLE posts
ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id);


CREATE table comments (
  id bigserial PRIMARY KEY,
  content varchar(250),
  user_id int,
  post_id int,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE table friendships (
id bigserial PRIMARY KEY,
lower_user_id int,
higher_user_id int,
  FOREIGN KEY (lower_user_id) REFERENCES users(id),
  FOREIGN KEY (higher_user_id) REFERENCES users(id)
);

CREATE table likes (
  id bigserial PRIMARY KEY,
  user_id int,
  post_id int,
  comment_id int,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id)
)