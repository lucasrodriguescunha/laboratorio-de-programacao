# Cadastro de Pessoa (JavaFX)

Projeto em Java com JavaFX — Laboratório de Programação.
Formulário desktop que cadastra uma pessoa — **CPF**, **nome**, **endereço**,
**estado** e **cargo** — e mostra os dados digitados em uma janela de mensagem
quando o usuário clica em **Imprimir Dados**. É a versão em JavaFX do
[`person-registration`](../person-registration), feito em Swing.

## Como executar

O JavaFX não vem no JDK, então este projeto usa Maven para baixar as
dependências (`javafx-controls` e `javafx-fxml`).

**Pela IDE:** abra a pasta como projeto Maven e execute a classe `Main`
(`src/main/java/Main.java`).

**Pelo terminal**, a partir da raiz do projeto:

```bash
mvn javafx:run
```

Requer JDK 17 ou superior e Maven 3.

## O formulário

O layout fica em `person-form.fxml`: um `GridPane` de duas colunas, com um
`Label` e um campo por linha e o botão ocupando as duas colunas na última. A
aparência (cores, fontes e o botão) fica em `styles.css`.

O campo de CPF usa um `TextFormatter` que aceita só dígitos e aplica o formato
`###.###.###-##` enquanto o usuário digita. Estado e cargo são
`ComboBox<String>` preenchidos com uma `ObservableList<String>` montada a partir
dos enums `State` e `Role` — adicionar uma opção nova é só acrescentar uma
constante ao enum, nada no formulário muda.

O clique no botão é tratado com uma expressão lambda (`setOnAction`) no
controlador. Se algum campo obrigatório estiver vazio, um `Alert` de aviso lista
o que falta; caso contrário, o controlador monta um `Person` com o que está nos
campos e abre um `Alert` de informação com o texto formatado.

## Estrutura do projeto

```
pom.xml                                dependências do JavaFX e o plugin javafx:run
src/main/
├── java/
│   ├── Main.java                      ponto de entrada: inicia a aplicação
│   ├── app/
│   │   └── PersonRegistrationApp.java a Application: carrega o FXML e o CSS e abre o Stage
│   ├── entities/
│   │   ├── Person.java                dados da pessoa
│   │   ├── Role.java                  enum dos cargos
│   │   └── State.java                 enum dos estados brasileiros
│   └── ui/
│       ├── PersonFormController.java  controlador do formulário: combos, máscara, validação e clique
│       └── ResultAlert.java           exibe os dados em um Alert de informação
└── resources/ui/
    ├── person-form.fxml               layout do formulário (GridPane)
    └── styles.css                     estilo do formulário
```
