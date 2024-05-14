CREATE TABLE login_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) DEFAULT NULL,
    details varchar(100) DEFAULT NULL
);

CREATE TABLE login_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    full_name varchar(255) NOT NULL
);

CREATE TABLE login_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_login_user_id FOREIGN KEY (user_id) REFERENCES login_user (id),
    CONSTRAINT fk_login_role_id FOREIGN KEY (role_id) REFERENCES login_role (id)
);

INSERT INTO login_role (id, name, details) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO login_role (id, name, details) VALUES (2, 'ROLE_REP', 'Representative');

INSERT INTO login_user (id, username, password, full_name) VALUES
(1, 'admin', '$2a$12$iixCarmsryuIGCHY1Q2Tme6Ff4L5eBfgoUICY8FJ9guA43JAczNS.', 'Administrator'),
(2, 'rep_mary', '$2a$12$.YWMPAjxgU9zJmTwpgjZn.6M5TTsw2AtVMJwOxd.a5ovGEBn7jDcS', 'Mary'),
(3, 'rep_carl', '$2a$12$.YWMPAjxgU9zJmTwpgjZn.6M5TTsw2AtVMJwOxd.a5ovGEBn7jDcS', 'Carl');

-- ROLES
INSERT INTO login_user_role(user_id, role_id) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 2);