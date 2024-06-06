CREATE SEQUENCE if not exists seq_users_id;

CREATE TABLE if not exists users_
(
    id                BIGSERIAL PRIMARY KEY not null,
    modification_date timestamp             not null default now(),
    creation_date     timestamp             not null default now(),
    name              VARCHAR(255)          NOT NULL,
    birthday          DATE,
    start_work        DATE,
    telegram          VARCHAR(255),
    city              VARCHAR(255)          NOT NULL,
    email             VARCHAR(255),
    phone_number      VARCHAR(255)          NOT NULL,
    login             VARCHAR(255)          NOT NULL,
    password          VARCHAR(255)          NOT NULL,
    is_active         BOOLEAN                        default false,
    role_id           INTEGER                NOT NULL
);
