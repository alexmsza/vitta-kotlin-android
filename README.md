# VittaCare Kotlin (Android)

Aplicativo Android nativo desenvolvido em **Kotlin** com **Jetpack Compose** e **Material 3** para gestão de plantões e acompanhamentos domiciliares de cuidadores.

## Stack
- **Linguagem:** Kotlin 2.0
- **UI:** Jetpack Compose + Material 3
- **Arquitetura:** StateFlow + ViewModel (`CareShiftViewModel`)
- **Build:** Gradle Kotlin DSL (`build.gradle.kts`)
- **Testes:** JUnit 4 (`CareShiftViewModelTest`)

## Funcionalidades
- Listagem de plantões com valor/hora, bairro, horário e observações de cuidado
- Filtro por status (`Todos`, `Disponível`, `Aceito`, `Concluído`)
- Fluxo de transição de status (`Aceitar plantão` → `Concluir`)
- Cadastro de novo plantão via diálogo modal
