INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_USER');

INSERT INTO fam (name, description, created) VALUES ('admin', 'fam for admin', NOW());

INSERT INTO member (member_id, password, birth, first_name, last_name, phone)
VALUES ('admin', '$2a$10$OxKKq/oYJmQKfhIS.DX0y.aOV5LZHqaHBJ0K2ET6D5UMv/E29QJJ2', '900101', 'admin', 'admin', '01000000000');
INSERT INTO members_roles VALUES (1, 1);

INSERT INTO fam_members (fam_idx, member_idx, is_master, description, created)
VALUES (1, 1, 1, 'admin', NOW());


-- for test
INSERT INTO fam (name, description, created) VALUES ('test fam', 'test!', NOW());

INSERT INTO member (member_id, password, birth, first_name, last_name, phone)
VALUES ('test1', '$2a$10$OxKKq/oYJmQKfhIS.DX0y.aOV5LZHqaHBJ0K2ET6D5UMv/E29QJJ2', '991101', 'te1', 'st1', '01000000000');
INSERT INTO members_roles VALUES (2, 2);
INSERT INTO member (member_id, password, birth, first_name, last_name, phone)
VALUES ('test2', '$2a$10$OxKKq/oYJmQKfhIS.DX0y.aOV5LZHqaHBJ0K2ET6D5UMv/E29QJJ2', '920224', 'te2', 'st2', '01000000000');
INSERT INTO members_roles VALUES (3, 2);
INSERT INTO member (member_id, password, birth, first_name, last_name, phone)
VALUES ('test3', '$2a$10$OxKKq/oYJmQKfhIS.DX0y.aOV5LZHqaHBJ0K2ET6D5UMv/E29QJJ2', '901230', 'te3', 'st3', '01000000000');
INSERT INTO members_roles VALUES (4, 2);

INSERT INTO fam_members (fam_idx, member_idx, is_master, description, created) VALUES (2, 2, 1, 'admin', NOW());
INSERT INTO fam_members (fam_idx, member_idx, is_master, description, created) VALUES (2, 3, 0, 'member', NOW());
INSERT INTO fam_members (fam_idx, member_idx, is_master, description, created) VALUES (2, 4, 0, 'member', NOW());
