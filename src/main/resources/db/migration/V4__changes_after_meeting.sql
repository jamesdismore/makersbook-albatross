ALTER table users
DROP column username,
add first_name varchar(50),
add last_name varchar(50),
add dob date;

ALTER table posts
add post_timestamp TIMESTAMP;


alter table comments
add comment_timestamp TIMESTAMP;

alter table friendships
add friendship_timestamp TIMESTAMP;

alter table likes
add like_timestamp TIMESTAMP;