# Sistema Bancário

Lista de atividades (03) — Laboratório de Programação.
Sistema de console em Java que abre uma conta bancária e realiza as operações do
enunciado — **creditar**, **debitar**, **consultar saldo** e **encerrar a conta** —
com as entradas protegidas por `try/catch` e por exceções próprias do domínio.
O programa se encerra quando o usuário encerra a conta (ou escolhe sair).

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

## A conta da demonstração

O `Main` abre, antes de o menu aparecer, a conta pedida no enunciado:

| Número | Agência | Nome da agência | Tipo | Saldo |
|---|---|---|---|---|
| 1 | 100 | Sede | 1 (conta corrente) | R$ 0,00 |

Os tipos são os do enunciado: **1** corrente, **2** poupança, **3** conjunta e
**4** conta encerrada. Eles aparecem no código como as constantes `CHECKING`,
`SAVINGS`, `JOINT` e `CLOSED` de `Account`, porque `type == 4` espalhado pelo
código não diz nada e `type == CLOSED` diz.

## Menu

| Opção | Ação | Método de `Account` | Regra aplicada |
|---|---|---|---|
| 1 | Creditar | `credit(valor)` | Conta aberta e valor maior que zero |
| 2 | Debitar | `debit(valor)` | Conta aberta e valor maior que zero |
| 3 | Consultar saldo | `balanceText()` | — |
| 4 | Encerrar conta | `close()` + `closingText(valor)` | Saldo não pode estar negativo |
| 5 | Sair | — | Sai do programa com a conta aberta |

Qualquer outro número exibe `Opção inválida.` e mostra o menu de novo; um texto
no lugar do número faz o programa pedir a opção outra vez, sem quebrar.

**Por que o débito não verifica o saldo:** o enunciado prevê que a conta possa
ficar negativa — é o cheque especial — e é justamente por isso que o
encerramento precisa recusar saldo negativo. Se o débito barrasse, a regra do
encerramento nunca teria efeito.

**Encerrar** devolve o saldo que estava na conta, zera o saldo e muda o tipo
para 4. O texto de encerramento recebe esse valor de volta, porque a essa
altura o saldo já é 0.

## Tratamento de erros

Todas as exceções do domínio herdam de `BankException`, e o menu é o **único**
lugar que as captura: um `catch (BankException e)` em `AccountMenu.start()`, que
atende a qualquer opção, exibe a mensagem e devolve o usuário ao menu.

| Situação | Exceção | Mensagem exibida |
|---|---|---|
| Encerrar com saldo negativo | `NegativeBalanceException` | `A conta 1 está com saldo negativo (R$ -49,50) e não pode ser encerrada.` |
| Movimentar conta encerrada | `AccountClosedException` | `A conta 1 está encerrada (tipo 4) e não é possível creditar.` |
| Valor menor ou igual a zero | `BankException` | `O valor informado deve ser maior que zero.` |
| Tipo de conta inválido na abertura | `BankException` | `Tipo de conta inválido: 7. Use 1, 2, 3 ou 4.` |

Fora deles, o `Main` tem o `try/catch/finally` do enunciado: a rede que
transforma qualquer falha não prevista em uma mensagem, em vez de uma pilha de
exceção na tela, e o `finally` que fecha o `Scanner` aconteça o que acontecer.

## Roteiro de demonstração

Sequência sugerida para mostrar o sistema funcionando, incluindo os casos de
erro.

**1. Consultar o saldo inicial** — opção `3`: `Conta 1 (Conta corrente) | Saldo: R$ 0,00`.

**2. Creditar** — opção `1`, valor `150,50`. O novo saldo é exibido logo depois.
A vírgula é aceita como separador decimal.

**3. Debitar mais do que existe** — opção `2`, valor `200`. O saldo fica
`R$ -49,50`: a conta entrou no negativo, como o enunciado prevê.

**4. Tentar encerrar no negativo** — opção `4`:
`Erro: A conta 1 está com saldo negativo (R$ -49,50) e não pode ser encerrada.`
O menu continua aberto.

**5. Erros de entrada** — opção `9` responde `Opção inválida.`; digitar `abc`
responde `Digite um número inteiro válido.`; creditar `0` responde
`Erro: O valor informado deve ser maior que zero.`

**6. Encerrar de verdade** — opção `1`, valor `100` (o saldo volta a `R$ 50,50`)
e depois opção `4`:

```
Conta 1 encerrada.
Agência: 100 - Sede
Tipo: 4 - Conta encerrada
Saldo devolvido: R$ 50,50
```

O programa termina, como pede o enunciado.

## Conceitos aplicados

- **Encapsulamento** — os atributos são privados e o saldo não tem setter: ele
  só muda dentro de `credit()`, `debit()` e `close()`, que são os únicos lugares
  onde as regras são aplicadas.
- **Separação de responsabilidades** — `Account` decide o que é permitido,
  `AccountMenu` só conversa com o usuário e `ConsoleInput` só lê e valida o que
  é digitado. A verificação de conta encerrada fica na entidade, uma única vez,
  e não repetida em cada opção do menu.
- **Exceções personalizadas** — hierarquia de `BankException`, capturada em um
  único ponto graças ao polimorfismo.
- **Proteção das entradas** — texto no lugar de número vira uma nova pergunta;
  fim da entrada (Ctrl+Z) vira mensagem, e não `NoSuchElementException`.

## Estrutura do projeto

```
src/
├── Main.java              ponto de entrada: abre a conta e o menu; try/catch/finally
├── app/
│   ├── AccountMenu.java   menu de console: as cinco opções do enunciado
│   └── ConsoleInput.java  leitura e validação da entrada digitada
├── entities/
│   └── Account.java       a conta: dados, operações e regras
└── exceptions/
    ├── BankException.java              base de todas as exceções do domínio
    ├── AccountClosedException.java     operação em conta encerrada
    └── NegativeBalanceException.java   encerramento com saldo negativo
```
