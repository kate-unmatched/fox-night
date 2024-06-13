ALTER SEQUENCE rest_audit_SEQ INCREMENT BY 50;

CREATE TABLE if not exists rest_audit
(
    id           BIGSERIAL PRIMARY KEY not null,
    name         varchar(255)          not null,
    request_time timestamp             not null default now(),
    role         VARCHAR(10)           NOT NULL,
    request_type VARCHAR(10)           NOT NULL,
    body         text
);

CREATE TABLE if not exists rest_type
(
    id   BIGINT PRIMARY KEY not null,
    rest VARCHAR(10)        not null
);

INSERT INTO rest_type
VALUES (0, 'GET'),
       (1, 'POST'),
       (2, 'PUT'),
       (3, 'PATCH'),
       (4, 'DELETE')
on conflict (id)
    DO UPDATE SET rest = EXCLUDED.rest;
