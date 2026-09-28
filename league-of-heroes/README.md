# Liga de Heróis

Projeto em Java do Laboratório de Programação que modela uma **liga de heróis**,
aplicando classes, objetos, encapsulamento, herança, polimorfismo e abstração.
Cada herói se apresenta com nome, vida e energia e ataca do seu próprio jeito.

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

`Hero` é abstrata: guarda o que todo herói tem — **nome**, **vida** e
**energia** — e deixa o ataque para as subclasses.

| Método | Onde está | O que faz |
|---|---|---|
| `setLife(vida)` / `setEnergy(energia)` | `Hero` | Valores negativos viram `0` |
| `introduce()` | `Hero` | Imprime `Olá, eu sou <nome> \| Vida: ... \| Energia: ...` |
| `attack()` | abstrato, cada subclasse responde | Imprime o golpe característico do herói |

| Herói | Ataque |
|---|---|
| `Goku` | Kamehameha |
| `Sonic` | Ataque girando em alta velocidade |
| `Mario` | Bola de fogo |

## Saída do programa

```
Olá, eu sou Goku | Vida: 1000000000 | Energia: 1000000000
Goku lança um Kamehameha!

Olá, eu sou Sonic | Vida: 100 | Energia: 100
Sonic ataca girando em alta velocidade!

Olá, eu sou Mario | Vida: 100 | Energia: 100
Mario lança uma bola de fogo!
```

## Conceitos de POO aplicados

- **Abstração** — `Hero` é abstrata e não pode ser instanciada; só existem
  heróis concretos.
- **Herança** — `Goku`, `Sonic` e `Mario` estendem `Hero` e herdam nome, vida,
  energia e a apresentação.
- **Encapsulamento** — atributos `private` com acesso por getters/setters; os
  setters impedem vida e energia negativas.
- **Polimorfismo** — o `Main` guarda os heróis em um `Hero[]` e chama
  `introduce()` e `attack()` sem testar o tipo: cada um ataca do seu jeito.

## Estrutura do projeto

```
src/
├── Main.java                 ponto de entrada: cria os heróis e os faz atacar
└── entities/
    ├── Hero.java (abstract)  nome, vida, energia e a apresentação
    ├── Goku.java             Kamehameha
    ├── Sonic.java            ataque girando
    └── Mario.java            bola de fogo
```
