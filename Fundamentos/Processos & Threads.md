# Processo vs Thread

## 1. O que é um processo e como funciona?

- **Definição**  
  Um processo é a instância de um programa em execução, incluindo seu ambiente de execução: código, dados atuais, registradores, contador de programa, privilégios e limites de recursos :contentReference[oaicite:0]{index=0}.

- **Contexto de hardware e software**  
  O contexto de hardware guarda os registradores da CPU; o contexto de software inclui PID, UID, quotas e privilégios :contentReference[oaicite:1]{index=1}.

- **Estados de um processo**  
  - Execução (running)  
  - Pronto (ready)  
  - Espera / bloqueado (waiting) :contentReference[oaicite:2]{index=2}

- **Escalonamento e troca de contexto**  
  O sistema operacional realiza multitarefa e preempção, alternando processos conforme prioridades, aging e interrupções de I/O. Cada troca de contexto armazena o estado em PCB (Process Control Block) :contentReference[oaicite:3]{index=3}.

- **Tipos de processos**  
  - CPU-bound: consome intensamente a CPU  
  - I/O-bound: espera por operações de entrada/saída :contentReference[oaicite:4]{index=4}

## 2. O que é uma thread e como funciona?

- **Definição**  
  Uma thread é a menor unidade de execução dentro de um processo; representa uma sequência de instruções que compartilha o espaço de memória do processo :contentReference[oaicite:5]{index=5}.

- **Funcionamento e criação**  
  Threads são criadas dentro de processos e herdam recursos como memória e arquivos abertos. Executam tarefas concorrentemente :contentReference[oaicite:6]{index=6}.

- **Multithreading e SMT**  
  CPUs modernas podem executar múltiplas threads simultaneamente via SMT (Simultaneous Multithreading): núcleos físicos funcionam como múltiplos núcleos virtuais :contentReference[oaicite:7]{index=7}.

- **Vantagens das threads**  
  - Menor overhead de criação e destruição  
  - Melhor aproveitamento de CPU  
  - Compartilhamento direto de recursos :contentReference[oaicite:8]{index=8}

- **Context switching entre threads**  
  Alternar entre threads é mais leve do que entre processos, porém ainda requer salvar e restaurar o contexto :contentReference[oaicite:9]{index=9}.

## 3. Diferenças principais

| Aspecto               | Processo                                                  | Thread                                                         |
|-----------------------|-----------------------------------------------------------|----------------------------------------------------------------|
| Espaço de memória     | Isolado, próprio                                           | Compartilhado dentro do processo                               |
| Overhead              | Alto (criação e troca de contexto pesados)                | Baixo (troca rápida, criação mais leve)                        |
| Comunicação           | Requer IPC (pipes, sockets, memória compartilhada)        | Comunicação direta via memória compartilhada                  |
| Tolerância a falhas   | Falha isolada; outros processos seguem                    | Falha pode afetar todo o processo                              |
| Escalonamento         | Via PCB, com prioridades, aging, filas de I/O :contentReference[oaicite:10]{index=10} | Interno ao processo; pode usar agendamento de usuário ou SO     |
| Paralelismo real      | Limitado (um processo por núcleo)                         | Pode usar múltiplos núcleos físicos ou SMT para paralelismo real |
