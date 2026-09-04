# Consolida IAG — Back-end API: Guidelines & Specifications

Bem-vindo ao repositório **Back-end** do **Consolida IAG**. Este documento estabelece as diretrizes centrais de arquitetura de software, modelagem de banco de dados e contratos de API para o serviço centralizador de compras da instituição.

---

## 📌 Visão Geral do Repositório (Back-end)

O **Consolida IAG (Back-end)** é responsável por prover a API REST e a camada de persistência para gerenciar o fluxo semanal de compras do **Instituto Anchieta Grajaú (IAG)**, atendendo aos seus 3 serviços operacionais:

* **CEI** (*Centro de Educação Infantil*)
* **CCA** (*Centro para Crianças e Adolescentes*)
* **CJ** (*Centro de Juventude*)

Sua missão central é fornecer endpoints para registro das solicitações por serviço, cálculo exato da soma consolidada para atacado e extração individualizada para conferência fiscal, sem jamais mesclar dados de forma destrutiva.

---

## 📜 Regras do Agente (Workspace Rules)

As especificações e restrições detalhadas do back-end estão organizadas no diretório `.agents/rules/`:

1. **Domínio e Regras de Negócio:** @.agents/rules/business-domain.md
   * Entidades de domínio (`Serviço`, `Produto`, `CicloSemanal`, `Solicitação Semanal`).
   * Unidade de Medida padrão por produto (`kg`, `litro`, `unidade`, etc.).
   * Ciclo semanal com estados `ABERTO` e `FECHADO` (bloqueio de alterações após fechamento).
   * Regras de negócio centrais:
     * **RN-01:** Vinculação e Isolamento Fiscal por Serviço (RN-01.1, RN-01.2).
     * **RN-02:** Ciclo de Solicitação de Insumos (RN-02.1 a RN-02.5).
     * **RN-03:** Consolidação e Agrupamento de Compras (RN-03.1, RN-03.2).
     * **RN-04:** Relatórios, Conferência e Histórico de Ciclos Fechados (RN-04.1 a RN-04.3).

2. **Fronteiras e Fora de Escopo:** @.agents/rules/technical-boundaries.md
   * Restrições explícitas para proteção do prazo de 4 meses do MVP:
     * ❌ **Sem Controle de Estoque Ativo** (sem saldo de almoxarifado, consumo diário ou entrada física).
     * ❌ **Sem Integração Fiscal com SEFAZ/Governo** (sem emissão ou validação de NF-e).
     * ❌ **Sem Cotação Financeira Automatizada / Pagamentos** (sem APIs de pagamento ou precificação bancária).
     * ❌ **Sem Código de Interface Frontend** (CSS/React pertencem exclusivamente ao repositório de front-end).

3. **Arquitetura de API e Banco de Dados:** @.agents/rules/backend-architecture.md
   * Diagrama relacional e schema SQL (`servicos`, `produtos`, `ciclos_semanais`, `solicitacoes`, `itens_solicitacao`).
   * Constraints de integridade: `UNIQUE (servico_id, ciclo_id)` e `CHECK (quantidade > 0)`.
   * Contrato completo de endpoints REST (`/api/v1/...`) com payloads JSON de requisição e resposta.

---

## 🛡️ Invariantes de Desenvolvimento e Decisões Críticas

Ao propor ou implementar código no Back-end:

* **Decisão 3 Serviços vs. 2 Cozinhas Físicas:** O modelo relacional estrutura-se estritamente sobre os **3 Centros de Custo** (`CEI`, `CCA`, `CJ`). Não crie tabelas ou campos referenciando cozinhas físicas.
* **Não-destrutividade:** A consolidação de pedidos é uma agregação computada (`SUM(quantidade) GROUP BY produto_id`); os registros submetidos por cada Serviço jamais são sobrescritos ou deletados.
* **Atomicidade:** A gravação da solicitação e seus respectivos itens de insumo deve ocorrer sob transação de banco de dados (`BEGIN ... COMMIT`).
* **Imutabilidade de Ciclos Fechados:** Se `ciclo.status == 'FECHADO'`, qualquer tentativa de `POST`, `PUT` ou `DELETE` deve retornar `400 Bad Request` ou `422 Unprocessable Entity`.

---

## 🚀 Roteiro Prático de Próximos Passos (Back-end)

1. **Definição da Stack Back-end:** Escolha da tecnologia (ex: Node.js/TypeScript, Python/FastAPI ou Go) e ORM/Query Builder.
2. **Migrations e Modelagem:** Criação dos scripts de banco de dados refletindo o schema de @.agents/rules/backend-architecture.md.
3. **Seed Inicial:** Carga dos 3 Serviços (`CEI`, `CCA`, `CJ`) e dos produtos mais comuns da instituição com suas unidades de medida.
4. **Implementação dos Endpoints:** Construção das rotas de ciclo, submissão de pedidos e relatórios (consolidado e individual).
5. **Testes Automatizados:** Testes unitários e de integração validando as regras RN-01 a RN-04.
