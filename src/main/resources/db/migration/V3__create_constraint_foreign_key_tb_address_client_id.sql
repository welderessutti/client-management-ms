ALTER TABLE IF EXISTS tb_address
    ADD CONSTRAINT tb_address_client_id_fk
    FOREIGN KEY (client_id)
    REFERENCES tb_client (id)
;