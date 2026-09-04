---
trigger: always_on
description: "UI/UX specifications, operational user flows (Mobile and Desktop), and interaction design constraints for Consolida IAG."
---

# Consolida IAG — UI/UX Specifications & Operational Flows

## Purpose

This rule defines the user experience (UX), interface requirements, and operational workflows for **Consolida IAG**.

Any frontend design, component implementation, or user journey created by the Agent **MUST** follow these usability constraints to ensure rapid adoption and zero operational friction.

---

## 1. User Profiles & Operational Reality

| Perfil de Usuário | Contexto e Restrições | Diretriz de Design Principal |
| :--- | :--- | :--- |
| **Operador do Serviço (Cozinha)** | Tempo escasso (< 2 min), ambiente dinâmico, pouca familiaridade com sistemas complexos. Alto risco de abandono se houver burocracia. | **Interface Ultrassimples:** Lista visual, botões grandes de `+` / `-`, zero digitação de nomes de insumos. |
| **Comprador / Gestor** | Trabalho em desktop, necessidade de negociar por volume no atacado e prestar contas por Centro de Custo. | **Painel com 2 Abas:** Visão Consolidada para cotação e Visão Individual por Serviço com exportação em PDF/impressão para NF. |

---

## 2. Pilar 1: Fluxo do Solicitante (Web Mobile)

A interface dos serviços opera em ambiente web responsivo mobile-first acessada diretamente por link ou identificador do serviço:

### 2.1 Requisitos de Interação

1. **Identificação Clara do Serviço:** A tela deve exibir em destaque o Serviço ativo (`CEI`, `CCA` ou `CJ`).
2. **Catálogo Pré-carregado:** Exibição em lista dos insumos ativos recorrentes da instituição (ex: Arroz, Feijão, Óleo, Detergente).
3. **Controles de Quantidade Numérica:**
   - Botões grandes e destacados de incremento (`+`) e decremento (`-`).
   - Alvos de toque com área mínima de 44x44px para facilitar o toque com as mãos no ambiente de cozinha.
   - Zero digitação manual obrigatória de nomes ou códigos de produtos.
4. **Campo de Observação Livre (RN-02.3):**
   - Localizado ao final da lista para registrar itens pontuais, emergências ou produtos fora do catálogo institucional.
5. **Ação de Conclusão Única:**
   - Botão de ação principal fixo ou em destaque: *"Finalizar Pedido da Semana"*.
   - Feedback visual imediato de sucesso após envio.
6. **Métrica de Usabilidade:**
   - O fluxo completo deve ser realizável em **menos de 2 minutos**.

---

## 3. Pilar 2: Fluxo do Comprador/Gestor (Web Desktop)

O painel de compras e gestão opera em ambiente web desktop estruturado em **duas abas centrais**:

### 3.1 Aba 1 — Visão Consolidada (Negociação & Atacado)

- **Objetivo:** Permitir ao comprador negociar melhores preços por volume com fornecedores atacadistas.

- **Comportamento:**
  - Agrupamento automático por produto.
  - Soma matemática exata das quantidades de todos os serviços participantes no ciclo ativo (RN-03.1).
  - Exemplo visual: *Arroz Agulhinha Tipo 1: 20kg [CEI] + 15kg [CCA] + 25kg [CJ] = 60kg total*.
  - Consolidação em tempo real ou sob demanda de forma puramente derivada (RN-03.2).

### 3.2 Aba 2 — Visão por Serviço (Prestação de Contas & Auditoria)

- **Objetivo:** Conferência física item a item com as Notas Fiscais emitidas e envio ao setor financeiro.

- **Comportamento:**
  - Filtro seletor por Serviço (`CEI`, `CCA` ou `CJ`).
  - Exibição estrita dos itens e quantidades solicitados exclusivamente por aquele Centro de Custo (RN-04.2).
  - Exibição das observações de texto livre submetidas pelo respectivo serviço.
  - **Exportação em PDF e Impressão:** Botão de ação para gerar documento formatado para impressão física, contendo cabeçalho com identificação do Centro de Custo, data do ciclo e relação de itens para conferência na entrega da mercadoria.

---

## 4. Invariantes de Usabilidade e Acessibilidade

1. **Responsividade Pura:** Não projetar nem sugerir recursos nativos de SO que dependam de compilação em lojas de apps (sem push notifications nativas complexas, sem APK/IPA).
2. **Prevenção de Erros de Submissão:**
   - Bloquear envio se nenhuma quantidade for superior a zero (RN-02.2).
   - Confirmação visual simples antes do envio final.
3. **Persistência de Sessão e Rastreabilidade:** Toda submissão mobile registra timestamp e vínculo indelével ao Centro de Custo de origem.
