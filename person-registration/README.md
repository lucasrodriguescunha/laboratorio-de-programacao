# Cadastro de Pessoa

Projeto em Java com Swing — Laboratório de Programação.
Formulário desktop que cadastra uma pessoa — **CPF**, **nome**, **endereço**,
**estado** e **cargo** — e mostra os dados digitados em uma janela à parte
quando o usuário clica em **Imprimir**.

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

## O formulário

O campo de CPF usa `MaskFormatter` (`###.###.###-##`) para forçar o formato
enquanto o usuário digita. Estado e cargo são `JComboBox` preenchidos a partir
dos enums `State` e `Role` — adicionar uma opção nova é só acrescentar uma
constante ao enum, nada no formulário muda.

Ao clicar em **Imprimir**, o painel monta um `Person` com o que está nos campos
e abre um `JDialog` somente leitura com o texto formatado.

## Estrutura do projeto

```
src/
├── Main.java                     ponto de entrada: abre o formulário
├── app/
│   └── PersonRegistrationForm.java   a janela (JFrame): monta o painel e liga o clique ao diálogo
├── entities/
│   ├── Person.java                dados da pessoa e o texto formatado para exibição
│   ├── Role.java                  enum dos cargos
│   └── State.java                 enum dos estados brasileiros
└── ui/
    ├── PersonFormPanel.java       o painel (JPanel): campos, layout e botão
    └── ResultDialog.java          o diálogo (JDialog) que exibe os dados
```
