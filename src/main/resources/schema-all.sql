DROP TABLE IF EXISTS person;

CREATE TABLE person  (
    person_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);
