# Processo vs Thread

## 1. O que é um processo e como funciona?

- **Definição:**
  - Um processo é a instância de um programa em execução, contendo seu próprio espaço de memória e recursos do sistema.
- **Funcionamento:**
  1. **Criação:** O sistema operacional cria uma estrutura de dados (PCB – Process Control Block) para gerenciar o processo.
  2. **Escalonamento:** O escalonador do SO decide quando e por quanto tempo o processo será executado na CPU.
  3. **Execução:** O processo opera em seu espaço de endereço isolado, acessando memória, arquivos e recursos.
  4. **Comunicação e Sincronização:** Se necessário, usa Comunicação Inter-Processos (IPC) – pipes, sockets, memória compartilhada, etc.
  5. **Término:** Ao concluir, o processo libera recursos e informa ao SO seu estado final.

## 2. O que é uma thread e como funciona?

- **Definição:**
  - Uma thread (ou linha de execução) é a menor unidade de execução dentro de um processo.
- **Funcionamento:**
  1. **Criação:** Inicia-se dentro de um processo existente, compartilhando espaço de memória e recursos.
  2. **Escalonamento:** O SO escalona threads de diferentes processos ou do mesmo processo, usando PCB de threads.
  3. **Execução:** Threads do mesmo processo podem executar concorrentemente, acessando as mesmas variáveis e memória.
  4. **Sincronização:** Para evitar condições de corrida, usam mecanismos como mutex, semáforos e monitores.
  5. **Término:** Cada thread pode terminar independentemente; o processo só termina quando todas as threads finalizam.

## 3. Diferenças principais

| Aspecto               | Processo                              | Thread                             |
|-----------------------|---------------------------------------|------------------------------------|
| Espaço de memória     | Isolado (próprio)                     | Compartilhado dentro do processo   |
| Criação               | Mais custoso (alocação de recursos)   | Menos custoso (herda recursos)     |
| IPC / Comunicação     | IPC (pipes, sockets, etc.)            | Acesso direto à memória compartilhada |
| Falha                | Falha de um não afeta outros processos | Falha pode comprometer todo o processo |
| Overhead de troca     | Alto (context switch pesado)          | Baixo (context switch leve)        |
