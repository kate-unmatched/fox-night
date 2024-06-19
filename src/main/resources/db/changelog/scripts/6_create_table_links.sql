CREATE SEQUENCE links_seq INCREMENT BY 50;

CREATE TABLE if not exists links
(
    id           BIGSERIAL PRIMARY KEY not null,
    request_time timestamp             not null default now(),
    short_name   VARCHAR(255),
    url          text                  NOT NULL
);
