alter table friend_requests
add status varchar(50);

drop table friendships;

create table friendships(
id bigserial PRIMARY KEY,
user_id int,
friend_id int,
friendship_timestamp TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (friend_id) REFERENCES users(id)
);


INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (1,2,'2023-09-01 08:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (2,1,'2023-09-01 08:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (2,5,'2023-06-01 10:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (5,2,'2023-06-01 10:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (3,4,'2022-01-01 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (4,3,'2022-01-01 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (1,6,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (6,1,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (2,6,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (6,2,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (3,6,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (6,3,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (4,6,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (6,4,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (5,6,'2024-03-11 12:00:00');
INSERT INTO friendships(user_id,friend_id,friendship_timestamp) values (6,5,'2024-03-11 12:00:00');