# CadastroAlunos (Sistema Escola)

Projeto em Java com Swing e JDBC — Laboratório de Programação
(ED03 - Atividade Prática Avaliativa 02).
Aplicação desktop que **cadastra e lista alunos, professores e cursos** no
banco MySQL `escola`. É um projeto **NetBeans Java with Ant** (sem Maven): o
driver JDBC fica em `lib/` e é referenciado em `nbproject/project.properties`.

## Como executar

1. **Crie o banco:** execute `script-sql.txt` no MySQL (Workbench ou
   `mysql -u root -p < script-sql.txt`).
2. **Confira a conexão:** usuário e senha ficam em
   `src/cadastroalunos/conexao/Conexao.java` (`root` / `admin`).
3. **Execute:**
   - **NetBeans:** *File > Open Project*, selecione a pasta `CadastroAlunos` e
     clique em *Run Project* (F6). Ao abrir o projeto, o NetBeans gera
     `nbproject/build-impl.xml`, que o `build.xml` importa.
   - **IntelliJ IDEA:** o módulo `CadastroAlunos.iml` já inclui o driver;
     execute a classe `cadastroalunos.CadastroAlunos`.
   - **Terminal**, a partir da pasta do projeto (PowerShell):

     ```powershell
     javac -encoding UTF-8 -cp lib/mysql-connector-j-9.1.0.jar -d build/classes (Get-ChildItem -Recurse src -Filter *.java).FullName
     java -cp "build/classes;lib/mysql-connector-j-9.1.0.jar" cadastroalunos.CadastroAlunos
     ```

Requer JDK 11 ou superior e MySQL 8. O passo a passo de uso de cada tela está
em [`TUTORIAL.txt`](TUTORIAL.txt).

## O banco de dados

```sql
CREATE TABLE aluno (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL
);

CREATE TABLE professor (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  disciplina VARCHAR(100) NOT NULL
);

CREATE TABLE curso (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  carga_horaria INT NOT NULL,
  CHECK (carga_horaria > 0)
);
```

O script completo, com `CREATE DATABASE` e `IF NOT EXISTS` (pode ser executado
mais de uma vez), está em [`script-sql.txt`](script-sql.txt).

## A conexão

| Classe              | Responsabilidade                                                             |
|---------------------|------------------------------------------------------------------------------|
| `Conexao`           | URL, usuário e senha; carrega o driver (`Class.forName`) e abre a `Connection` |
| `ConnectionFactory` | nome usado no código dos botões da atividade; repassa para `Conexao`          |

A atividade pede a classe `Conexao.java`, mas o código fornecido para os botões
chama `ConnectionFactory.getConnection()`. As duas existem: todas as telas usam
`ConnectionFactory`, que delega para `Conexao`. Os erros de conexão mais comuns
(driver ausente, senha errada, banco inexistente, servidor parado) viram
mensagens em português.

## As telas

Todos os botões seguem o padrão do código fornecido para aluno: conexão pela
`ConnectionFactory`, SQL com `?` em um `PreparedStatement`, mensagem de
confirmação ou de erro em `JOptionPane` e campos limpos depois da inserção.
Em relação ao código original, a conexão e o statement ficam em um
*try-with-resources* (são fechados mesmo em caso de erro) e os campos são
validados antes do `INSERT`.

| Tela                    | Classe                  | Botões                | Comando SQL                       |
|-------------------------|-------------------------|-----------------------|-----------------------------------|
| Menu principal          | `TelaPrincipal`         | abre as outras telas, Testar Conexão, Sair | —            |
| Alunos                  | `TelaAluno`             | Salvar, Listar        | `INSERT` / `SELECT` em `aluno`    |
| Cadastro de Professor   | `TelaCadastroProfessor` | Inserir               | `INSERT INTO professor`           |
| Listagem de Professores | `TelaListagemProfessor` | Listar                | `SELECT ... FROM professor`       |
| Cadastro de Curso       | `TelaCadastroCurso`     | Inserir               | `INSERT INTO curso`               |
| Listagem de Cursos      | `TelaListagemCurso`     | Listar                | `SELECT ... FROM curso`           |

As listagens aparecem em um `JTextArea`, uma linha por registro
(`1 - Nome: ... - Disciplina: ...`), com o total no rodapé. As telas são
`JDialog` não modais: cadastro e listagem podem ficar abertos lado a lado.

## Estrutura do projeto

```
build.xml                          script Ant (importa nbproject/build-impl.xml)
manifest.mf                        manifesto do .jar
nbproject/
├── project.xml                    tipo do projeto (Java with Ant) e nome
└── project.properties             classe principal, versão do Java e o driver em Libraries
lib/
└── mysql-connector-j-9.1.0.jar    driver JDBC do MySQL (Connector/J)
script-sql.txt                     criação do banco escola e das tabelas
TUTORIAL.txt                       tutorial de navegação e roteiro do vídeo
src/cadastroalunos/
├── CadastroAlunos.java            ponto de entrada: abre a tela principal
├── conexao/
│   ├── Conexao.java               URL, usuário, senha, driver e getConnection()
│   └── ConnectionFactory.java     fábrica usada pelas telas
└── telas/
    ├── TelaPrincipal.java         menu (JFrame) e status da conexão
    ├── TelaAluno.java             cadastro e listagem de alunos
    ├── TelaCadastroProfessor.java
    ├── TelaListagemProfessor.java
    ├── TelaCadastroCurso.java
    ├── TelaListagemCurso.java
    └── Formulario.java            painéis e avisos usados por várias telas
```
