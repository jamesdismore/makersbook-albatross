INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('jimmyd@gmail.com','1990-11-01','James','Dickinson','TRUE','https://imgur.com/g0Lfp8N');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('sashab@gmail.com','1991-06-01','Sansha','Ballom','TRUE','https://imgur.com/LZE6sj2');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('walterwhite@gmail.com','1972-07-10','Walter','White','TRUE','https://imgur.com/Fm5tAKH');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('Mandymayhem@gmail.com','1986-03-01','Mandy','Tucker','TRUE','https://imgur.com/OOkQKY9');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('vicky123@gmail.com','2015-01-01','Victoria','Ballom','TRUE','https://imgur.com/2IprHCN');


INSERT INTO posts (content,user_id,timestamp) VALUES ('Saw a dog today!',1,'2024-01-01 12:00:00');
INSERT INTO posts (content,user_id,timestamp) VALUES ('Going out to cook, noone follow me',3,'2024-01-03 18:00:00');

INSERT INTO comments(content,user_id,post_id, timestamp) VALUES ('Was it cute?',2,1,'2024-01-01 12:30:00');
INSERT INTO comments(content,user_id,post_id, timestamp) VALUES ('It was! <3 <3',1,1,'2024-01-01 12:45:00');
INSERT INTO comments(content,user_id,post_id, timestamp) VALUES ('Where are you going?',5,2,'2024-01-03 18:03:00');
INSERT INTO comments(content,user_id,post_id, timestamp) VALUES ('None of your business',3,2,'2024-01-03 18:05:00');
INSERT INTO comments(content,user_id,post_id, timestamp) VALUES ('Don\'t talk to my daughter like that!',2,2,'2024-01-03 18:07:00');

INSERT INTO friendships(lower_user_id,higher_user_id,timestamp) values (1,2,'2023-09-01 08:00:00');

INSERT INTO post_likes(user_id,post_id) values (2,1,);

INSERT INTO comment_likes(user_id,comment_id) values (1,1);