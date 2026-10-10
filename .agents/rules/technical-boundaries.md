---
trigger: always_on
description: "Explicit out-of-scope boundaries, system limits, and architectural invariants for Consolida IAG."
---

# Consolida IAG — Scope Boundaries & Technical Invariants

## Purpose

This rule defines the strict system boundaries, out-of-scope functional areas, and technical invariants for **Consolida IAG**.

These boundaries prevent over-engineering, unnecessary external integrations, and architectural drift. The Agent **MUST NOT** implement features or dependencies related to items marked as out-of-scope.

---

## 1. Out of Scope Boundaries

The following capabilities are explicitly outside the scope of the system:

| Functional Area                              | Business Directive & Technical Restriction                                                                                                                                                                                                          |
|:---------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Active Inventory Management**              | The system **MUST NOT** implement warehouse stock tracking, daily consumption deductions, physical counts, loss tracking, or physical receiving dock workflows.                                                                                     |
| **Fiscal / Tax Authority Integration**       | The system **MUST NOT** issue, receive, parse electronic invoice XMLs, or communicate directly with governmental tax authorities (e.g., SEFAZ NF-e/NFS-e).                                                                                          |
| **Financial Quotations & Payments**          | The system **MUST NOT** integrate external supplier price-scraping APIs, payment gateways, accounts payable/receivable, or banking transactions.                                                                                                    |
| **Reopening Closed Cycles**                  | The system **MUST NOT** provide mechanisms to reopen, un-finalize, or edit `CLOSED` cycles. Closed cycles are strictly permanent and immutable to safeguard quotation integrity and fiscal audits.                                                  |
| **Multi-Level Approval Workflows**           | The system **MUST NOT** implement multi-tier hierarchical approvals (e.g., Nutritionist -> Kitchen Leader -> Executive Director, intermediate `DRAFT` or `PENDING_APPROVAL` states). Requests go straight from service submission to consolidation. |
| **Budget & Spending Ceilings**               | The system **MUST NOT** enforce financial limits, balance checks, or budget ceilings per service.                                                                                                                                                   |
| **Unit-Specific Quantity Type Validation**   | The system **MUST NOT** restrict packaging units (`CX`, `UN`, `PCT`) to integer-only inputs; `DECIMAL(10, 2)` is universally supported across all catalog units to minimize validation overhead during the MVP.                                     |
| **Native Mobile Applications (iOS/Android)** | The system **MUST NOT** be developed as a native mobile application. The architecture is strictly a Web API (Back-end) consumed by a Responsive Web Application (Front-end).                                                                        |

> [!NOTE]
> **MVP Protection (4-month Timeline):** The exclusions above are mandated to ensure successful technical delivery within the academic timeframe and prevent unnecessary operational overhead.

---

## 2. Technical Invariants & Data Integrity (Back-end)

### 2.1 Database & Persistence Level

* **Individual Origin Preservation:** Never merge or normalize records across services into a single destructive record. Every requested quantity line item must retain its foreign key / association to the originating `Service`.
* **Immutability of Closed Cycles:** Once a cycle reaches `status = 'CLOSED'`, all associated requests and item records are permanently read-only. Updates and deletes MUST be blocked with `400 Bad Request` or `422 Unprocessable Entity`.
* **Transactional Integrity:** Creating or updating a weekly request and its items MUST execute within an atomic database transaction (`@Transactional`).

### 2.2 Reporting & Projections

* **Consolidated View:** Read-only derived aggregation across all participating services for supplier wholesale negotiation. Must be calculated dynamically or projected from raw requests (`SUM(quantity) GROUP BY product_id`).
* **Individual Service View:** Filtered by single `Service` (`CEI`, `CCA`, or `CJ`) for reconciliation against physical invoice delivery.

---

## 3. Enforcement Instructions for Agent

1. **Reject Scope Creep:** If asked to add inventory stock deductions, fiscal communication protocols, or payment gateways, alert the user that these are explicitly designated as out-of-scope per this rule.
2. **Preserve Separation:** Keep data models and business logic technology-agnostic at the domain layer, ensuring business rules RN-01 through RN-04 remain invariant.
3. **Repository Scope:** This repository is dedicated to the **Back-end API**. Do not introduce frontend UI styling, React/Vue components, or CSS within this repository.
