# Arquiteturas de Rede: Cliente-Servidor vs Ponto-a-Ponto

## 1. Arquitetura Cliente-Servidor

- **Definição:**
  - Modelo centralizado em que clientes solicitam serviços e servidores os fornecem.
- **Como funciona:**
  1. O **servidor** aguarda conexões em uma porta de rede.
  2. O **cliente** inicia a conexão e envia requisições.
  3. O servidor processa e retorna respostas.
- **Características:**
  - **Escalabilidade:** Vertical (upgrade de servidor) ou horizontal (adicionar mais servidores).  
  - **Segurança:** Centralizada, com controle de acesso e autenticação no servidor.  
  - **Tolerância a falhas:** Pode usar balanceadores de carga e redundância.  
  - **Manutenção:** Atualização central, sem impactar clientes diretamente.

## 2. Arquitetura Ponto-a-Ponto (P2P)

- **Definição:**
  - Modelo descentralizado em que cada nó atua simultaneamente como cliente e servidor.
- **Como funciona:**
  1. Cada **peer** (nó) pode iniciar conexões e atender requisições de outros peers.
  2. Comunicação direta entre peers, sem servidor central.
- **Características:**
  - **Escalabilidade:** Alta, pois novos peers adicionam recursos à rede.  
  - **Segurança:** Desafiadora, pois não há controle central; costuma usar criptografia end-to-end.  
  - **Tolerância a falhas:** Resiliência alta; falha de um peer não derruba a rede.  
  - **Manutenção:** Cada peer é responsável por suas próprias atualizações.

## 3. Comparação Rápida

| Aspecto            | Cliente-Servidor                    | Ponto-a-Ponto (P2P)            |
|--------------------|-------------------------------------|--------------------------------|
| Centralização      | Alta (servidor único/liderança)    | Nenhuma (descentralizado)      |
| Custo de infraestrutura | Qualificado (servidores potentes) | Distribuído (recursos dos peers) |
| Resiliência        | Baixa se servidor falha, mas pode ser alta com redundância | Alta intrinsicamente           |
| Gerenciamento      | Centralizado                       | Distribuído                    |
| Performance        | Dependente do servidor             | Balanceada entre peers         |
