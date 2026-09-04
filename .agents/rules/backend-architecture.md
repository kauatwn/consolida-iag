---
trigger: always_on
description: "Relational database schema, integrity constraints, and REST API contract for Consolida IAG Back-end."
---

# Consolida IAG — Back-end Architecture & API Contracts

## Purpose

This rule defines the official technical architecture, relational database schema, and REST API contracts for the **Consolida IAG Back-end**.

Any data model, migration, endpoint implementation, or backend unit/integration test created by the Agent **MUST** follow these contracts and integrity constraints.

---

## 1. Relational Database Schema (SQL Modeling)

The persistence layer guarantees data integrity, domain isolation per service (RN-01), and non-destructive consolidation (RN-03).

### 1.1 Diagrama Entidade-Relacionamento

```text
  ┌────────────────┐         ┌─────────────────────┐
  │    SERVICOS    │         │   CICLOS_SEMANAIS   │
  ├────────────────┤         ├─────────────────────┤
  │ id (PK)        │         │ id (PK)             │
  │ codigo (UNIQUE)│         │ data_inicio         │
  │ nome           │         │ data_fim            │
  └───────┬────────┘         │ status (ABERTO/FECH)│
          │                  └──────────┬──────────┘
          │ 1                           │ 1
          │        ┌──────────────┐     │
          └───────<│ SOLICITACOES │>────┘
                 N ├──────────────┤ N
                   │ id (PK)      │
                   │ servico_id   │
                   │ ciclo_id     │
                   │ observacao   │
                   └──────┬───────┘
                          │ 1
                          │
                   ┌──────┴────────────┐         ┌──────────────────┐
                   │ ITENS_SOLICITACAO │>───────┤     PRODUTOS     │
                   ├───────────────────┤ N     1 ├──────────────────┤
                   │ id (PK)           │         │ id (PK)          │
                   │ solicitacao_id    │         │ nome             │
                   │ produto_id        │         │ unidade_medida   │
                   │ quantidade (> 0)  │         │ ativo (BOOLEAN)  │
                   └───────────────────┘         └──────────────────┘
```

### 1.2 Definição das Tabelas e Constraints

#### Tabela `servicos`

* `id`: Chave primária (UUID ou BIGINT AUTO_INCREMENT).
* `codigo`: VARCHAR(10) NOT NULL UNIQUE (Valores válidos: `'CEI'`, `'CCA'`, `'CJ'`).
* `nome`: VARCHAR(100) NOT NULL.
* `criado_em`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP.

#### Tabela `produtos`

* `id`: Chave primária.
* `nome`: VARCHAR(150) NOT NULL.
* `unidade_medida`: VARCHAR(20) NOT NULL (ex: `'kg'`, `'litro'`, `'unidade'`, `'pacote'`).
* `ativo`: BOOLEAN DEFAULT TRUE NOT NULL.
* `criado_em`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP.

#### Tabela `ciclos_semanais`

* `id`: Chave primária.
* `data_inicio`: DATE NOT NULL.
* `data_fim`: DATE NOT NULL.
* `status`: VARCHAR(20) NOT NULL DEFAULT `'ABERTO'` (CHECK: `status IN ('ABERTO', 'FECHADO')`).
* `criado_em`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP.
* `fechado_em`: TIMESTAMP NULL.

#### Tabela `solicitacoes`

* `id`: Chave primária.
* `servico_id`: FK referenciando `servicos(id)` NOT NULL.
* `ciclo_id`: FK referenciando `ciclos_semanais(id)` NOT NULL.
* `observacao`: TEXT NULL (para necessidades pontuais / não catalogadas - RN-02.3).
* `criado_em`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP.
* `atualizado_em`: TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP.
* **CONSTRAINT UNIQUE:** `CONSTRAINT uq_servico_ciclo UNIQUE (servico_id, ciclo_id)` — *Garante no banco a RN-02.4 (apenas 1 solicitação por serviço por ciclo).*

#### Tabela `itens_solicitacao`

* `id`: Chave primária.
* `solicitacao_id`: FK referenciando `solicitacoes(id)` ON DELETE CASCADE NOT NULL.
* `produto_id`: FK referenciando `produtos(id)` NOT NULL.
* `quantidade`: DECIMAL(10, 2) NOT NULL (CHECK: `quantidade > 0`).
* **CONSTRAINT UNIQUE:** `CONSTRAINT uq_solicitacao_produto UNIQUE (solicitacao_id, produto_id)`.

---

## 2. REST API Contract (Endpoints & Payloads)

Prefixo base: `/api/v1`

### 2.1 Catálogo de Produtos

