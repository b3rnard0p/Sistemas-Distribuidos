# Processo vs Thread

## 1. O que é um processo e como funciona?

- **Definição**  
  Um processo é a instância de um programa em execução, incluindo seu ambiente de execução: código, dados atuais, registradores, contador de programa, privilégios e limites de recursos.

- **Contexto de hardware e software**  
  O contexto de hardware representa o estado da CPU,  guarda ponteiros, flags e o program counter (PC) — que aponta para a próxima instrução a ser executada. O contexto de software inclui PID, estado e privilégios.

- **Estados de um processo**  
  - Execução (running)  
  - Pronto (ready)  
  - Espera / bloqueado (waiting)

- **Escalonamento e troca de contexto**  
  O sistema operacional realiza multitarefa e preempção, alternando processos conforme prioridades, aging e interrupções de I/O. Cada troca de contexto armazena o estado em PCB (Process Control Block).

- **Tipos de processos**  
  - CPU-bound: consome intensamente a CPU  
  - I/O-bound: espera por operações de entrada/saída

## 2. O que é uma thread e como funciona?

- **Definição**  
  Uma thread é a menor unidade de execução dentro de um processo; representa uma sequência de instruções que compartilha o espaço de memória do processo.

- **Funcionamento e criação**  
  Threads são criadas dentro de processos e herdam recursos como memória e arquivos abertos. Executam tarefas concorrentemente.

- **Multithreading e SMT**  
  CPUs modernas podem executar múltiplas threads simultaneamente via SMT (Simultaneous Multithreading): núcleos físicos funcionam como múltiplos núcleos virtuais.

- **Vantagens das threads**  
  - Menor overhead de criação e destruição  
  - Melhor aproveitamento de CPU  
  - Compartilhamento direto de recursos

- **Context switching entre threads**  
  Alternar entre threads é mais leve do que entre processos, porém ainda requer salvar e restaurar o contexto.

## 3. Diferenças principais

| Aspecto               | Processo                                                  | Thread                                                         |
|-----------------------|-----------------------------------------------------------|----------------------------------------------------------------|
| Espaço de memória     | Isolado, próprio                                          | Compartilhado dentro do processo                               |
| Overhead              | Alto (criação e troca de contexto pesados)                | Baixo (troca rápida, criação mais leve)                        |
| Comunicação           | Requer IPC (pipes, sockets, memória compartilhada)        | Comunicação direta via memória compartilhada                   |
| Tolerância a falhas   | Falha isolada; outros processos seguem                    | Falha pode afetar todo o processo                              |
| Escalonamento         | Via PCB, com prioridades, aging, filas de I/O             | Interno ao processo; pode usar agendamento de usuário ou SO    |
| Paralelismo real      | Limitado (um processo por núcleo)                         | Pode usar múltiplos núcleos físicos ou SMT para paralelismo real |
