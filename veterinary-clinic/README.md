# Animais

Lista de atividades (04) — Laboratório de Programação.
Projeto em Java que modela os **animais** de uma clínica veterinária, aplicando
classes, objetos, encapsulamento, herança, polimorfismo e abstração. O
veterinário atende uma lista de animais de espécies diferentes, pede que cada um
emita o seu som e o encaminha para a carrocinha.

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

`Animal` é abstrata: guarda o que todo animal tem — **nome**, **idade** e
**cor** — e deixa para as subclasses as duas partes que mudam de espécie para
espécie.

| Método | Onde está | O que faz |
|---|---|---|
| `species()` | abstrato, cada subclasse responde | Nome da espécie: `Cachorro`, `Gato`, `Cavalo`, `Coelho` |
| `speciesSound()` | abstrato e `protected` | Som padrão da espécie |
| `setSound(som)` | `Animal` | Troca o som **deste** animal; `null` devolve o som da espécie |
| `getSound()` | `Animal` | O som trocado, ou o da espécie se nenhum foi definido |
| `emitSound()` | `Animal` | Imprime `Nome faz: <som>` |
| `description()` | `Animal` | Imprime espécie, nome, idade e cor |

`speciesSound()` é `protected` porque só interessa dentro da hierarquia: quem
está de fora pergunta o som por `getSound()`, sem saber se ele veio da espécie
ou foi trocado. É o que permite atender o item 04 do enunciado — alterar o som
de um animal — sem que cada subclasse precise saber disso.

As espécies são quatro: o enunciado pede `Cachorro`, `Gato` e `Cavalo` no item
03 e cita o `Coelho` na lista do veterinário, no item 05. Acrescentar uma
espécie custa só a classe nova: nada em `Animal`, em `Veterinarian` ou no `Main`
precisou mudar para atender o coelho.

## O veterinário

`Veterinarian` mantém duas listas de `Animal`: a fila de atendimento e a
carrocinha.

| Método | O que faz |
|---|---|
| `receive(animal)` | Coloca o animal na fila de atendimento |
| `attendAll()` | Percorre a fila: descreve o animal, chama `emitSound()` e o encaminha para a carrocinha |
| `sendToVan(animal)` | Coloca o animal na carrocinha |
| `listVan()` | Lista quem está na carrocinha |
| `getAnimals()` / `getVan()` | Devolvem as listas somente leitura |

As listas são `ArrayList<Animal>`, e não `ArrayList<Dog>`: é o que permite
guardar cachorro, gato, cavalo e coelho lado a lado e tratá-los da mesma forma.

## Saída do programa

```
----- ATENDIMENTO -----
Responsável: Alex Ramos

Cachorro: Rex | Idade: 3 ano(s) | Cor: Caramelo
Rex faz: Au au!

Gato: Mimi | Idade: 2 ano(s) | Cor: Branco
Mimi faz: Miau!

Cavalo: Trovão | Idade: 7 ano(s) | Cor: Preto
Trovão faz: Relincho!

Cachorro: Bidu | Idade: 9 ano(s) | Cor: Cinza
Bidu faz: Au... au rouco

Coelho: Pipoca | Idade: 1 ano(s) | Cor: Branco
Pipoca faz: Rangido baixinho!

----- CARROCINHA -----
Cachorro: Rex
Gato: Mimi
Cavalo: Trovão
Cachorro: Bidu
Coelho: Pipoca
```

Repare no **Bidu**: ele é um `Dog` que recebeu `setSound("Au... au rouco")` no
`Main`. O som dele mudou; o do Rex, que é da mesma espécie, continua `Au au!`.

## Conceitos de POO aplicados

- **Abstração** — `Animal` é abstrata e define o que todo animal tem; não existe
  "animal genérico" na sala de espera, só cachorro, gato, cavalo ou coelho.
- **Herança** — `Dog`, `Cat`, `Horse` e `Rabbit` estendem `Animal` e herdam
  nome, idade, cor e o mecanismo do som.
- **Encapsulamento** — atributos `private` com acesso por getters/setters; o
  setter da idade valida a entrada, então ela nunca fica negativa, e as listas
  do veterinário são devolvidas somente leitura.
- **Polimorfismo** — `attendAll()` percorre a lista chamando `description()` e
  `emitSound()` sem nenhum teste de tipo: cada animal responde do seu jeito, e
  uma espécie nova não muda uma linha do método.

## Estrutura do projeto

```
src/
├── Main.java                    ponto de entrada: monta a fila e manda atender
└── entities/
    ├── Animal.java (abstract)   nome, idade, cor e o som (da espécie ou trocado)
    ├── Dog.java                 Cachorro — "Au au!"
    ├── Cat.java                 Gato — "Miau!"
    ├── Horse.java               Cavalo — "Relincho!"
    ├── Rabbit.java              Coelho — "Rangido baixinho!"
    └── Veterinarian.java        fila de atendimento e carrocinha
```
