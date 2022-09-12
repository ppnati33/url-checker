CREATE TABLE IF NOT EXISTS urls (
    url             VARCHAR(1000) NOT NULL PRIMARY KEY,
    created_at      TIMESTAMP NOT NULL
);

INSERT INTO urls (url, created_at) VALUES ('https://google.com', CURRENT_TIMESTAMP());
INSERT INTO urls (url, created_at) VALUES ('https://google-wrong.com', CURRENT_TIMESTAMP());