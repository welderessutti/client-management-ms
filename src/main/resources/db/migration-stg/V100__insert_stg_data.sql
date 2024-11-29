INSERT INTO tb_client (full_name, email, mobile_phone_number, document_number)
VALUES ('João da Silva', 'joao@silva.com.br', '77777777777', '55426210152');
INSERT INTO tb_client (full_name, email, mobile_phone_number, document_number)
VALUES ('Maria Conceição', 'maria@conceicao.com.br', '88888888888', '12813762482');
INSERT INTO tb_client (full_name, email, mobile_phone_number, document_number)
VALUES ('Lucio Martins', 'lucio@martins.com.br', '99999999999', '76948546773');
INSERT INTO tb_client (full_name, email, mobile_phone_number, document_number)
VALUES ('Roberto Carlos', 'roberto@carlos.com.br', '55555555555', '58019778055');
INSERT INTO tb_client (full_name, email, mobile_phone_number, document_number)
VALUES ('Fernanda Costa', 'fernanda@costa.com.br', '66666666666', '25131909040');
INSERT INTO tb_client (full_name, email, mobile_phone_number, document_number)
VALUES ('Leticia Martins', 'leticia@martins.com.br', '44444444444', '32059291011');

INSERT INTO tb_address (cep, street, complement, unit, number, neighborhood, locality, uf, state, region, ibge, gia,
                        ddd, siafi, client_id)
VALUES ('76331890', 'Rua Oscar Freire', 'de 10/512 ao fim', '', '80', 'Centro', 'São Paulo', 'SP',
        'São Paulo', 'Sudeste', '4411663', '7845', '11', '8521', 1);
INSERT INTO tb_address (cep, street, complement, unit, number, neighborhood, locality, uf, state, region, ibge, gia,
                        ddd, siafi, client_id)
VALUES ('84120522', 'Rua João Martins', 'de 578/1000 ao fim', '', '55', 'Brooklin', 'São Paulo', 'SP',
        'São Paulo', 'Sudeste', '4477663', '1144', '11', '9978', 2);
INSERT INTO tb_address (cep, street, complement, unit, number, neighborhood, locality, uf, state, region, ibge, gia,
                        ddd, siafi, client_id)
VALUES ('36741110', 'Rua Miguel Costa', 'de 789/1452 ao fim', '', '312', 'Liberdade', 'São Paulo', 'SP',
        'São Paulo', 'Sudeste', '7531595', '2235', '11', '4852', 3);
INSERT INTO tb_address (cep, street, complement, unit, number, neighborhood, locality, uf, state, region, ibge, gia,
                        ddd, siafi, client_id)
VALUES ('04538132', 'Avenida Brigadeiro Faria Lima', 'de 789/1452 ao fim', '', '1744', 'Jardim Paulistano',
        'São Paulo', 'SP',
        'São Paulo', 'Sudeste', '3550308', '3500', '11', '7854', 4);
INSERT INTO tb_address (cep, street, complement, unit, number, neighborhood, locality, uf, state, region, ibge, gia,
                        ddd, siafi, client_id)
VALUES ('30130000', 'Rua da Bahia', 'de 789/1452 ao fim', '', '123', 'Centro', 'Belo Horizonte', 'MG',
        'Minas Gerais', 'Sudeste', '3106200', '3100', '31', '9856', 5);
INSERT INTO tb_address (cep, street, complement, unit, number, neighborhood, locality, uf, state, region, ibge, gia,
                        ddd, siafi, client_id)
VALUES ('80010200', 'Rua XV de Novembro', 'de 789/1452 ao fim', '', '150', 'Centro', 'Curitiba', 'PR',
        'Paraná', 'Sul', '4106902', '4100', '41', '7532', 6);