* **`GET /api/v1/produtos`**
  * Retorna lista de produtos ativos com suas respectivas unidades de medida.
  * Resposta (200 OK):

    ```json
    [
      { "id": 1, "nome": "Arroz Agulhinha Tipo 1", "unidade_medida": "kg", "ativo": true },
      { "id": 2, "nome": "Feijão Carioca", "unidade_medida": "kg", "ativo": true },
      { "id": 3, "nome": "Óleo de Soja", "unidade_medida": "litro", "ativo": true }
    ]
    ```

### 2.2 Ciclos Semanais

* **`GET /api/v1/ciclos/ativo`**
  * Retorna o ciclo semanal com status `ABERTO`.
  * Resposta (200 OK):

    ```json
    {
      "id": 12,
      "data_inicio": "2026-09-01",
      "data_fim": "2026-09-07",
      "status": "ABERTO"
    }
    ```

  * Se não houver ciclo aberto: `404 Not Found` `{ "error": "Nenhum ciclo semanal aberto no momento." }`.

* **`GET /api/v1/ciclos`**
  * Suporta query param `?status=FECHADO` para consulta histórica (RN-04.3).
  * Retorna lista de ciclos ordenados por data decrescente.

* **`POST /api/v1/ciclos/{id}/fechar`**
  * Encerra o ciclo ativo e define `status = 'FECHADO'`.
  * Trava todas as solicitações existentes contra qualquer modificação futura (RN-02.5).

### 2.3 Submissão de Solicitação pelo Serviço

* **`POST /api/v1/solicitacoes`**
  * Submete o pedido semanal do serviço (CEI, CCA ou CJ).
  * Payload de Requisição:

    ```json
    {
      "servico_id": 1,
      "ciclo_id": 12,
      "observacao": "Necessidade de 2 caixas de fósforo extra.",
      "itens": [
        { "produto_id": 1, "quantidade": 20.0 },
        { "produto_id": 2, "quantidade": 15.0 }
      ]
    }
    ```

  * **Validações Obrigatórias do Back-end:**
    1. O ciclo informado deve existir e estar com `status = 'ABERTO'` (RN-02.5).
    2. O serviço não pode ter outra solicitação registrada para este ciclo (RN-02.4).
    3. Pelo menos 1 item deve possuir quantidade maior que zero (RN-02.2).
    4. Todos os `produto_id` devem corresponder a produtos com `ativo = TRUE` (RN-02.1).

### 2.4 Relatórios e Projeções (Comprador/Gestão)

* **`GET /api/v1/ciclos/{id}/consolidado` (Visão Consolidada - Atacado)**
  * Calcula em tempo real ou sob demanda a soma exata dos itens (RN-03.1).
  * Resposta (200 OK):

    ```json
    {
      "ciclo_id": 12,
      "status": "ABERTO",
      "itens": [
        {
          "produto_id": 1,
          "nome": "Arroz Agulhinha Tipo 1",
          "unidade_medida": "kg",
          "quantidade_total": 60.0,
          "detalhe_por_servico": [
            { "servico_codigo": "CEI", "quantidade": 20.0 },
            { "servico_codigo": "CCA", "quantidade": 15.0 },
            { "servico_codigo": "CJ", "quantidade": 25.0 }
          ]
        }
      ]
    }
    ```

* **`GET /api/v1/ciclos/{id}/servicos/{servico_id}` (Visão por Serviço - Prestação de Contas)**
  * Retorna os itens e observações específicas daquele Centro de Custo para bater com a NF (RN-04.2).
  * Resposta (200 OK):

    ```json
    {
      "ciclo_id": 12,
      "servico": { "id": 1, "codigo": "CEI", "nome": "Centro de Educação Infantil" },
      "observacao": "Necessidade de 2 caixas de fósforo extra.",
      "itens": [
        { "produto_id": 1, "nome": "Arroz Agulhinha Tipo 1", "unidade_medida": "kg", "quantidade": 20.0 },
        { "produto_id": 2, "nome": "Feijão Carioca", "unidade_medida": "kg", "quantidade": 15.0 }
      ]
    }
    ```

---

## 3. Diretrizes de Implementação Técnica

1. **Atomicidade Transacional:** A criação de uma solicitação com seus múltiplos itens deve ser envolvida em uma única transação de banco de dados (`BEGIN ... COMMIT`).
2. **Consultas de Consolidação Eficientes:** Utilizar agregação SQL (`SUM(quantidade) ... GROUP BY produto_id`) para performance sem duplicar armazenamento.
3. **Respostas de Erro Padronizadas:** Respostas com erros de validação devem retornar `400 Bad Request` ou `422 Unprocessable Entity` com formato `{ "error": "DESCRICAO", "details": [...] }`.
