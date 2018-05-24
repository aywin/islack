INSERT INTO privilege (name, description) VALUES ('PRIVILEGE_ADMIN_READ', 'description for privilege admin read');
INSERT INTO privilege (name, description) VALUES ('PRIVILEGE_USER_READ', 'description for privilege account read');

INSERT INTO role (name, description) VALUES ('ROLE_ADMIN', 'description for role admin');
INSERT INTO role (name, description) VALUES ('ROLE_USER', 'description for role account');

INSERT INTO role_privileges (role_id, privileges_id) VALUES (1, 1);
INSERT INTO role_privileges (role_id, privileges_id) VALUES (2, 2);

INSERT INTO account (enabled, username, email, password) VALUES (1, 'admin', 'admin@islack.com', 'admin');
INSERT INTO account (enabled, username, email, password) VALUES (1, 'account', 'account@islack.com', 'account');

INSERT INTO account_roles (account_id, roles_id) VALUES (1, 1);
INSERT INTO account_roles (account_id, roles_id) VALUES (2, 2);