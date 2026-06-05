---
name: claude-code-review
description: Revisa sdk-java com foco em compatibilidade retroativa de schemas.
---

# Code Review — sdk-java

- Ler `docs/ai-context.yaml` antes de revisar.
- Verificar se novos campos em schemas são `Optional` ou têm valor default (compatibilidade retroativa).
- Verificar se Builder patterns são mantidos para todos os schemas alterados.
- Verificar impacto em `json-mapper-jackson` ao mudar serialização.
- Ignorar comentários de estilo sem impacto funcional.
