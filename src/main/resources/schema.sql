CREATE TABLE person (
    person_id INTEGER NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    age INTEGER NOT NULL,
    favourite_colour VARCHAR(128) NOT NULL,
    
    PRIMARY KEY (person_id)
);

CREATE TABLE hobby (
    hobby_id INTEGER NOT NULL AUTO_INCREMENT,
    hobby_name VARCHAR(128),
    
    PRIMARY KEY (hobby_id)
);

CREATE TABLE PERSON_HOBBY (
	person_id INTEGER ,
	foreign key (person_id) references person(person_id),
	
	hobby_id INTEGER ,
	foreign key (hobby_id) references hobby(hobby_id)
);