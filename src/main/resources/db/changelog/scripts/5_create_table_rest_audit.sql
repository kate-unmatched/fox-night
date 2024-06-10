CREATE SEQUENCE if not exists rest_audit_sequence;

CREATE TABLE if not exists rest_audit
(
    id           BIGSERIAL PRIMARY KEY not null,
    request_time timestamp             not null default now(),
    role         VARCHAR(10)           NOT NULL,
    request_type VARCHAR(10)           NOT NULL,
    body         jsonb
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
