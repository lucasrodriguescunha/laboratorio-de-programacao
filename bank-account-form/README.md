# Formulário de Conta Bancária

Projeto em Java com Swing — Laboratório de Programação.
Janela desktop com os dados bancários — **código da agência** e **número da
conta** — e os dados do cliente — **nome**, **endereço**, **telefone** e
**CPF** —, além da escolha entre **Conta Corrente** e **Conta Poupança**.

## Como executar

**Pela IDE:** execute a classe `Principal` (`src/Principal.java`).

**Pelo terminal**, a partir da raiz do projeto:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Principal
```

No Windows, sem o `find` disponível:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out Principal
```

Requer JDK 17 ou superior.

## A janela

A `Janela` usa layout nulo (`getContentPane().setLayout(null)`) e posiciona
cada componente com `setSize` e `setLocation`, nas medidas pedidas no
exercício. A janela tem 400 x 255 pixels, abre centralizada e não pode ser
redimensionada.

Os campos de agência, conta, telefone e CPF são `JFormattedTextField` com
`MaskFormatter`, que forçam o formato enquanto o usuário digita:

| Campo              | Máscara           |
|--------------------|-------------------|
| Código da Agência  | `####-#`          |
| Número da Conta    | `#####-#`         |
| Telefone           | `(##) #####-####` |
| CPF                | `###.###.###-##`  |

Os dois `JRadioButton` ficam no `ButtonGroup` `bgContas`, então só um tipo de
conta fica selecionado por vez — **Conta Corrente** vem marcada ao abrir.
O botão **Atualizar** começa desabilitado e **Fechar** encerra a aplicação.

Atalhos de teclado (mnemônicos):

| Atalho    | Componente     |
|-----------|----------------|
| Alt + C   | Conta Corrente |
| Alt + P   | Conta Poupança |
| Alt + S   | Consultar      |
| Alt + A   | Atualizar      |
| Alt + F   | Fechar         |

> O enunciado posiciona o botão **Fechar** em x = 225, o que o sobrepõe ao
> **Atualizar** (145 + 100 = 245). O código usa x = 255, mantendo os 10 px de
> espaço entre os botões, como aparece na figura.

## Estrutura do projeto

```
src/
├── Principal.java        ponto de entrada: abre a janela
└── app/
    └── Janela.java       a janela (JFrame): atributos, medidas e posições dos componentes
```
