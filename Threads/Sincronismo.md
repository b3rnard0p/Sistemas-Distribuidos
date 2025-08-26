# Sincronismo em Threads

Este documento explica conceitos fundamentais de **sincronismo** em sistemas concorrentes e distribuídos, com foco em: **relógios (físicos vs lógicos)** e **exclusão mútua** (locks, algoritmos baseados em relógio e mecanismos por eleição/token).

---

## 1. Por que sincronismo é necessário?

Em programas concorrentes e distribuídos, múltiplas threads ou processos podem acessar recursos compartilhados simultaneamente. Problemas comuns:

* **Race conditions**: comportamento depende da ordem de execução.
* **Violação de invariantes**: dados inconsistentes quando várias entidades modificam o mesmo estado.
* **Problemas de ordem**: eventos que deveriam ocorrer antes/depois aparecem fora de ordem.

Objetivos do sincronismo:

* Garantir **exclusão mútua** (apenas um agente no recurso crítico).
* Controlar a **ordenação** de eventos (causalidade).
* Evitar **deadlocks** e **famine (starvation)** quando possível.

---

## 2. Relógios: físico vs lógico

### 2.1. Relógio físico

* É o relógio real do sistema (por exemplo, `System.currentTimeMillis()` / relógios NTP).
  -Útil para timestamps reais, métricas, ordenação aproximada por tempo real.

**Problemas/limitações**:

* Desvio (skew) e deriva entre máquinas distintas.
* Não oferece garantia de ordem causal (dois eventos com timestamps próximos podem ter causalidade invertida).
* Requer sincronização (NTP, PTP) e ainda assim pode ter erros.

**Quando usar**: logs, medidas de latência, ordenação aproximada de eventos quando precisão forte não é necessária.

### 2.2. Relógio lógico

Relógios lógicos são construídos para **capturar causalidade** entre eventos, independente do tempo real.

#### Lamport Timestamps (relogio de Lamport)

* Cada processo mantém um contador inteiro `C`.
* Regras básicas:

  1. `C = C + 1` antes de cada evento local.
  2. Ao enviar uma mensagem, anexa o timestamp `t = C`.
  3. Ao receber uma mensagem com timestamp `t_m`, faz `C = max(C, t_m) + 1`.
* Propriedade: se A -> B (A acontece antes de B causalmente), então `L(A) < L(B)`.
* **Limitação**: ordem total por timestamp não implica causalidade; timestamps iguais ou menores não garantem ausência de causalidade.

#### Vector Clocks (vetor de relógios)

* Cada processo tem um vetor `V[0..N-1]` onde `V[i]` é o número de eventos do processo `i` que o processo conhece.
* Atualização: incrementa sua componente local; ao receber, element-wise max.
* Permite decidir se dois eventos são comparáveis causalmente (`V(a) <= V(b)` para todos os componentes) ou concorrentes (não comparáveis).
* **Vantagem**: preserva a relação de causalidade completa; **desvantagem**: custo O(N) memória e comunicação.

**Resumo**:

* Relógio físico = tempo real (bom para métricas).
* Relógio lógico (Lamport) = ordenação parcial que preserva `happens-before`.
* Vector clocks = informação mais rica sobre causalidade, detecta concorrência.

---

## 3. Exclusão mútua

Exclusão mútua garante que somente uma thread/processo execute uma seção crítica. Vamos ver abordagens locais (locks) e distribuídas (algoritmos baseados em relógios, token/election).

### 3.1. Locks (threads no mesmo processo / SMP)

**Tipos comuns**:

* **Mutex** (mutual exclusion lock): bloqueia o acesso; implementado pelo SO/linguagem.
* **Reentrant lock**: permite que a mesma thread adquira várias vezes.
* **Spinlock**: loop ativo testando uma flag (útil quando espera é curta e custo de context switch é alto).
* **Reader-Writer locks**: permitem múltiplos leitores ou um escritor.
* **Semáforos**: contador que controla acesso a N recursos.

**Propriedades desejadas**:

* **Mutual Exclusion**: somente um entra.
* **Progress (Ausência de deadlock)**: se ninguém está na sec. crítica, alguém que quer entrar deverá conseguir.
* **Bounded waiting (Ausência de starvation)**: garantia de que espera não é indefinida.

**Exemplo (Python)**:

```python
import threading

lock = threading.Lock()

def critical_section():
    with lock:  # adquire e libera automaticamente
        # seção crítica
        do_work()
```

