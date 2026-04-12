# Arquitectura y Patrones

La aplicación **CeMeDe** está construida sobre una arquitectura **Clean Architecture** adaptada para Kotlin Multiplatform (KMP), asegurando que la lógica de negocio permanezca independiente de las plataformas.

## Capas del Proyecto

### 1. Domain (Capa Central)
Contiene las reglas de negocio, modelos e interfaces.
*   **Modelos**: `Partner`, `StaffMember`, `DayOfWeek`, etc.
*   **Use Cases**: Lógica específica como `SyncPartnersInfoUseCase` o `GetStaffMemberDetailUseCase`.
*   **Interfaces de Repositorio**: Definición de contratos que las capas de datos deben implementar.

### 2. Data (Capa de Persistencia y Red)
Implementación de los repositorios y fuentes de datos.
*   **Database**: Utiliza **Room** para el almacenamiento local.
*   **Network**: Utiliza **Ktor** para la comunicación con el servidor.
*   **Data Sources**: Fuentes como `CSVDataSourceImpl` para procesamiento de datos externos.

### 3. Presentation (Capa de UI)
Implementada con **Jetpack Compose Multiplatform**.
*   **MVVM**: Uso de `StateFlow` para manejar el estado de las pantallas y `ViewModel` para la lógica de presentación.
*   **Componentes Reutilizables**: `CemedeBanner`, `CemedeCard`, `CemedeErrorState`.

## Patrones de Diseño

*   **Expect/Actual**: Utilizado para implementar funcionalidades que dependen de la plataforma, como `NetworkHelper` o el sistema de logs.
*   **Dependency Injection**: **Koin** se utiliza para la inyección de dependencias a través de módulos compartidos y específicos por plataforma.
*   **Reactive UI**: Estado de la UI reactivo mediante `StateFlow` y recolección de eventos.

---
Regresar al [Índice](../Index.md)
