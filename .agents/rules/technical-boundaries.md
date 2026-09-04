---
trigger: always_on
description: "Explicit out-of-scope boundaries, system limits, and architectural invariants for Consolida IAG."
---

# Consolida IAG — Scope Boundaries & Technical Invariants

## Purpose

This rule defines the strict system boundaries, out-of-scope functional areas, and technical invariants for **Consolida IAG**.

These boundaries prevent over-engineering, unnecessary external integrations, and architectural drift. The Agent **MUST NOT** implement features or dependencies related to items marked as out-of-scope.

---

## 1. Out of Scope Boundaries (Fronteiras e Fora do Escopo)

The following capabilities are explicitly outside the scope of the system:

| Área Funcional | Diretriz de Negócio & Restrição Técnica |
| :--- | :--- |
| **Controle de Estoque Ativo** *(Active Inventory Management)* | O sistema **NÃO** deve implementar controle de saldo em almoxarifado, baixa diária de consumo, inventário físico, registro de perdas ou entrada física de mercadorias. |
| **Integração Fiscal** *(Fiscal Integration)* | O sistema **NÃO** deve realizar emissão, recepção, validação de arquivos XML ou comunicação de Notas Fiscais Eletrônicas (NF-e/NFS-e) junto à SEFAZ ou outros órgãos governamentais. |
| **Cotação Financeira e Pagamentos** *(Financial Quotation & Payments)* | O sistema **NÃO** deve integrar APIs de fornecedores para cotação automatizada de preços, gateways de pagamento, controle de contas a pagar/receber ou processamento de transações financeiras. |

---

## 2. Technical Invariants & Data Integrity

### 2.1 Database & Persistence Level
* **Individual Origin Preservation:** Never merge or normalize records across services into a single destructive record. Every requested quantity line item must retain its foreign key / association to the originating `Serviço`.
* **Immutability of Historical Cycles:** Past purchase cycles must be immutable once closed/finalized to ensure clean audit trails.

### 2.2 Reporting & Views
* **Consolidated View:** Read-only / derived aggregation across all services for supplier negotiation.
* **Individual View:** Filtered by single `Serviço` (CEI, CCA, or CJ) for reconciliation against physical invoice delivery.

---

## 3. Enforcement Instructions for Agent

1. **Reject Scope Creep:** If asked to add inventory stock deductions, fiscal communication protocols, or payment gateways, alert the user that these are explicitly designated as out-of-scope per this rule.
2. **Preserve Separation:** Keep data models and business logic technology-agnostic at the domain layer, ensuring business rules RN-01 through RN-04 remain invariant regardless of UI or storage choice.
