

ALTER TABLE friend_requests
    ALTER COLUMN from_user_id TYPE bigint,
    ALTER COLUMN to_user_id TYPE bigint;


-- Add Animal users

-- | id  | Name         | Username      | AuthO password |
-- | --- | ------------ | ------------- | -------------- |
-- | 7   | Roger Fox    | fox@ex.com    | 12345aA!       |
-- | 8   | Tobias Wolf  | wolf@ex.com   | 12345aA!       |
-- | 9   | Amelia Dog   | dog@ex.com    | 12345aA!       |
-- | 10  | Boris Bear   | bear@ex.com   | 12345aA!       |
-- | 11  | Shona Salmon | salmon@ex.com | 12345aA!       |
-- | 12  | Hannah Hen   | hen@ex.com    | 12345aA!       |
-- | 13  | Rita Raven   | raven@ex.com  | 12345aA!       |


INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('fox@ex.com','1985-01-01','Roger','Fox','TRUE','7');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('wolf@ex.com','1990-01-01','Tobias','Wolf','TRUE','8');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('dog@ex.com','1995-01-01','Amelia','Dog','TRUE','9');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('bear@ex.com','2000-01-01','Boris','Bear','TRUE','10');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('salmon@ex.com','2005-01-01','Shona','Salmon','TRUE','11');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('hen@ex.com','2010-01-01','Hannah','Hen','TRUE','12');
INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('raven@ex.com','1980-01-01','Rita','Raven','TRUE','13');


-- Adjustments to friend_requests table

ALTER TABLE friend_requests
ADD request_message VARCHAR(255);

ALTER TABLE friend_requests
ADD response_message VARCHAR(255);

ALTER TABLE friend_requests
RENAME COLUMN friend_request_timestamp TO request_timestamp;

ALTER TABLE friend_requests
ADD response_timestamp TIMESTAMP;




-- INSERT INTO friend_requests(from_user_id, to_user_id,  request_message, request_timestamp, response_message, response_timestamp, status) VALUES
--  (7, 8, 'Fancy a hunting trip sometime?', '2025-06-03 08:15:00', NULL, NULL, 'PENDING'),
--  (7, 12, 'Can you ever forgive me? 🤣', '2025-05-19 10:30:00', NULL, NULL, 'PENDING'),
--  (7, 9, 'You look familiar - wanna be mates?', '2025-05-29 14:45:00', NULL, NULL , 'PENDING'),
--  (13, 7, E'The scraps are much appreciated - let\'s keep in touch more regularly', '2025-06-1 21:00:00', NULL, NULL, 'PENDING');


INSERT INTO friend_requests(from_user_id, to_user_id,  request_message, request_timestamp, response_message, response_timestamp, status) VALUES
 (7, 8, 'Fancy a hunting trip sometime?', '2025-06-03 08:15:00', NULL, NULL, 'PENDING'),
 (7, 12, 'Can you ever forgive me? 🤣', '2025-05-19 10:30:00', 'Friends!?️ After what you did!?', '2025-05-21 22:15:00', 'REJECTED'),
 (7, 9, 'You look familiar - wanna be mates?', '2025-05-29 14:45:00', 'Of course I''ll be your friend - let''s meet up.', '2025-06-5 9:00:00', 'ACCEPTED'),
 (13, 7, E'The scraps are much appreciated - let\'s keep in touch more regularly', '2025-06-1 21:00:00', NULL, NULL, 'PENDING');






