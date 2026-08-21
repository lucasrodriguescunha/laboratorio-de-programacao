# Sistema de Gerenciamento de Biblioteca

Atividade Prática Avaliativa (01) — Laboratório de Programação.
Sistema de console em Java que gerencia **livros**, **membros**, **funcionários**
e **empréstimos**, aplicando abstração, herança, encapsulamento, polimorfismo, interfaces e
exceções personalizadas.

O diagrama de classes e a explicação de cada decisão de projeto estão em
[`docs/diagrama-de-classes.md`](docs/diagrama-de-classes.md).

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

## Dados de exemplo

A classe `SampleData`, chamada pelo `Main`, cadastra antes de o menu abrir:

| Tipo | Identificador | Descrição |
|---|---|---|
| Ebook | `978-65-00-00077-1` | Engenharia de Software Moderna — 9.9 MB |
| Livro físico | `8543024978` | Engenharia de Software Moderna — 1680 g |
| Membro | `1` | Lucas Rodrigues Cunha |

Os funcionários começam vazios: são cadastrados pelo menu, em
`Funcionários (4)` → `Cadastrar funcionário (1)`.

## Funcionalidades implementadas

Todas são acessadas pelo menu de console. O `LibraryMenu` exibe os grupos do
enunciado, mais o cadastro de funcionários, e encaminha para o submenu de cada
um: `BookMenu`, `MemberMenu`, `EmployeeMenu` e `LoanMenu`.

### Gerenciamento de Livros — `BookService`

| Funcionalidade | Método | Regra aplicada |
|---|---|---|
| Incluir livro | `add(Book)` | O código do livro precisa ser único no acervo |
| Editar livro | `update(código, título, autor)` | Só edita livro existente |
| Remover livro | `remove(código)` | Livro emprestado não pode ser removido |
| Listar livros | `list()` | Devolve a lista somente leitura |

Na inclusão, o menu pergunta o tipo do livro: **físico** (informa o peso em
gramas) ou **ebook** (informa o tamanho do arquivo em MB).

### Gerenciamento de Membros — `MemberService`

| Funcionalidade | Método | Regra aplicada |
|---|---|---|
| Cadastrar membro | `register(Member)` | A matrícula precisa ser única |
| Editar membro | `update(matrícula, nome, email)` | Só edita membro existente |
| Listar membros | `list()` | Mostra também os empréstimos ativos de cada um |

### Gerenciamento de Funcionários — `EmployeeService`

| Funcionalidade | Método | Regra aplicada |
|---|---|---|
| Cadastrar funcionário | `register(Employee)` | A matrícula precisa ser única |
| Editar funcionário | `update(matrícula, nome, email, cargo, salário)` | Só edita funcionário existente |
| Listar funcionários | `list()` | Mostra cargo e salário de cada um |

O funcionário não toma livros emprestados — por isso `LoanService` não conhece
este serviço. Ele é o outro ramo da herança de `Person`, com atributos e
descrição diferentes do membro.

### Gerenciamento de Empréstimos — `LoanService`

| Funcionalidade | Método | Regra aplicada |
|---|---|---|
| Realizar empréstimo | `create(matrícula, código, dias)` | Membro e livro precisam existir; membro abaixo do limite de 3 empréstimos ativos; livro disponível |
| Encerrar empréstimo | `close(número)` | Devolve o livro ao acervo e baixa o contador do membro |
| Listar empréstimos | `list()` | Mostra situação e indica atraso |
| Listar por membro | `listByMember(matrícula)` | Matrícula inexistente vira erro, não lista vazia |

## Tratamento de erros

Todas as exceções do domínio herdam de `LibraryException`, e o menu é o **único**
lugar que as captura: um `catch (LibraryException e)` em `Menu.start()`, que
atende a qualquer opção de qualquer submenu, exibe a mensagem e devolve o
usuário ao menu.

| Situação | Exceção | Mensagem exibida |
|---|---|---|
| Código de livro inexistente | `BookNotFoundException` | `Nenhum livro encontrado com o código X.` |
| Matrícula de membro inexistente | `MemberNotFoundException` | `Nenhum membro encontrado com a matrícula X.` |
| Matrícula de funcionário inexistente | `EmployeeNotFoundException` | `Nenhum funcionário encontrado com a matrícula X.` |
| Livro já emprestado | `BookUnavailableException` | `O livro "X" (código Y) já está emprestado.` |
| Membro no limite de empréstimos | `LoanLimitExceededException` | `O membro X já atingiu o limite de empréstimos ativos.` |
| Código ou matrícula repetidos | `LibraryException` | `Já existe um livro cadastrado com o código X.` |
| Empréstimo inexistente ou já encerrado | `LibraryException` | `O empréstimo N já está encerrado.` |

## Roteiro de demonstração

Sequência sugerida para mostrar o sistema funcionando, incluindo os casos de
erro. Entre parênteses, a opção a digitar.

**1. Listar o acervo inicial** — `Livros (1)` → `Listar livros (4)` → `Voltar (0)`.
Mostra o ebook e o livro físico, ambos disponíveis. A mesma listagem imprime os
dois tipos sem verificar tipo nenhum: é o polimorfismo de `description()`.

