---
trigger: always_on
description: "Core business domain rules, entities, and validation constraints for Consolida IAG."
---

# Consolida IAG — Business Domain Rules (Regras de Domínio)

## Purpose & Authority

This rule defines the authoritative business domain, domain entities, business rules (RN), and integrity constraints of the **Consolida IAG** system.

When designing, implementing, refactoring, or testing the system, the Agent **MUST** strictly preserve these rules. Do not introduce any behavior or data model changes that contradict these rules without explicit user authorization.

---

## 1. Domain Overview & Entities (Entidades de Domínio)

The system manages the weekly purchase intent for **Instituto Anchieta Grajaú (IAG)**, centralizing purchase requests across three operational services to prevent duplication while preserving fiscal accountability and auditability.

### 1.1 Serviço (Centro de Custo / Unidade Fiscal)

Represents an administrative and fiscal cost center unit. The only valid services are:

* **CEI** (*Centro de Educação Infantil*)
* **CCA** (*Centro para Crianças e Adolescentes*)
* **CJ** (*Centro de Juventude*)

> [!IMPORTANT]
> **Decisão de Modelagem Crítica: 3 Serviços Fiscais vs. 2 Cozinhas Físicas**
> A instituição conta fisicamente com apenas 2 espaços de cozinha, porém a prestação de contas governamental exige rigorosamente a separação de 3 Notas Fiscais distintas para os 3 Centros de Custo. Portanto, o sistema **NUNCA** deve modelar entidades como "Cozinha" baseadas na planta física predial. O modelo de dados, rotas e pedidos deve ser estritamente estruturado em torno dos **3 Serviços** (`CEI`, `CCA` e `CJ`).

### 1.2 Produto (Item de Catálogo)

Represents an active input/item registered in the institution's general catalog.

### 1.3 Solicitação Semanal (Ciclo de Pedido de Compras)

Represents a weekly purchase request submitted by a specific `Serviço` for an active purchase cycle.

---

## 2. Business Rules (Regras de Negócio)

### RN-01: Vinculação e Isolamento Fiscal por Serviço

* **RN-01.1:** Toda solicitação de insumos deve obrigatoriamente pertencer a um único Serviço (`CEI`, `CCA` ou `CJ`). *(Every purchase request for supplies MUST belong to exactly one Serviço).*
* **RN-01.2:** O sistema não deve permitir a unificação definitiva dos registros de pedidos no nível de banco de dados; a origem de cada item por Serviço deve ser preservada individualmente para fins de auditoria e prestação de contas. *(The system MUST preserve the individual origin of each requested item by Serviço and MUST NOT permanently merge records at the database level).*

### RN-02: Ciclo de Solicitação de Insumos

* **RN-02.1:** Apenas produtos ativos no catálogo institucional podem ser selecionados para compor uma solicitação. *(Only active products from the institutional catalog MAY be selected for a purchase request).*
* **RN-02.2:** O envio da solicitação exige a definição de quantidade maior que zero para ao menos um produto do catálogo. *(A purchase request MAY only be submitted when at least one catalog product has a quantity greater than zero).*
* **RN-02.3:** A solicitação deve permitir a inclusão de um campo de observação de texto livre por Serviço para registrar necessidades pontuais não catalogadas. *(A purchase request MUST allow a free-text observation field per Serviço to record specific non-cataloged needs).*
* **RN-02.4:** Apenas uma solicitação ativa por Serviço é permitida por ciclo semanal de compra. *(Only one active purchase request per Serviço is allowed for each weekly purchase cycle).*

### RN-03: Consolidação e Agrupamento de Compras

* **RN-03.1:** O cálculo da necessidade de compra para cotação em atacado deve realizar a soma matemática exata das quantidades solicitadas para o mesmo produto entre todos os serviços no ciclo corrente. *(The consolidated purchase quantity for a product MUST be the exact mathematical sum of the quantities requested for that product by all participating Serviços in the current cycle).*
* **RN-03.2:** A consolidação de quantidades para cotação não altera, sobrescreve ou apaga os quantitativos originais submetidos por cada Serviço individualmente. A visão consolidada é uma representação derivada. *(Consolidation MUST NOT modify, overwrite, or delete original quantities submitted by individual Serviços. The consolidated view is strictly a derived representation).*

### RN-04: Relatórios e Conferência de Prestação de Contas

* **RN-04.1:** O sistema deve fornecer a extração da listagem unificada de compras (**Visão Consolidada**) com o somatório total dos produtos para negociação com fornecedores. *(The system MUST provide a consolidated purchase view containing the total quantity of each product for supplier negotiation).*
* **RN-04.2:** O sistema deve fornecer a extração individualizada por Serviço (**Visão por Serviço**), com suporte a exportação em PDF e formato para impressão, exibindo apenas os itens e quantidades solicitados por aquele CNPJ/Centro de Custo, para conferência direta com a respectiva Nota Fiscal física e encaminhamento ao setor financeiro. *(The system MUST provide an individual view per Serviço with PDF export and print support, containing only the products and quantities requested by that Serviço to reconcile against the corresponding physical invoice).*

---

## 3. Domain Integrity Guardrails

When implementing any feature, API, schema, or test:

1. **Non-Destructive Aggregation:** Aggregations and consolidated views must always be computed or projected from raw requests. Never mutate source records during consolidation.
2. **Fiscal Traceability:** Every line item in reports or databases must be traceable to its originating `Serviço` (CEI, CCA, or CJ).
3. **No Silent Overrides:** Existing business rules must not be modified or bypassed.
4. **Pre-Implementation Conflict Check:** If a requested feature or technical solution conflicts with any RN rule, the Agent **MUST** identify and highlight the conflict before writing code.
