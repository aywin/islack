INSERT INTO privilege (id, name, description) VALUES (1, 'PRIVILEGE_ADMIN_READ', 'description for privilege admin read');
INSERT INTO privilege (id, name, description) VALUES (2, 'PRIVILEGE_USER_READ', 'description for privilege user read');

INSERT INTO role (id, name, description) VALUES (1, 'ROLE_ADMIN', 'description for role admin');
INSERT INTO role (id, name, description) VALUES (2, 'ROLE_USER', 'description for role user');

INSERT INTO role_privileges (role_id, privileges_id) VALUES (1, 1);
INSERT INTO role_privileges (role_id, privileges_id) VALUES (2, 2);

INSERT INTO user (id, enabled, username, email, password) VALUES (1, true, 'admin', 'admin@islack.com', '$2a$10$MTFVrdqbHOi.CCUhkrkZnOBdrZEfk3gzIUyZBdQvLWvdF/0pnkEO2');
INSERT INTO user (id, enabled, username, email, password) VALUES (2, true, 'user', 'user@islack.com', '$2a$10$6KDklkImZgGANWR8pDAwSexf6Bt4Z9I0nDiwdih9Q38HI4eAkWk0u');

INSERT INTO user_roles (user_id, roles_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, roles_id) VALUES (2, 2);