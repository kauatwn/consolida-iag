---
trigger: always_on
description: "Core business domain rules, entities, and validation constraints for Consolida IAG."
---

# Consolida IAG — Business Domain Rules

## Purpose & Authority

This rule defines the authoritative business domain, domain entities, business rules (RN), and integrity constraints of the **Consolida IAG** system.

When designing, implementing, refactoring, or testing the system, the Agent **MUST** strictly preserve these rules. Do not introduce any behavior or data model changes that contradict these rules without explicit user authorization.

---

## 1. Domain Overview & Entities

The system manages the weekly purchase intent for **Instituto Anchieta Grajaú (IAG)**, centralizing purchase requests across three operational services to prevent duplicate orders while preserving fiscal accountability and auditability.

### 1.1 Service (Cost Center / Fiscal Unit)

Represents an administrative and fiscal cost center unit. The only valid services are:

* **CEI** (*Centro de Educação Infantil* / Early Childhood Education Center)
* **CCA** (*Centro para Crianças e Adolescentes* / Child and Adolescent Center)
* **CJ** (*Centro de Juventude* / Youth Center)

> [!IMPORTANT]
> **Critical Domain Modeling Decision: 3 Fiscal Services vs. 2 Physical Kitchens**
> The physical institution contains only 2 physical kitchen facilities; however, municipal and governmental accountability strictly mandates 3 distinct invoices corresponding to the 3 separate Cost Centers. Therefore, the system **MUST NEVER** model physical entities like "Kitchen". The domain model, relational tables, and API routes must be strictly structured around the **3 Services** (`CEI`, `CCA`, and `CJ`).

### 1.2 Product (Catalog Item & Unit of Measure)

Represents an active institutional supply item registered in the catalog.

* **Essential Attributes:** `id`, `name`, `unit_measure` (e.g., `"KG"`, `"L"`, `"UN"`, `"CX"`, `"PCT"`, `"G"`, `"ML"`), and `active`.
* **Standard Unit of Measure:** Each product has a predefined, immutable unit of measure in the catalog. Quantity inputs operate on this unit, allowing homogeneous mathematical summation during consolidation.

### 1.3 Weekly Purchase Cycle (`WeeklyCycle`)

Represents the weekly purchasing timeframe during which requests can be submitted, consolidated, and finalized.

* **Cycle States:**
  * `OPEN`: Active purchasing window during which Services may submit their weekly requests.
  * `CLOSED`: Finalized purchasing window closed by the procurement manager. Once closed, it becomes **strictly immutable** to preserve quotation and fiscal audit integrity.
* Only one weekly cycle may hold `status = 'OPEN'` simultaneously.

### 1.4 Weekly Request (`WeeklyRequest`)

Represents a weekly purchase intent submitted by a specific `Service` within an active `WeeklyCycle`. It contains requested line items (`RequestItem`) with positive quantities and optional observations.

---

## 2. Business Rules

### RN-01: Service Binding & Fiscal Isolation

* **RN-01.1:** Every purchase request for supplies must belong to exactly one Service (`CEI`, `CCA`, or `CJ`).
* **RN-01.2:** The system must not permanently merge purchase records at the database level; the individual origin of each requested item by Service must be preserved for audit and accounting reconciliation.

### RN-02: Weekly Request Supply Cycle

* **RN-02.1:** Only active products from the institutional catalog may be selected to compose a purchase request.
* **RN-02.2:** A purchase request may only be submitted when at least one catalog product has a quantity strictly greater than zero (`quantity > 0`).
* **RN-02.3:** A purchase request must allow an optional free-text observation field per Service to record specific non-cataloged needs.
* **RN-02.4:** Exactly one active purchase request per Service is permitted for each weekly purchase cycle (`CONSTRAINT uq_service_cycle UNIQUE (service_id, cycle_id)`).
* **RN-02.5:** The system must reject any submission, modification, or deletion of requests linked to a cycle with `status = 'CLOSED'`. Once closed, a cycle is strictly irreversible (reopening is forbidden).
* **RN-02.6:** While a weekly cycle holds `status = 'OPEN'`, a Service may update its existing request, replacing requested line items and observations with transactional atomicity.
* **RN-02.7:** Weekly cycle closure does not require 100% Service participation. If a Service submits no request for the week, the procurement manager may still close the cycle; non-participating services are computed as zero quantity (`0.0`) and do not block consolidation.
* **RN-02.8:** The system must guarantee that at most one weekly cycle holds `status = 'OPEN'` simultaneously. Creating a new cycle while another is open must be rejected.
* **RN-02.9:** Deactivating a catalog product only blocks its selection in new or active requests; historical line items and reports in past `CLOSED` cycles remain immutable and accessible.
* **RN-02.10:** Product names in the catalog must be unique to prevent ambiguous line items.

### RN-03: Purchase Consolidation & Aggregation

* **RN-03.1:** The consolidated purchase quantity for wholesale supplier negotiation must be the exact mathematical sum of quantities requested for that product across all participating Services in the active cycle (`SUM(quantity) GROUP BY product_id`).
* **RN-03.2:** Consolidation must never modify, overwrite, or delete original quantities submitted by individual Services. The consolidated view is strictly a derived projection.

### RN-04: Reporting, Invoice Reconciliation & Audit History

* **RN-04.1:** The system must provide a **Consolidated View** containing total aggregated quantities per product for supplier wholesale price negotiation.
* **RN-04.2:** The system must provide an **Individual Service View** containing only the items and quantities requested by that specific cost center to reconcile against physical invoice delivery and fiscal submission.
* **RN-04.3:** The system must support querying and retrieving past **Closed Cycles History** for accounting audit and retroactive verification.

---

## 3. Domain Integrity Guardrails

When implementing any feature, API, schema, or test:

1. **Non-Destructive Aggregation:** Aggregations and consolidated views must always be computed or projected from raw requests. Never mutate source records during consolidation.
2. **Fiscal Traceability:** Every line item in reports and databases must remain traceable to its originating `Service` (`CEI`, `CCA`, or `CJ`).
3. **No Silent Overrides:** Existing business rules must not be bypassed or silently altered.
4. **Pre-Implementation Conflict Check:** If a requested feature or technical solution conflicts with any RN rule, the Agent **MUST** highlight the conflict before writing code.
