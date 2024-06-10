CREATE TABLE if not exists user_role
(
    id   BIGINT PRIMARY KEY not null,
    role VARCHAR(30)        not null
);

INSERT INTO user_role
VALUES (0, 'ADMIN'),
       (1, 'HR'),
       (2, 'EMPLOYEE')
on conflict (id)
    DO UPDATE SET role = EXCLUDED.role;