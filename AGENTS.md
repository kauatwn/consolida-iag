# Consolida IAG — Back-end API: Guidelines & Specifications

Welcome to the **Back-end** repository of **Consolida IAG**. This document establishes the core software architecture guidelines, database modeling, and API contracts for the institution's centralized purchasing service.

---

## 📌 Repository Overview (Back-end)

**Consolida IAG (Back-end)** is responsible for providing the REST API and persistence layer to manage the weekly purchasing workflow for **Instituto Anchieta Grajaú (IAG)** across its 3 operational services:

* **CEI** (*Centro de Educação Infantil* / Early Childhood Education Center)
* **CCA** (*Centro para Crianças e Adolescentes* / Child and Adolescent Center)
* **CJ** (*Centro de Juventude* / Youth Center)

Its primary mission is to provide endpoints for registering weekly supply requests by service, computing the exact consolidated wholesale quantities, and generating individualized reports for physical delivery reconciliation and fiscal auditing, without ever merging records destructively.

---

## 📜 Agent Rules (Workspace Rules)

Detailed back-end specifications, integrity constraints, and domain rules are organized within `.agents/rules/`:

1. **Domain & Business Rules:** [business-domain.md](file:///Users/kaual/VSCodeProjects/pi/consolida-iag/.agents/rules/business-domain.md)
   * Domain entities (`Service`, `Product`, `WeeklyCycle`, `WeeklyRequest`, `RequestItem`).
   * Standard Unit of Measure per product (`KG`, `L`, `UN`, `CX`, `PCT`, `G`, `ML`).
   * Weekly cycle lifecycle states: `OPEN` and `CLOSED` (immutable locking once closed).
   * Core business rules:
     * **RN-01:** Service Binding & Fiscal Isolation (RN-01.1, RN-01.2).
     * **RN-02:** Weekly Request Supply Cycle (RN-02.1 to RN-02.10).
     * **RN-03:** Purchase Consolidation & Aggregation (RN-03.1, RN-03.2).
     * **RN-04:** Reporting, Invoice Reconciliation & Audit History (RN-04.1 to RN-04.3).

2. **Scope Boundaries & Invariants:** [technical-boundaries.md](file:///Users/kaual/VSCodeProjects/pi/consolida-iag/.agents/rules/technical-boundaries.md)
   * Explicit restrictions protecting the 4-month MVP deadline:
     * ❌ **No Active Inventory Management** (no warehouse stock balance, daily consumption deductions, or physical receiving).
     * ❌ **No Fiscal / Tax Integration** (no XML parsing, electronic invoice generation, or SEFAZ governmental communication).
     * ❌ **No Financial Quotations or Payments** (no supplier quotation APIs, banking integrations, or payment gateways).
     * ❌ **No Reopening of Closed Cycles** (closed cycles are permanent and immutable to safeguard audit trails).
     * ❌ **No Multi-Level Approval Workflows** (direct submission from cost centers without intermediate approval tiers).
     * ❌ **No Budget or Spending Ceilings** (no financial quotas or balance checking).
     * ❌ **No Frontend UI Code** (CSS, HTML templates, and React components belong exclusively to the front-end repository).

3. **Architecture & Database Schema:** [backend-architecture.md](file:///Users/kaual/VSCodeProjects/pi/consolida-iag/.agents/rules/backend-architecture.md)
   * Formal Mermaid Entity-Relationship Diagram (ERD) and relational SQL schema (`services`, `products`, `weekly_cycles`, `weekly_requests`, `request_items`).
   * Integrity constraints: `UNIQUE (service_id, cycle_id)` and `CHECK (quantity > 0)`.
   * Complete REST API contracts (`/api/v1/...`) with JSON request and response payloads.

---

## 🛡️ Critical Invariants & Architectural Decisions

When designing, refactoring, or implementing back-end code:

* **3 Services vs. 2 Physical Kitchens Decision:** The relational model and domain architecture are strictly modeled around the **3 Cost Centers** (`CEI`, `CCA`, `CJ`). Never introduce entities, tables, or fields referencing physical kitchen buildings.
* **Non-Destructive Aggregation:** Purchase consolidation is a derived aggregation (`SUM(quantity) GROUP BY product_id`). Raw requests submitted by each Service are never overwritten or deleted.
* **Transactional Atomicity:** Persisting a weekly request and its respective line items must execute within an atomic database transaction (`@Transactional`).
* **Immutability of Closed Cycles:** Once a cycle reaches `status = 'CLOSED'`, any attempt to reopen it, or to create, modify, or delete associated requests must be rejected with `400 Bad Request` or `422 Unprocessable Entity`.

---

## 🚀 Practical Back-end Roadmap

1. **Stack & Project Configuration:** Spring Boot 4.x / Java 25, Spring Data JPA, Bean Validation, PostgreSQL.
2. **Database Migrations & Modeling:** Relational tables and constraints matching the en-US schema in [backend-architecture.md](file:///Users/kaual/VSCodeProjects/pi/consolida-iag/.agents/rules/backend-architecture.md).
3. **Initial Seed:** Seed the 3 Services (`CEI`, `CCA`, `CJ`) and institutional catalog products with standard units of measure.
4. **Endpoint Implementation:** Endpoints for product catalog, cycle lifecycle, request submission, and consolidated/individual reports.
5. **Automated Testing & Governance:** Unit, integration, and ArchUnit architecture governance tests enforcing DDD purity, Hexagonal Architecture, and Vertical Slice boundaries.
