# Sistema de Biblioteca

Projeto em Java com Swing — Laboratório de Programação.
Aplicação desktop para gerenciar o acervo de uma biblioteca: cadastro de
**livros** (físicos e e-books), de **membros** e controle de **empréstimos**.
Os dados ficam em memória (`ArrayList`) e são perdidos ao fechar o programa; o
sistema já abre com alguns livros e membros de exemplo.

## Como executar

**Pela IDE:** execute a classe `Main` (`src/Main.java`).

**Pelo terminal**, a partir da raiz do projeto:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
```

No Windows, sem o `find` disponível:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out Main
```

Requer JDK 17 ou superior.

## As telas

A `MainWindow` é o menu inicial, com botões para **Gerenciamento de Livros**,
**Gerenciamento de Membros**, **Gerenciamento de Empréstimos** e **Sair**. Cada
botão abre um `JDialog` modal, criado de novo a cada clique para sempre mostrar
os dados atuais. Todas as telas recebem a mesma instância de `Library`, criada
no `Main`.

As telas de gerenciamento seguem a mesma estrutura: formulário e botões em cima
e uma `JTable` somente leitura no centro. Clicar em uma linha carrega o registro
no formulário para edição.

| Tela            | Operações                                                    |
|-----------------|--------------------------------------------------------------|
| Livros          | cadastrar, editar, remover e limpar campos                   |
| Membros         | cadastrar, editar, ativar/inativar e listar                  |
| Empréstimos     | realizar, encerrar e listar (todos, ativos ou finalizados)   |

## Regras de negócio

As validações ficam em `Library`, que lança `LibraryException` com a mensagem
exibida ao usuário. Os filtros de digitação de `InputFilters` (limite de
caracteres e máscaras) só melhoram a usabilidade.

- **Título e autor:** obrigatórios, até 150 caracteres; o autor aceita apenas
  letras.
- **Ano:** 4 dígitos, entre 1450 e o ano atual.
- **ISBN:** ISBN-13 brasileiro (prefixo 978-65 ou 978-85) com dígito
  verificador válido, sem repetição. Exibido como `978-65-XXXXXXX-X`.
- **Livros emprestados** não podem ser removidos, e o tipo do livro não muda na
  edição.
- **Matrícula:** somente números, até 20 dígitos, sem repetição.
- **Contato:** telefone brasileiro, celular `(DD) 9XXXX-XXXX` ou fixo
  `(DD) XXXX-XXXX`.
- **Membros** não são excluídos, apenas inativados. Membro inativo não pode
  pegar livros emprestados, e membro com empréstimo ativo não pode ser
  inativado.
- **Empréstimos** só listam livros disponíveis e membros ativos. Encerrar um
  empréstimo registra a data de devolução e libera o livro.

O passo a passo de uso de cada tela está em [`TUTORIAL.txt`](TUTORIAL.txt).

## Estrutura do projeto

```
src/
├── Main.java                  ponto de entrada: cria a Library, carrega os exemplos e abre o menu
├── app/
│   └── MainWindow.java        a janela principal (JFrame): menu de navegação
├── entities/
│   ├── Book.java              livro (classe abstrata)
│   ├── Ebook.java             livro digital
│   ├── PhysicalBook.java      livro físico
│   ├── Member.java            membro da biblioteca
│   ├── Loan.java              empréstimo de um livro para um membro
│   ├── LoanStatus.java        enum da situação do empréstimo
│   ├── Library.java           listas em memória e regras de negócio
│   └── LibraryException.java  erro de regra de negócio
├── ui/
│   ├── BooksDialog.java       gerenciamento de livros
│   ├── MembersDialog.java     gerenciamento de membros
│   ├── LoansDialog.java       gerenciamento de empréstimos
│   ├── Dialogs.java           mensagens padronizadas (JOptionPane)
│   └── InputFilters.java      limites e máscaras aplicados ao digitar
└── util/
    ├── IsbnUtils.java         validação e formatação do ISBN
    ├── PhoneUtils.java        validação e formatação do telefone
    └── TextUtils.java         auxiliares para texto digitado
```
