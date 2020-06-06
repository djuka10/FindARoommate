insert into languages (iso_code, name) values ('srp', 'Serbian');
insert into languages (iso_code, name) values ('eng', 'English');
insert into languages (iso_code, name) values ('spa', 'Spanish');
insert into languages (iso_code, name) values ('deu', 'German');
insert into languages (iso_code, name) values ('fra', 'French');
insert into languages (iso_code, name) values ('por', 'Portuguese');
insert into languages (iso_code, name) values ('rus', 'Russian');
insert into languages (iso_code, name) values ('zho', 'Chinise');
insert into languages (iso_code, name) values ('hun', 'Hungarian');
insert into languages (iso_code, name) values ('ell', 'Greek');

-- PERSONALITY
insert into user_characteristics (type, value) values (0, 'Calm');
insert into user_characteristics (type, value) values (0, 'Active');
insert into user_characteristics (type, value) values (0, 'Cheerful');
insert into user_characteristics (type, value) values (0, 'Friendly');
insert into user_characteristics (type, value) values (0, 'Energetic');
insert into user_characteristics (type, value) values (0, 'Organised');
insert into user_characteristics (type, value) values (0, 'Funny');
insert into user_characteristics (type, value) values (0, 'Tolerant');
insert into user_characteristics (type, value) values (0, 'Easygoing');
insert into user_characteristics (type, value) values (0, 'Sociable');

-- LIFESTYLE
insert into user_characteristics (type, value) values (1, 'Traveler');
insert into user_characteristics (type, value) values (1, 'Athlete');
insert into user_characteristics (type, value) values (1, 'Gamer');
insert into user_characteristics (type, value) values (1, 'Vegan');
insert into user_characteristics (type, value) values (1, 'Dancer');
insert into user_characteristics (type, value) values (1, 'Book lover');
insert into user_characteristics (type, value) values (1, 'Tech lover');
insert into user_characteristics (type, value) values (1, 'Walker');
insert into user_characteristics (type, value) values (1, 'Partier');
insert into user_characteristics (type, value) values (1, 'Workaholic');

-- MUSIC
insert into user_characteristics (type, value) values (2, 'Pop');
insert into user_characteristics (type, value) values (2, 'Rock');
insert into user_characteristics (type, value) values (2, 'Alternative');
insert into user_characteristics (type, value) values (2, 'Dance');
insert into user_characteristics (type, value) values (2, 'Hip-hop');
insert into user_characteristics (type, value) values (2, 'Jaaz');
insert into user_characteristics (type, value) values (2, 'Blues');
insert into user_characteristics (type, value) values (2, 'Tolerant');
insert into user_characteristics (type, value) values (2, 'Punk');
insert into user_characteristics (type, value) values (2, 'Metal');

-- FILM
insert into user_characteristics (type, value) values (3, 'Action');
insert into user_characteristics (type, value) values (3, 'Adventure');
insert into user_characteristics (type, value) values (3, 'Crime');
insert into user_characteristics (type, value) values (3, 'Horror');
insert into user_characteristics (type, value) values (3, 'Romance');
insert into user_characteristics (type, value) values (3, 'Thriller');
insert into user_characteristics (type, value) values (3, 'Sci-fi');
insert into user_characteristics (type, value) values (3, 'Animation');
insert into user_characteristics (type, value) values (3, 'Documentary');
insert into user_characteristics (type, value) values (3, 'Drama');

-- SPORT
insert into user_characteristics (type, value) values (4, 'Football');
insert into user_characteristics (type, value) values (4, 'Basketball');
insert into user_characteristics (type, value) values (4, 'Tennis');
insert into user_characteristics (type, value) values (4, 'MMA');
insert into user_characteristics (type, value) values (4, 'Gym');
insert into user_characteristics (type, value) values (4, 'Golf');
insert into user_characteristics (type, value) values (4, 'Swimming');
insert into user_characteristics (type, value) values (4, 'Skateboarding');
insert into user_characteristics (type, value) values (4, 'Baseball');
insert into user_characteristics (type, value) values (4, 'Volleyball');

insert into ad_items (name) values ('Wifi');
insert into ad_items (name) values ('Lift');
insert into ad_items (name) values ('Washing machine');
insert into ad_items (name) values ('Dishing');
insert into ad_items (name) values ('Room service');
insert into ad_items (name) values ('Doorman');
insert into ad_items (name) values ('Tv');
insert into ad_items (name) values ('Heating');
insert into ad_items (name) values ('Air cond');
insert into ad_items (name) values ('Furnished');
insert into ad_items (name) values ('Parking');
insert into ad_items (name) values ('Garage');
insert into ad_items (name) values ('Pool');
insert into ad_items (name) values ('Terrace');
insert into ad_items (name) values ('Pet friendly');
insert into ad_items (name) values ('Garden');
insert into ad_items (name) values ('Balcony');
insert into ad_items (name) values ('Dryer');
insert into ad_items (name) values ('Private lift');
insert into ad_items (name) values ('Natural ligth');

-- users
insert into users (about, active_since, birth_day, email, first_name, gender, last_name, occupation, password, study_level, url_profile, working_status) values 
('Lorem ipsum', '2020-05-05', '1996-02-09', 'pera123@gmail.com', 'Petar', 'Male', 'Petrovic', 'SW architect', 'password', 'Bachelor''s degree', '', 'Study');

insert into users (about, active_since, birth_day, email, first_name, gender, last_name, occupation, password, study_level, url_profile, working_status) values 
('Lorem ipsum', '2020-05-05', '1996-03-29', 'lukajvnv@gmail.com', 'Luka', 'Male', 'Jovanovic', 'SW architect', 'password', 'Bachelor''s degree', '', 'Study');

insert into users (about, active_since, birth_day, email, first_name, gender, last_name, occupation, password, study_level, url_profile, working_status) values 
('Lorem ipsum', '2020-05-05', '1996-03-01', 'srdjanpopovic14@gmail.com', 'Srdjan', 'Male', 'Popovic', 'SW architect', 'password', 'Bachelor''s degree', '', 'Study');

insert into users (about, active_since, birth_day, email, first_name, gender, last_name, occupation, password, study_level, url_profile, working_status) values 
('Lorem ipsum', '2020-05-05', '1996-02-09', 'viktordjuka10@gmail.com', 'Viktor', 'Male', 'Djuka', 'SW architect', 'password', 'Bachelor''s degree', '', 'Study');