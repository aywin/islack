insert into profile (id, full_name, address, phone_number) values (1, 'full name 1', 'address 1', 'phone 1');
insert into profile (id, full_name, address, phone_number) values (2, 'full name 2', 'address 2', 'phone 2');
insert into profile (id, full_name, address, phone_number) values (3, 'full name 3', 'address 3', 'phone 3');
insert into profile (id, full_name, address, phone_number) values (4, 'full name 4', 'address 4', 'phone 4');
insert into profile (id, full_name, address, phone_number) values (5, 'full name 5', 'address 5', 'phone 5');


insert into user (id, username, email, password, profile_id) values (1, 'username_1', 'email_1@email.com', 'pass1', 1);
insert into user (id, username, email, password, profile_id) values (2, 'username_2', 'email_2@email.com', 'pass2', 2);
insert into user (id, username, email, password, profile_id) values (3, 'username_3', 'email_3@email.com', 'pass3', 3);
insert into user (id, username, email, password, profile_id) values (4, 'username_4', 'email_4@email.com', 'pass4', 4);
insert into user (id, username, email, password, profile_id) values (5, 'username_5', 'email_5@email.com', 'pass5', 5);
insert into user (id, username, email, password) values (6, 'username_6', 'email_6@email.com', 'pass6');


insert into role (role) VALUES ('user');
insert into role (role) VALUES ('admin');