**Problemas a considerar**:

* **Deadlocks**: múltiplos locks tomados em ordens inconsistentes.
* **Priority inversion**: thread de baixa prioridade retém recurso necessário para thread de alta prioridade.
* **Overhead**: locks finos demais aumentam complexidade; locks grosseiros reduzem paralelismo.

**Boas práticas**:

* Manter secções críticas curtas.
* Evitar segurar locks durante operações bloqueantes (I/O, chamadas de rede).
* Sempre adquirir locks em ordem fixa para evitar deadlocks.
* Preferir primitivas de alto nível (condições, barriers) a ad-hoc.

---

### 3.2. Algoritmos distribuídos baseados em relógio (Lamport / Ricart-Agrawala)

Quando processos estão em máquinas diferentes, não há um lock compartilhado simples. Dois algoritmos clássicos:

#### Lamport's Distributed Mutual Exclusion

* Cada processo mantém um relógio de Lamport e uma fila de requisições ordenadas por `(timestamp, process_id)`.
* Passos (simplificado):

  1. Para solicitar o recurso, processo envia `REQUEST(ts)` para todos.
  2. Outros processos respondem `REPLY` imediatamente **a menos** que estejam no seu próprio pedido com timestamp menor; nesse caso adiariam a resposta até liberar.
  3. O solicitante entra na seção crítica somente após receber `REPLY` de todos os outros e quando sua requisição estiver no topo da sua fila local.
  4. Ao liberar, envia `RELEASE` a todos para que estes removam sua entrada da fila.
* **Comunicação**: O(N) mensagens, espera por N-1 replies.
* **Vantagem**: simplicidade; ordem por timestamp (usando relógios lógicos) assegura progressão correta.

#### Ricart–Agrawala (otimização)

* Variante do Lamport que evita mensagens `RELEASE` separadas.
* Regras: ao receber `REQUEST`, responde imediatamente **somente** se não estiver interessado ou se o pedido remoto tiver timestamp menor; caso contrário adia a resposta.
* O solicitante entra na seção crítica após receber N-1 respostas.
* **Complexidade**: mensagens por entrada = N-1 (mais eficiente que Lamport que usa 2(N-1) em algumas formulações).

**Uso de relógios**: ambos dependem de timestamps lógicos (Lamport), não do tempo físico. Os timestamps resolvem empates e produzem uma ordem total determinística.

---

### 3.3. Token-based / eleição (Token Ring, Election)

#### Token Ring (mecanismo de token)

* Um token único circula entre os processos em anel lógico.
* O processo que possui o token pode entrar na seção crítica.
* Ao terminar, passa o token adiante.
* **Vantagens**: muito simples, evita mensagens para cada pedido (token passa periodicamente).
* **Desvantagens**: latência depende do tempo de passagem do token; falha do detentor do token precisa de recuperação (election).

#### Eleição (Bully, Ring)

* Algoritmos de eleição escolhem um coordenador (coordenador pode controlar o token ou tomar decisões centralizadas).
* **Bully**: processo com maior id assume; derrota os menores; troca de mensagens O(N)–O(N²) dependendo da falha.
* **Ring election**: mensagem circula no anel com o id do candidato, o maior vence.

**Como se relaciona com exclusão mútua**:

* Um coordenador eleito pode gerenciar permissões centralmente (recebe pedidos e concede accesso). Esse padrão é simples, mas cria um ponto único de falha e bottleneck.

---

## 4. Comparações rápidas

| Abordagem                 |                   Escopo |        Latência |           Mensagens |                     Robustez a falhas | Quando usar                            |
| ------------------------- | -----------------------: | --------------: | ------------------: | ------------------------------------: | -------------------------------------- |
| Locks (Mutex OS)          |  Mesma memória (threads) |           Baixa | N/A (memória local) |                              Boa (SO) | Threads no mesmo processo              |
| Lamport / Ricart-Agrawala |   Processos distribuídos |        Moderada |           O(N) msgs |  Sensível a falhas (precisa timeouts) | Sistemas distribuídos sem token        |
| Token Ring                |              Distribuído | Depende do anel |       Baixo (token) |             Sensível a perda do token | Sistemas com baixo churn e anel lógico |
| Coordenador (eleição)     | Distribuído centralizado | Baixa (central) |        Centralizado | Ponto único de falha, precisa eleição | Quando coordenação central é aceitável |
