# Pool de Threads

## 1. O que é um Pool de Threads?

Um *thread pool* é um componente que mantém um conjunto (pool) de threads reutilizáveis para executar tarefas (jobs, runnables, callables). Em vez de criar e destruir uma thread para cada tarefa, um pool permite reaproveitar threads, reduzindo overheads de criação e destruição e controlando concorrência.

**Principais responsabilidades**:

* Gerenciar o ciclo de vida das threads (criar, reutilizar, destruir).
* Receber tarefas e entregá-las a threads livres.
* Controlar a concorrência máxima (número de threads ativas).
* Implementar políticas de fila e rejeição quando há sobrecarga.

---

## 2. Por que usar um Pool de Threads?

* **Reduz overhead** de criação de threads repetidas.
* **Limita a concorrência** e protege recursos (CPU, memória, conexões externas).
* **Permite políticas** (prioridade, deadlines, rejeição) e métricas (tarefas em fila, threads ativas).
* **Melhora performance** em aplicações com tarefas curtas e frequentes.

---

## 3. Arquitetura básica

Um pool típico tem:

* **Fila de tarefas (work queue)**: onde novas tarefas aguardam.
* **Conjunto de workers (threads)**: que buscam tarefas na fila e as executam.
* **Thread factory**: cria threads com políticas (nome, daemon, prioridades).
* **Rejection handler**: define o que fazer quando a fila está cheia.
* **Parâmetros de configuração**: `corePoolSize`, `maximumPoolSize`, `keepAliveTime`, `queueCapacity`.

Fluxo simplificado:

1. Cliente submete uma tarefa.
2. Se há thread livre, ela executa imediatamente.
3. Senão, a tarefa vai para a fila (se houver espaço).
4. Se a fila estiver cheia e `poolSize < maximumPoolSize`, cria-se nova thread.
5. Se a fila estiver cheia e `poolSize == maximumPoolSize`, aplica-se a política de rejeição.

---

## 4. Tipos comuns de Pool

### 4.1. Fixed-size (tamanho fixo)

* `corePoolSize = maximumPoolSize = N`
* Fila normalmente *limitada* ou *ilimitada* (dependendo da implementação).
* Garantia de número estável de threads.
* Bom para trabalhar com carga previsível.

### 4.2. Cached (dinâmico / crescimento elástico)

* Inicia com poucas threads; cresce até `Integer.MAX_VALUE` em implementações como `Executors.newCachedThreadPool()` (Java).
* Usa fila *síncrona* ou sem fila para que novas threads sejam criadas quando não houver threads livres.
* Adequado para tarefas curtas e grandes rajadas de requisições; perigoso com tarefas bloqueantes (pode criar muitas threads -> OOM).

### 4.3. Scheduled (agendado)

* Suporta execução periódica ou com atraso (ex.: `ScheduledThreadPoolExecutor`).
* Usado para timers, tarefas periódicas de manutenção.

### 4.4. Work-stealing / ForkJoinPool

* Cada worker tem uma deque (double-ended queue).
* Threads roubam trabalho de outras deques quando o próprio deque está vazio (work stealing).
* Eficiente para *divide-and-conquer* (tarefas recursivas divididas em subtarefas).
* Implementações: `ForkJoinPool` (Java), frameworks de concorrência funcional.

---

## 5. Conclusão

Thread pools são ferramentas essenciais para gerenciar concorrência de maneira eficiente e previsível. O segredo é **entender o perfil das tarefas (CPU vs I/O)**, escolher a **estrutura correta** (tipo de pool e fila), e **monitorar** o comportamento em produção para ajustar parâmetros.