**2. Incluir um livro** — `Livros (1)` → `Incluir livro (1)`.
Escolha `Ebook (2)`, código `E1`, título `Clean Code`, autor `Robert C. Martin`,
464 páginas, tamanho `9,9`. Liste de novo para ver os três livros.

**3. Tentar incluir com código repetido** — repita a inclusão usando o código
`E1`. Resposta: `Erro: Já existe um livro cadastrado com o código E1.`

**4. Editar e cadastrar membro** — `Membros (2)` → `Cadastrar membro (1)`,
matrícula `2024001`, nome e email quaisquer → `Listar membros (3)`. O membro
aparece com `Empréstimos ativos: 0/3`.

**5. Realizar um empréstimo** — `Empréstimos (3)` → `Realizar empréstimo (1)`,
matrícula `1`, código `8543024978`, prazo `14`:

```
Empréstimo realizado com sucesso.

Empréstimo: 1
Livro: Engenharia de Software Moderna (8543024978)
Membro: Lucas Rodrigues Cunha (1)
Data do empréstimo: 2026-08-21
Devolução prevista: 2026-09-04
Devolvido em: Não devolvido
Situação: Ativo
```

**6. Tentar emprestar o mesmo livro físico** — repita a opção com os mesmos
dados: `Erro: O livro "Engenharia de Software Moderna" (código 8543024978) já
está emprestado.`

**7. Emprestar o mesmo ebook repetidas vezes** — matrícula `1`, código
`978-65-00-00077-1`. As duas primeiras funcionam, porque cópia digital não se
esgota (`Ebook.isAvailable()` sempre retorna `true`) — repare que o mesmo livro
é emprestado de novo sem erro, ao contrário do passo 6. Na terceira tentativa:
`Erro: O membro Lucas Rodrigues Cunha já atingiu o limite de empréstimos ativos.`
São dois ebooks mais o livro físico do passo 5, fechando o limite de 3 ativos —
`Listar membros` mostra `Empréstimos ativos: 3/3`.

**8. Encerrar um empréstimo** — `Encerrar empréstimo (2)`, número `1`. Liste os
empréstimos e veja a situação como `Encerrado`, com a data de devolução
preenchida. Tentar encerrar de novo: `Erro: O empréstimo 1 já está encerrado.`

**9. Remover o livro devolvido** — `Livros (1)` → `Remover livro (3)`, código
`8543024978`. Agora é permitido, porque ele voltou ao acervo. Se tivesse sido
removido no passo 6, o erro seria `BookUnavailableException`.

**10. Cadastrar um funcionário** — `Funcionários (4)` → `Cadastrar funcionário (1)`,
matrícula `1`, nome e email quaisquer, cargo `Desenvolvedor backend`, salário
`4500` → `Listar funcionários (3)`. A mesma listagem do passo 1 imprime uma
pessoa em vez de um livro, sem nenhum teste de tipo. Repetir a matrícula `1` dá
`Erro: Já existe um funcionário cadastrado com a matrícula 1.`

**11. Erros de busca** — tente qualquer operação com o código `XYZ` ou a
matrícula `9999` para ver `BookNotFoundException`, `MemberNotFoundException` e
`EmployeeNotFoundException`.

## Conceitos de POO aplicados

Resumo — o detalhamento, com os diagramas, está na seção 6 de
[`docs/diagrama-de-classes.md`](docs/diagrama-de-classes.md).

- **Abstração** — `Book` e `Person` são abstratas e definem o que todo livro e
  toda pessoa têm; `description()` é abstrato e cada filha decide o que mostrar.
- **Herança** — `PhysicalBook` e `Ebook` estendem `Book`; `Member` e `Employee`
  estendem `Person`.
- **Encapsulamento** — atributos `private` com acesso por getters/setters; os
  setters validam (páginas, peso e salário nunca ficam negativos) e o campo
  `available` só muda por `borrow()` e `giveBack()`.
- **Polimorfismo** — o método `describeAll()` de `Menu` lista livros, membros,
  funcionários e empréstimos com o mesmo código, sem `if` de tipo; `Ebook`
  sobrescreve `isAvailable()` mudando a regra, não só o texto impresso.
- **Template Method** — `Menu` guarda o roteiro comum a todos os menus (exibir,
  ler, executar, repetir); cada submenu declara só o título, as opções e o que
  cada uma faz.
- **Interfaces** — `Describable` (sei me descrever) e `Borrowable` (posso ser
  emprestado) representam capacidades: `Loan` se descreve, mas não é emprestável.
- **Exceções** — hierarquia de `LibraryException`, capturada em um único ponto.

## Estrutura do projeto

```
src/
├── Main.java              ponto de entrada: cria a biblioteca e abre o menu
├── app/                   Menu (roteiro comum), o menu principal, os quatro
│                          submenus, ConsoleInput e SampleData
├── interfaces/            Describable, Borrowable
├── entities/              book/, person/, loan/ — o modelo de domínio
├── services/              Library (fachada) e os quatro serviços
└── exceptions/            LibraryException e suas cinco especializações
```
