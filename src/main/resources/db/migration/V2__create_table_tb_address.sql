CREATE TABLE tb_address
(
    id           SERIAL PRIMARY KEY,
    client_id    INTEGER      NOT NULL UNIQUE,
    cep          VARCHAR(8)   NOT NULL,
    street       VARCHAR(100) NOT NULL,
    complement   VARCHAR(100) NOT NULL,
    unit         VARCHAR(100) NOT NULL,
    number       VARCHAR(20)  NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    locality     VARCHAR(100) NOT NULL,
    uf           VARCHAR(2)   NOT NULL,
    state        VARCHAR(100) NOT NULL,
    region       VARCHAR(100) NOT NULL,
    ibge         VARCHAR(100) NOT NULL,
    gia          VARCHAR(100) NOT NULL,
    ddd          VARCHAR(2)   NOT NULL,
    siafi        VARCHAR(100) NOT NULL
);