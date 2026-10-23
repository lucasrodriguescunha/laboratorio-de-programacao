# Gerenciamento de Funcionários

Projeto em Java com Swing e JDBC — Laboratório de Programação.
Aplicação desktop que **cadastra, lista, atualiza e exclui** funcionários
(nome, cargo, data de contratação e salário) em um banco **MySQL**. Diferente
dos outros projetos do repositório, os dados não ficam em memória: toda
operação vai ao banco por JDBC, através da `ConnectionFactory`.

## Como executar

1. **Crie o banco:** execute `sql/create-database.sql` no MySQL (Workbench ou
   linha de comando). `sql/sample-data.sql` insere alguns funcionários de
   exemplo (opcional).
2. **Configure a conexão:** ajuste usuário e senha em
   `src/main/resources/database.properties`.
3. **Execute:**
   - **NetBeans:** *File > Open Project*, selecione a pasta `employee-management`
     (é reconhecida como projeto Maven) e clique em *Run* (F6).
   - **IntelliJ IDEA:** abra a pasta como projeto Maven e execute a classe `Main`.
   - **Terminal**, a partir da pasta do projeto:

     ```bash
     mvn compile exec:java
     ```

Requer JDK 17 ou superior, Maven 3 (já embutido no NetBeans e no IntelliJ) e
MySQL 8. O driver JDBC (`mysql-connector-j`) é baixado pelo Maven; o passo a
passo para adicioná-lo manualmente a um projeto Ant está em
[`DOCUMENTATION.txt`](DOCUMENTATION.txt).

## O banco de dados

```sql
CREATE TABLE funcionarios (
    id               INT           NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(100)  NOT NULL,
    cargo            VARCHAR(50)   NOT NULL,
    data_contratacao DATE          NOT NULL,
    salario          DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_funcionarios_salario CHECK (salario > 0)
);
```

O `id` é gerado pelo MySQL (`AUTO_INCREMENT`) e lido de volta com
`getGeneratedKeys()` depois do `INSERT`.

## As camadas

| Camada             | Classe                | Responsabilidade                                               |
|--------------------|-----------------------|----------------------------------------------------------------|
| Conexão            | `ConnectionFactory`   | lê `database.properties`, carrega o driver e abre a `Connection` |
| Acesso a dados     | `EmployeeDao`         | `INSERT`, `SELECT`, `UPDATE` e `DELETE` com `PreparedStatement` |
| Regras de negócio  | `EmployeeService`     | valida os dados antes de chamar o DAO                          |
| Telas              | `MainWindow` e `ui/*` | Swing; mostram as mensagens das exceções ao usuário            |

Cada método do DAO abre uma conexão nova com `try-with-resources`, que fecha
`ResultSet`, `PreparedStatement` e `Connection` mesmo quando ocorre erro. As
`SQLException` viram `DatabaseException` com uma mensagem em português; a
`ConnectionFactory` traduz os erros mais comuns (senha errada, banco
inexistente, servidor parado).

## As telas

- **Menu principal (`MainWindow`):** botões *Cadastrar Funcionário*, *Listar
  Funcionários*, *Testar Conexão* e *Sair*, e o status da conexão no rodapé.
- **Cadastro/edição (`EmployeeFormDialog`):** formulário com máscara de data
  (`dd/mm/aaaa`) e de valor (`1.234,56`). O mesmo diálogo serve para incluir e
  para alterar.
- **Listagem (`EmployeeListDialog`):** busca por nome, `JTable` somente leitura,
  botões *Novo*, *Editar*, *Excluir* e *Atualizar lista*, total de
  funcionários e soma dos salários. Duplo clique em uma linha abre a edição.

O passo a passo de cada tela, os comandos SQL e o roteiro do vídeo estão em
[`DOCUMENTATION.txt`](DOCUMENTATION.txt).

## Estrutura do projeto

```
pom.xml                              dependência do mysql-connector-j e o plugin exec:java
sql/
├── create-database.sql              cria o banco empresa e a tabela funcionarios
└── sample-data.sql                  funcionários de exemplo
src/main/
├── java/
│   ├── Main.java                    ponto de entrada: abre o menu principal
│   ├── app/
│   │   └── MainWindow.java          janela principal (JFrame) e teste de conexão
│   ├── db/
│   │   ├── ConnectionFactory.java   fábrica de conexões JDBC
│   │   └── DatabaseException.java   erro de acesso ao banco
│   ├── dao/
│   │   └── EmployeeDao.java         CRUD da tabela funcionarios
│   ├── entities/
│   │   └── Employee.java            funcionário
│   ├── services/
│   │   ├── EmployeeService.java     validações e regras de negócio
│   │   └── EmployeeException.java   erro de regra de negócio
│   ├── ui/
│   │   ├── EmployeeFormDialog.java  cadastro e edição
│   │   ├── EmployeeListDialog.java  listagem, busca e exclusão
│   │   ├── Dialogs.java             mensagens padronizadas (JOptionPane)
│   │   └── InputFilters.java        limites e máscaras aplicados ao digitar
│   └── util/
│       ├── DateUtils.java           conversão e máscara de datas
│       ├── MoneyUtils.java          conversão e máscara de valores
│       └── TextUtils.java           auxiliares para texto digitado
└── resources/
    └── database.properties          URL, usuário e senha do MySQL
```
