CREATE TABLE person (
    person_id INTEGER NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    age INTEGER NOT NULL,
    favourite_colour VARCHAR(128) NOT NULL,
    
    PRIMARY KEY (person_id)
);

CREATE TABLE hobby (
    id INTEGER NOT NULL AUTO_INCREMENT,
    hobby_name VARCHAR(128),
    person_id INTEGER ,
    foreign key (person_id) references person(person_id),
    
    PRIMARY KEY (id)
);