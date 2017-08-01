DROP TABLE IF EXISTS PEOPLE;
DROP TABLE IF EXISTS MESSAGES;
DROP TABLE IF EXISTS FOLLOWERS;
DROP TABLE IF EXISTS LOGIN;

-- Feel free to augment or modify these schemas (and the corresponding data) as you see fit!
CREATE TABLE PEOPLE (
    id IDENTITY,
    handle VARCHAR,
    name VARCHAR
);

CREATE TABLE MESSAGES (
    id IDENTITY,
    person_id NUMBER REFERENCES people (id),
    content VARCHAR
);

CREATE TABLE FOLLOWERS (
    id IDENTITY,
    person_id NUMBER REFERENCES people (id),
    follower_person_id NUMBER REFERENCES people (id)
);

CREATE TABLE LOGIN (
	id IDENTITY,
	user_name VARCHAR,
	password VARCHAR
);
