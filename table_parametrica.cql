USE test;

CREATE TABLE IF NOT EXISTS transaction_type (
                                                id int,
                                                name text PRIMARY KEY
);

INSERT INTO transaction_type (id, name) VALUES (1, 'transferencia interbancaria');
INSERT INTO transaction_type (id, name) VALUES (2, 'transferencia interna');
INSERT INTO transaction_type (id, name) VALUES (3, 'transferencia internacional');