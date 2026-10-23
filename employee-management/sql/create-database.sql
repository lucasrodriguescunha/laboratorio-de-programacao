-- Sistema de Gerenciamento de Funcionários
-- Cria o banco de dados "empresa" e a tabela "funcionarios".
-- Pode ser executado mais de uma vez: nada é apagado se já existir.

CREATE DATABASE IF NOT EXISTS empresa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE empresa;

CREATE TABLE IF NOT EXISTS funcionarios (
    id               INT           NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(100)  NOT NULL,
    cargo            VARCHAR(50)   NOT NULL,
    data_contratacao DATE          NOT NULL,
    salario          DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_funcionarios_salario CHECK (salario > 0)
);
