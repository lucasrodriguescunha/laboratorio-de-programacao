# Controle de Clientes

Projeto em Java com Swing — Laboratório de Programação.
Janela desktop com barra de menus (**Arquivo**, **Relatório** e **Sobre**) que
abre um formulário de cadastro de cliente — **CPF**, **nome**, **telefone** e
**estado** — e confirma o cadastro ao clicar em **Salvar**.

## Como executar

**Pela IDE:** execute a classe `Main` (`src/Main.java`).

**Pelo terminal**, a partir da raiz do projeto:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
cp -r src/images out/
java -cp out Main
```

No Windows, sem o `find` disponível:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
Copy-Item -Recurse -Force src/images out/
java -cp out Main
```

A cópia da pasta `images` é necessária porque o `javac` só gera os `.class`; a
logo é carregada do classpath. Pela IDE isso acontece automaticamente.

Requer JDK 17 ou superior.

## A janela

A janela usa um `CardLayout` com duas telas: a de boas-vindas, com o título e a
logo, e o formulário de cadastro. **Arquivo → Novo** abre o formulário;
**Salvar** e **Cancelar** limpam os campos e voltam para a tela inicial.

Cada item de menu e cada botão tem um ícone circular colorido desenhado em
`Theme.dot`, sem arquivos de imagem. Cores e fontes ficam centralizadas em
`Theme`.

## O formulário

CPF e telefone usam `MaskFormatter` (`###.###.###-##` e `(##) #####-####`) para
forçar o formato enquanto o usuário digita. O estado é um `JComboBox`
preenchido a partir do enum `State`. Ao clicar em **Salvar**, o painel valida
os campos obrigatórios e monta um `Client` com o que foi digitado.

## Estrutura do projeto

```
src/
├── Main.java                  ponto de entrada: abre a janela principal
├── app/
│   └── MainWindow.java        a janela (JFrame): menus e troca entre as telas
├── entities/
│   ├── Client.java            dados do cliente
│   └── State.java             enum dos estados brasileiros
├── images/
│   └── logo-unifagoc.png      logo exibida na tela de boas-vindas
└── ui/
    ├── ClientFormPanel.java   o painel (JPanel): campos, layout e botões
    ├── Theme.java             cores, fontes e o ícone circular dos menus
    └── WelcomePanel.java      tela inicial com título e logo
```
