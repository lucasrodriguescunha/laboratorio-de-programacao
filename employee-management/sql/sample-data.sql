-- Sistema de Gerenciamento de Funcionários
-- Funcionários de exemplo (opcional). Execute depois de create-database.sql.
-- Cada execução insere os registros de novo.

USE empresa;

INSERT INTO funcionarios (nome, cargo, data_contratacao, salario) VALUES
    ('Ana Souza',      'Analista de Sistemas',  '2021-03-15', 6500.00),
    ('Bruno Lima',     'Desenvolvedor Java',    '2022-08-01', 7200.50),
    ('Carla Mendes',   'Gerente de Projetos',   '2019-01-10', 11800.00),
    ('Diego Ferreira', 'Assistente Financeiro', '2023-05-22', 3150.75),
    ('Elisa Rocha',    'Analista de RH',        '2020-11-03', 4900.00);
