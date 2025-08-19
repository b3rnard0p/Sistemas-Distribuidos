## Conceitos Fundamentais em Sistemas Distribuídos 

### 1. Relógios Físicos (Physical Clocks)

Em sistemas distribuídos, cada nó (computador) possui seu próprio relógio de hardware, geralmente um cristal de quartzo. No entanto, esses cristais não são perfeitos e oscilam em frequências ligeiramente diferentes. Essa pequena variação, conhecida como "deriva do relógio" (*clock drift*), faz com que os relógios das máquinas se dessincronizem com o tempo.

**Conceito-chave:** O objetivo da sincronização de relógios físicos é garantir que os processos em diferentes máquinas compartilhem uma noção de tempo consistente e próxima do tempo real (UTC - Tempo Universal Coordenado).

**Por que é importante?**
- **Ordenação de eventos no mundo real:** Para aplicações que dependem do tempo real, como sistemas de transações financeiras ou sistemas de controle industrial.
- **Autenticação:** Protocolos como o Kerberos dependem de *timestamps* para evitar ataques de repetição.
- **Consistência:** Para garantir a ordem correta de operações em bancos de dados distribuídos.

**Algoritmos Comuns de Sincronização:**
* **Algoritmo de Cristian:** Um cliente solicita a hora a um servidor de tempo. Ao receber a resposta, o cliente ajusta seu relógio, levando em conta o tempo de ida e volta da mensagem para estimar o atraso da rede.
* **Algoritmo de Berkeley:** Um servidor de tempo periodicamente consulta a hora de todos os clientes. Ele calcula uma média e envia instruções de ajuste para cada cliente, centralizando a noção de tempo no sistema.
* **NTP (Network Time Protocol):** O protocolo padrão da internet. Utiliza uma hierarquia de servidores de tempo para alcançar alta precisão na sincronização.

### 2. Relógios Lógicos (Logical Clocks)

Em muitos sistemas distribuídos, a ordem causal dos eventos é mais importante do que o tempo exato em que ocorreram. Relógios lógicos fornecem um mecanismo para capturar a relação de "aconteceu antes" ($\rightarrow$) entre eventos, sem a necessidade de sincronizar com o tempo real.

**A Relação "Aconteceu Antes" (Happened-Before):**
1.  Se A e B são eventos no mesmo processo e A ocorre antes de B, então $A \rightarrow B$.
2.  Se A é o envio de uma mensagem por um processo e B é o recebimento dessa mesma mensagem por outro processo, então $A \rightarrow B$.
3.  Se $A \rightarrow B$ e $B \rightarrow C$, então $A \rightarrow C$ (transitividade).

Dois eventos, A e B, são considerados **concorrentes** ($A || B$) se não podemos dizer que $A \rightarrow B$ ou $B \rightarrow A$.

#### Tipos de Relógios Lógicos:

* **Timestamps de Lamport:**
    * Cada processo mantém um contador numérico.
    * O contador é incrementado a cada evento local.
    * Quando uma mensagem é enviada, ela carrega o valor atual do contador.
    * Ao receber uma mensagem, o processo receptor ajusta seu contador para `max(contador_local, timestamp_recebido) + 1`.
    * **Propriedade:** Se $A \rightarrow B$, então $Timestamp(A) < Timestamp(B)$.
    * **Limitação:** O inverso não é verdadeiro. Se $Timestamp(A) < Timestamp(B)$, não podemos concluir que $A \rightarrow B$. Eles podem ser concorrentes.

* **Relógios Vetoriais (Vector Clocks):**
    * São uma evolução dos timestamps de Lamport que superam sua limitação.
    * Cada processo mantém um vetor de contadores, com uma entrada para cada processo no sistema.
    * A cada evento local, o processo incrementa seu próprio contador no vetor.
    * Quando uma mensagem é enviada, ela carrega o vetor de relógios do remetente.
    * Ao receber uma mensagem, o processo receptor atualiza seu vetor, pegando o `max` de cada elemento do seu próprio vetor e do vetor recebido, e então incrementa seu próprio contador.
    * **Propriedade:** Um evento A é causalmente anterior a um evento B ($A \rightarrow B$) se, e somente se, o relógio vetorial de A for menor que o de B.

### 3. Exclusão Mútua (Mutual Exclusion)

