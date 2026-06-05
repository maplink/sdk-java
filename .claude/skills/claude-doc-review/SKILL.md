---
name: claude-doc-review
description: Revisa docs do sdk-java conforme mudancas de schema ou cliente.
---

# Doc Review — sdk-java

BLOCK quando:
- novo módulo de schema adicionado sem atualizar README
- mudança de API de cliente sem atualizar docs/flows

PASS quando:
- mudança interna sem impacto de contrato público
