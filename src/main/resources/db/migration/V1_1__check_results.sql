CREATE SEQUENCE check_result_pk_seq INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS check_results (
    id                  BIGINT NOT NULL PRIMARY KEY,
    check_result        VARCHAR(10) NOT NULL,
    check_time          TIMESTAMP NOT NULL,
    url                 VARCHAR(1000) NOT NULL
);