Exclusão mútua é um mecanismo que garante que apenas um processo por vez possa acessar um recurso compartilhado ou executar uma seção de código crítico. Em sistemas distribuídos, isso é um desafio, pois não há memória compartilhada ou um sistema operacional central para gerenciar o acesso.

**Requisitos para um bom algoritmo:**
1.  **Segurança (Safety):** Apenas um processo pode estar na região crítica por vez.
2.  **Vivacidade (Liveness):** Um processo que deseja entrar na região crítica eventualmente conseguirá (ausência de deadlock e starvation).
3.  **Ordenação:** As requisições de acesso são atendidas em uma ordem justa (ex: FIFO).

**Abordagens Principais:**

* **Centralizada:**
    * Um processo coordenador é eleito para gerenciar o acesso ao recurso.
    * Um processo envia uma mensagem de `REQUEST` ao coordenador.
    * Se o recurso estiver livre, o coordenador responde com `GRANT`.
    * Ao sair da região crítica, o processo envia um `RELEASE`.
    * **Vantagens:** Simples de implementar.
    * **Desvantagens:** O coordenador é um ponto único de falha e um gargalo de desempenho.

* **Distribuída (Baseada em Permissão):**
    * Um processo que deseja entrar na região crítica envia uma mensagem de requisição (com um *timestamp* lógico) para todos os outros processos.
    * Um processo receptor responde com `OK` se ele não estiver na região crítica e não quiser entrar, ou se a requisição do remetente tiver um *timestamp* menor.
    * O processo entra na região crítica apenas após receber `OK` de todos os outros processos.
    * **Exemplo:** Algoritmo de Ricart e Agrawala.
    * **Vantagens:** Totalmente descentralizado, sem ponto único de falha.
    * **Desvantagens:** Alto custo de comunicação ($2(n-1)$ mensagens por entrada na região crítica).

* **Baseada em Token (Token-Based):**
    * Um único "token" circula entre os processos. A posse do token concede o direito de entrar na região crítica.
    * **Exemplo:** Algoritmo de Token Ring, onde os processos são organizados em um anel lógico e o token passa de um para o outro.
    * **Vantagens:** Garante a ausência de *starvation* e tem baixo tráfego de mensagens quando a contenção é baixa.
    * **Desvantagens:** A perda do token é um problema complexo. A latência para entrar na região crítica pode ser alta se o processo precisar esperar o token dar a volta no anel.

### 4. Eleição (Election)

Muitos algoritmos distribuídos (como o de exclusão mútua centralizada) requerem um processo coordenador. Mas o que acontece se esse coordenador falhar? Um algoritmo de eleição é usado para escolher um novo coordenador de forma dinâmica entre os processos restantes.

**Objetivo:** Garantir que todos os processos não-falhos concordem sobre quem é o novo líder.

**Quando uma eleição é iniciada?**
* Quando um processo detecta que o coordenador atual não está respondendo.
* Na inicialização do sistema.

**Algoritmos Clássicos:**

* **Algoritmo do Valentão (Bully Algorithm):**
    * Assume que cada processo tem um ID único e ordenado. O processo com o maior ID é o líder.
    1.  Quando um processo P detecta a falha do líder, ele envia uma mensagem de `ELECTION` para todos os processos com ID maior que o seu.
    2.  Se nenhum processo com ID maior responder, P se declara o novo líder e envia uma mensagem `COORDINATOR` para todos.
    3.  Se um processo com ID maior Q recebe a mensagem de `ELECTION`, ele responde com `OK` para P (essencialmente dizendo "fique quieto, eu assumo daqui") e inicia sua própria eleição.
    * **Funcionamento:** Os processos "valentões" (com IDs maiores) silenciam os menores e continuam a eleição até que o maior ID ativo se declare líder.

* **Algoritmo em Anel (Ring Algorithm):**
    * Os processos são organizados em um anel lógico, onde cada processo conhece seu sucessor.
    1.  Um processo que detecta a falha do líder cria uma mensagem de `ELECTION` contendo seu próprio ID e a envia ao seu sucessor.
    2.  Cada processo que recebe a mensagem adiciona seu próprio ID à lista na mensagem e a repassa.
    3.  Quando a mensagem retorna ao processo que a iniciou, ela contém os IDs de todos os processos ativos. O iniciador então elege o processo com o maior ID da lista e envia uma nova mensagem `COORDINATOR` com o resultado.
    * **Funcionamento:** A mensagem circula pelo anel para coletar informações e depois outra mensagem circula para anunciar o resultado.
