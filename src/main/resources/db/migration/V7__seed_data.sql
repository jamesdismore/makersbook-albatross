ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE posts_id_seq RESTART WITH 1;
ALTER SEQUENCE comments_id_seq RESTART WITH 1;
ALTER SEQUENCE friendships_id_seq RESTART WITH 1;
ALTER SEQUENCE post_likes_id_seq RESTART WITH 1;
ALTER SEQUENCE comment_likes_id_seq RESTART WITH 1;

INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('jimmyd@gmail.com','1990-11-01','James','Dickinson','TRUE','https://imgur.com/g0Lfp8N');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('sashab@gmail.com','1991-06-01','Sansha','Ballom','TRUE','https://imgur.com/LZE6sj2');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('walterwhite@gmail.com','1972-07-10','Walter','White','TRUE','https://imgur.com/Fm5tAKH');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('Mandymayhem@gmail.com','1956-03-01','Mandy','Tucker','TRUE','https://imgur.com/jTQ7gAu');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('vicky123@gmail.com','2015-01-01','Victoria','Ballom','TRUE','https://imgur.com/2IprHCN');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('yoshiboy@gmail.com','2024-03-10','Yoshi','Hunter','TRUE','https://imgur.com/a/jWApU9E');


INSERT INTO posts (content,user_id,post_timestamp,photo) VALUES ('Saw a dog today!',1,'2024-01-01 12:00:00','https://imgur.com/a/tSWP7DS');
INSERT INTO posts (content,user_id,post_timestamp) VALUES ('Going out to cook, noone follow me',3,'2024-01-03 18:00:00');
INSERT INTO posts (content,user_id,post_timestamp) VALUES ('How do I use this?',4,'2024-01-04 12:00:00');
INSERT INTO posts (content,user_id,post_timestamp) VALUES ('How to use',4,'2024-01-04 12:04:00');
INSERT INTO posts (content,user_id,post_timestamp) VALUES ('Roast beef casserole recipe',4,'2024-01-04 15:04:00');

INSERT INTO comments(content,user_id,post_id, comment_timestamp) VALUES ('It''s so cute!',2,1,'2024-01-01 12:30:00');
INSERT INTO comments(content,user_id,post_id, comment_timestamp) VALUES ('It is! <3 <3',1,1,'2024-01-01 12:45:00');
INSERT INTO comments(content,user_id,post_id, comment_timestamp) VALUES ('Where are you going?',5,2,'2024-01-03 18:03:00');
INSERT INTO comments(content,user_id,post_id, comment_timestamp) VALUES ('None of your business',3,2,'2024-01-03 18:05:00');
INSERT INTO comments(content,user_id,post_id, comment_timestamp) VALUES ('Don''t talk to my daughter like that!',2,2,'2024-01-03 18:07:00');

INSERT INTO friendships(lower_user_id,higher_user_id,friendship_timestamp) values (1,2,'2023-09-01 08:00:00');
INSERT INTO friendships(lower_user_id,higher_user_id,friendship_timestamp) values (2,5,'2023-06-01 10:00:00');
INSERT INTO friendships(lower_user_id,higher_user_id,friendship_timestamp) values (3,4,'2022-01-01 12:00:00');
INSERT INTO friendships(lower_user_id,higher_user_id,friendship_timestamp) values (6,1,'2024-03-11 12:00:00');
INSERT INTO friendships(lower_user_id,higher_user_id,friendship_timestamp) values (6,2,'2024-03-11 12:00:00');
INSERT INTO friendships(lower_user_id,higher_user_id,friendship_timestamp) values (6,3,'2024-03-11 12:00:00');
INSERT INTO friendships(lower_user_id,higher_user_id,friendship_timestamp) values (6,4,'2024-03-11 12:00:00');
INSERT INTO friendships(lower_user_id,higher_user_id,friendship_timestamp) values (6,5,'2024-03-11 12:00:00');

INSERT INTO post_likes(user_id,post_id,like_timestamp) values (2,1,'2024-01-01 12:27:00');

INSERT INTO comment_likes(user_id,comment_id,like_timestamp) values (1,1,'2024-01-01 12:42:00');