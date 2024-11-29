CREATE TABLE tb_client
(
    id                  SERIAL PRIMARY KEY,
    full_name           VARCHAR(100) NOT NULL,
    email               VARCHAR(100) NOT NULL UNIQUE,
    mobile_phone_number VARCHAR(11)  NOT NULL,
    document_number     VARCHAR(11)  NOT NULL UNIQUE
);