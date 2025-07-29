# Sistemas Paralelos e Distribuídos

Este README reúne conceitos sobre **Sistemas Paralelos**, **Sistemas Distribuídos** e **Threads**.

---

## Sistemas Paralelos

- **Homogêneos**  
  Arquiteturas de hardware, sistema operacional e linguagens de programação idênticas.

- **Fortemente acoplados**  
  Dispositivos fixos em um mesmo local, comunicando-se via protocolos TCP/IP:  
  - Endereço de rede  
  - Porta lógica  
  - Máscara de rede  
  - Protocolos de transporte

- **Cluster computacional**

- **Arquiteturas Ponto-a-Ponto**  
  - Tolerância a falhas  
  - Escalabilidade  
  - Segurança  
  - Manutenção/atualização

- **Objetivo**  
  Compartilhar recursos: processador, memória e placa gráfica.

---

## Sistemas Distribuídos

- **Heterogêneos**  
  Diferentes arquiteturas de hardware, sistemas operacionais e linguagens de programação.

- **Fracamente acoplados**  
  Distribuídos geograficamente, comunicando-se via protocolos TCP/IP:  
  - Endereço de rede  
  - Porta lógica  
  - Máscara de rede  
  - Protocolos de transporte

- **Grid computacional**

- **Arquiteturas**  
  - Cliente–Servidor  
  - Ponto-a-Ponto (P2P)  
  - Híbrido  
  - Características comuns:  
    - Tolerância a falhas  
    - Escalabilidade  
    - Segurança  
    - Manutenção/atualização

- **Objetivo**  
  Compartilhar recursos (processador e memória).  
  - Para isso, é necessário controlar **sincronismo**:  
    - Relógio (lógico e físico)  
    - Exclusão mútua de recursos

- **Dependência do Sistema Operacional**  
  - Gestor de processamento  
  - Gestor de comunicação ou gestor de camadas de serviço  

> **Observação:**  
> Sistemas distribuídos, na essência, utilizam comunicação via **sockets** (IP, porta, máscara, objetos de escrita/leitura), que é **bloqueante**.  
> Em tempo de programação, a solução é o uso de **threads** para liberar a comunicação bloqueante.

### Características Básicas

- **Arquitetura**  
  - Cliente–Servidor  
  - Ponto-a-Ponto (P2P)  
  - Híbrido

- **Comunicação bloqueante**  
  - Operações de escrita  
  - Operações de leitura

- **Programação multitarefa (threads)**  
  - Thread é um “mini-processo” dentro de um processo  
  - Pode compartilhar memória:  
    - Mecanismos de sincronismo: monitores, semáforos  
  - Pode não compartilhar memória  
  - Importância: permitir a execução concorrente de processos e liberar operações bloqueantes em SD

---

## Thread

- **Definição**  
  Subprocesso ou miniprocesso pertencente a um processo, criado em tempo de programação/execução.  
  Cada thread possui identificador, nome, endereço de memória, tamanho, tempo de CPU e instruções.

- **Finalidade**  
  Garantir processamento concomitante/paralelo.

- **Estados de uma thread**  
  - Execução  
  - Pronto  
  - Espera/Aguardando  
  - Parado  
  - Dormindo  
  - Cancelado  
  - … e outros

- **Sincronismo**  
  Comandos e mecanismos que garantem a ordem e exclusão mútua no acesso a recursos compartilhados.

- **Tipos de Threads**  
  - **Com compartilhamento de memória/recurso**  
    - Processamento bloqueante  
    - Responsabilidade do programador garantir o sincronismo  
  - **Sem compartilhamento de memória/recurso**

- **Thread em Java**  
  - Processamento concomitante na JVM  
  - **Com compartilhamento de memória:** implementa a interface `Runnable`  
  - **Sem compartilhamento de memória:** estende a classe `Thread`
