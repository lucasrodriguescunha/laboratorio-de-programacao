# Sistema de Biblioteca

Projeto em Java do Laboratório de Programação que modela o acervo de uma
**biblioteca** com livros físicos e digitais, aplicando classes, objetos,
encapsulamento, herança, polimorfismo e abstração.

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

## O modelo

`Book` é abstrata: guarda o que todo livro tem — **título**, **autor** e
**número de páginas** — e deixa a descrição para as subclasses.

| Classe | Atributo próprio | Descrição impressa |
|---|---|---|
| `PhysicalBook` | peso (g) | `Livro físico: <título> \| Autor: ... \| Páginas: ... \| Peso: ...g` |
| `Ebook` | tamanho do arquivo (MB) | `Ebook: <título> \| Autor: ... \| Páginas: ... \| Tamanho: ...MB` |

Páginas, peso e tamanho negativos viram `0` nos setters.

`Library` mantém a lista de livros:

| Método | O que faz |
|---|---|
| `addBook(livro)` | Adiciona um livro ao acervo |
| `listBooks()` | Chama `description()` de cada livro |
| `findBookByTitle(título)` | Devolve o primeiro livro com o título exato, ou `null` |

## Saída do programa

```
Livro físico: Clean Code | Autor: Robert C. Martin | Páginas: 672 | Peso: 784.0g
Ebook: Clean Code | Autor: Robert C. Martin | Páginas: 672 | Tamanho: 16.9MB
```

## Conceitos de POO aplicados

- **Abstração** — `Book` é abstrata e declara `description()` sem implementá-lo.
- **Herança** — `PhysicalBook` e `Ebook` estendem `Book` e herdam título, autor
  e páginas.
- **Encapsulamento** — atributos `private` com acesso por getters/setters, que
  impedem valores negativos.
- **Polimorfismo** — `Library` guarda uma `List<Book>` e `listBooks()` chama
  `description()` sem saber se o livro é físico ou digital.

## Estrutura do projeto

```
src/
├── Main.java                  ponto de entrada: monta o acervo e o lista
└── entities/
    ├── Book.java (abstract)   título, autor e páginas
    ├── PhysicalBook.java      livro físico, com peso
    ├── Ebook.java             livro digital, com tamanho do arquivo
    └── Library.java           acervo: adicionar, listar e buscar por título
```
