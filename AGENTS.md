# Consolida IAG — Project Guidelines & Specifications

Bem-vindo ao repositório **Consolida IAG**. Este documento estabelece as diretrizes centrais de desenvolvimento, arquitetura e regras de negócio para o projeto.

---

## 📌 Visão Geral do Projeto

O **Consolida IAG** gerencia a intenção de compra semanal para o **Instituto Anchieta Grajaú (IAG)**, atendendo a 3 serviços operacionais:
* **CEI** (Centro de Educação Infantil)
* **CCA** (Centro para Crianças e Adolescentes)
* **CJ** (Centro de Juventude)

O objetivo principal é solucionar a duplicação de solicitações de compras, permitindo a negociação consolidada em atacado enquanto preserva rigorosamente a origem individual de cada pedido para fins de auditoria e prestação de contas fiscais.

---

## 📜 Regras do Agente (Workspace Rules)

As especificações e restrições detalhadas do projeto estão organizadas no diretório `.agents/rules/`:

1. **Domínio e Regras de Negócio:** @.agents/rules/business-domain.md
   * Entidades de domínio (`Serviço`, `Produto`, `Solicitação Semanal`).
   * Regras de negócio fundamentais:
     * **RN-01:** Vinculação e Isolamento Fiscal por Serviço (RN-01.1, RN-01.2).
     * **RN-02:** Ciclo de Solicitação de Insumos (RN-02.1, RN-02.2, RN-02.3, RN-02.4).
     * **RN-03:** Consolidação e Agrupamento de Compras (RN-03.1, RN-03.2).
     * **RN-04:** Relatórios e Conferência de Prestação de Contas (RN-04.1, RN-04.2).

2. **Fronteiras e Fora de Escopo:** @.agents/rules/technical-boundaries.md
   * Restrições explícitas contra escopo não planejado:
     * ❌ **Sem Controle de Estoque Ativo** (sem saldo de almoxarifado, consumo diário ou entrada física).
     * ❌ **Sem Integração Fiscal com SEFAZ/Governo** (sem emissão ou validação de NF-e).
     * ❌ **Sem Cotação Financeira Automatizada / Pagamentos** (sem APIs de pagamento ou precificação bancária).

---

## 🛡️ Invariantes de Desenvolvimento e Qualidade

Ao propor ou implementar qualquer funcionalidade:
* **Não-destrutividade:** A consolidação de pedidos é estritamente uma visão derivada (soma matemática exata); nunca sobrescreva nem delete registros originais submetidos pelos Serviços.
* **Rastreabilidade Fiscal:** Toda linha de pedido e todo relatório individualizado deve permitir a conferência item a item com a respectiva Nota Fiscal do Serviço (Centro de Custo).
* **Conflitos de Regra:** Se qualquer solicitação ou decisão técnica entrar em conflito com as regras de negócio ou limites de escopo, alerte o usuário antes de prosseguir com a implementação.
