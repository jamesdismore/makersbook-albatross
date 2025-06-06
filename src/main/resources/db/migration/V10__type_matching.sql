ALTER TABLE posts
    ALTER COLUMN user_id TYPE bigint;

ALTER TABLE comments
    ALTER COLUMN user_id TYPE bigint,
    ALTER COLUMN post_id TYPE bigint;

ALTER TABLE friendships
    ALTER COLUMN lower_user_id TYPE bigint,
    ALTER COLUMN higher_user_id TYPE bigint;

ALTER TABLE comment_likes
    ALTER COLUMN user_id TYPE bigint,
    ALTER COLUMN comment_id TYPE bigint;

ALTER TABLE post_likes
    ALTER COLUMN user_id TYPE bigint,
    ALTER COLUMN post_id TYPE bigint;