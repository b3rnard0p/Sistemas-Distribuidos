# Threads

## 1. Tipos de Threads

### 1.1 Threads com memória compartilhada  
- **Definição**: múltiplas threads acessam o mesmo espaço de memória (mesmo heap).  
- **Vantagens**:  
  - Comunicação direta por variáveis globais ou objetos compartilhados.  
  - Menor overhead de cópia de dados.  
- **Desvantagens**:  
  - **Concorrência e sincronização**: necessidade de mutexes, semáforos ou monitores para evitar condições de corrida.  
  - **Complexidade**: bugs de sincronização (deadlocks, starvation) são comuns e difíceis de depurar.

### 1.2 Threads sem memória compartilhada  
- **Definição**: cada thread tem seu próprio espaço de memória; comunicação ocorre via troca de mensagens ou filas.  
- **Vantagens**:  
  - Mais segurança: evita condições de corrida, pois não há estado compartilhado.  
  - Modelo mais escalável em sistemas distribuídos.  
- **Desvantagens**:  
  - **Overhead de comunicação**: serialização/deserialização de mensagens.  
  - **Latência**: troca de mensagens pode ser mais lenta que acesso direto à memória.

---

## 2. Uso de `join` em Threads

- **O que é `join`?**  
  Método que faz a thread chamadora esperar até que a thread alvo termine sua execução.  
- **Quando usar**:  
  - Para garantir dependências de resultado sequencial.  
  - Em cenários em que o dado produzido por uma thread é imprescindível antes de prosseguir.  
- **Problemas de uso excessivo**:  
  - **Bloqueio excessivo**: muitas esperas anulam o paralelismo, degradando desempenho.  
  - **Perda de sentido de concorrência**: se cada thread é imediatamente `join`-ada, a execução se comporta como sequencial.  
  - **Complexidade de fluxo**: encadeamento de `join` em loops ou listas de threads complica a leitura do código.

---

## 3. Boas Práticas

1. **Minimize seções críticas**  
   Faça o menor escopo possível de código protegido por locks.  
2. **Prefira comunicação assíncrona**  
   Use filas seguras (ex.: `BlockingQueue` em Java) em vez de memória compartilhada direta.  
3. **Agrupe joins quando necessário**  
   Em vez de dar `join` logo após criar cada thread, crie todas, execute em paralelo e sincronize todas de uma vez.  
4. **Considere alternativas**  
   - Pools de threads (ex.: `ExecutorService`) para gerenciar lifecycle e evitar overhead de criação/destruição constante.  
   - Frameworks de alto nível (ex.: OpenMP, Intel TBB, Ray) para paralelismo de dados ou tarefas.
