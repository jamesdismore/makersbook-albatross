CREATE table friend_requests (
  id bigserial PRIMARY KEY,
  from_user_id int NOT NULL,
  to_user_id int NOT NULL,
  friend_request_timestamp TIMESTAMP,
  FOREIGN KEY (from_user_id) REFERENCES users(id),
  FOREIGN KEY (to_user_id) REFERENCES users(id)
);

INSERT INTO friend_requests(from_user_id,to_user_id,friend_request_timestamp) values (1,3,'2025-02-01 09:00:00');