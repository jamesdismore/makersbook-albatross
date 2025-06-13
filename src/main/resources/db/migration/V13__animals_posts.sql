INSERT INTO users (username,dob,first_name,last_name,enabled,avatar) VALUES ('lion@ex.com','1980-01-01','Len','Lion','TRUE','14');



INSERT INTO friend_requests(from_user_id, to_user_id,  request_message, request_timestamp, response_message, response_timestamp, status) VALUES

-- From Wolf
(6, 8, 'You look familiar - will you be my friend?' , '2025-07-2 10:00:00', E'Of course I\'ll be your pal!' , '2025-07-03 15:00:00', 'ACCEPTED'),

-- From Yoshi to Hen
 (6, 12, E'Can I get an egg or two in exchange for a bit of meat?', '2025-06-2 10:00:00', E'Sorry Yoshi - I\'ve no time for new friends.' , '2025-03-2 15:00:00', 'REJECTED'),

 -- INCOMING -> From Lion to Yoshi
 (14, 6, 'Must be great to not have to hunt for your food - can you spare me a scrap or two?', '2025-06-11 10:00:00', NULL, NULL, 'PENDING'),

-- OUTGOING -> From Yoshi to Dog
(6, 9, E'I\'ve heard a lot about you - let\'s meet up', '2025-06-2 10:00:00', NULL, NULL, 'PENDING'),
(6, 13, E'Must be great to be able to fly, can you teach me?', '2025-04-19 10:00:00', NULL, NULL, 'PENDING');


-- Yoshi already friends with...
INSERT INTO friendships(user_id, friend_id, friendship_timestamp) VALUES
-- ...wolf
(6, 8, '2024-05-25 13:00:00'),
(8, 6, '2024-05-25 13:00:00'),

-- ...bear
(6, 10, '2024-05-27 14:00:00'),
(10, 6, '2024-05-27 14:00:00');

-- Two Raven posts
INSERT INTO posts(content, user_id, post_timestamp) VALUES
 (E'Greetings all. I am Raven, master of the morning breeze and shadow of the twilight. Let me share a glimpse into my daily life, a rhythm shaped by wings, wind, and wit.', 13, '2025-06-03 14:00:00'),
 (E'I am the king of the savannah, and nothing stirs my spirit like the chase. From the moment I catch sight of my prey, a surge of power pulses through my muscles — sharp, focused, alive.', 14, '2025-06-04 15:00:00'),
 (E'Hello from the treetops! I’m Raven, architect of the wild, and I’d like to share the story of how I build my nest — a sturdy sanctuary high above the world below.', 13, '2025-06-06 14:00:00'),
 (E'Every day, I roam the forests and mountains, feeling the weight of fear and anger that shepherds carry against me. They see me as a threat, not as a creature just trying to survive like them.', 8, '2025-04-06 15:00:00'),
 (E'I feel the cold breath of extinction closing in, as the lands I once ruled grow silent and empty. Every step I take is shadowed by hunters’ relentless pursuit, leaving fewer of my kind to roam free.', 8, '2024-06-07 15:00:00'),
 (E'If there’s one thing I truly look forward to each year, it’s the long, deep sleep of hibernation. After months of foraging, and feeling the pulse of the wild, there’s nothing like curling up in my den.', 10, '2024-08-01 15:00:00'),
 (E'Being a small furry dog feels like living in a world full of big, exciting smells everywhere. I’m always ready to curl up on a soft lap or cozy blanket for a nap.', 6, '2025-6-13 14:00:00');