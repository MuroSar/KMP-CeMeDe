# 📋 Roadmap y Pendientes (To-Do)

Este documento centraliza las tareas pendientes, mejoras de UI y lógica de negocio, priorizadas según el impacto en la experiencia del usuario y la estabilidad del sistema.

---

## 🚀 Prioridad Alta (Crítico / Core UX)

### 🛠️ Ajuste de Versión en Splash
*Evitar el uso de strings hardcodeados para la versión de la app.*
- [ ] **Dinamicidad de la versión:**
    - [ ] Configurar la Splash Screen para que obtenga y muestre el `versionCode` (o `versionName`) real definido en el build script, eliminando la dependencia de un string manual.

### 🛠️ Configuración de Seguridad - Android Release
*Asegurar el manejo correcto de credenciales de firmado.*
- [ ] **Configuración de Keystore:**
    - [ ] Implementar la lectura de claves (`storePassword`, `keyAlias`, `keyPassword`) desde el archivo `local.properties`.
    - [ ] Ajustar el `build.gradle.kts` del módulo `:composeApp` para utilizar estas propiedades en el bloque `signingConfigs`.
    - [ ] Ajustar la documentación [keystore](./releases/android/keystore.md)

### 🛠️ Corrección de Scroll - Detalle de Staff (StaffDetail)
*Optimización de la experiencia de navegación en el perfil del profesional.*
- [ ] **Lógica de scroll condicional:**
    - [ ] Escenario A: Si no hay elementos activos (horarios/sesiones) -> **Deshabilitar scroll**.
    - [ ] Escenario B: Elemento activo presente y visible en pantalla -> **Mantener scroll estático**.
    - [ ] Escenario C: Elemento activo presente pero fuera del área visible -> **Habilitar/Ejecutar scroll automático** hasta el elemento.
> **💡 Recomendación técnica:** Utilizar `LazyListState` junto con `derivedStateOf` para monitorear `layoutInfo.visibleItemsInfo`. Esto permite detectar la visibilidad del ítem activo sin disparar recomposiciones innecesarias cada vez que el usuario scrollea.

---

## 🛠️ Prioridad Media (Nuevas Funcionalidades)

### ⚙️ Pantalla de Configuración
- [ ] **Ajustes de la Aplicación:**
    - [ ] Implementar una interfaz para gestionar preferencias de usuario, parámetros de sincronización o temas visuales.

### 🔄 Lógica de Negocio
- [ ] **Flujo de Protocolos:** Implementar la máquina de estados o navegación secuencial para la gestión de protocolos médicos/clínicos.

---

## 💤 Prioridad Baja (Mejoras y Pulido)

### 🖥️ Ajustes de UI Desktop (jvmMain)
- [ ] **Refinamiento de Plataforma:** Adaptar la interfaz para que se sienta nativa en sistemas de escritorio (Windows/macOS/Linux).
> **💡 Recomendación técnica:** Implementar `VerticalScrollbar` de JetBrains específicamente en el sourceSet de Desktop. A diferencia de Mobile, el usuario de escritorio espera una barra visual persistente y una sensibilidad de la rueda del mouse ajustada para una navegación precisa.

---

## 🧪 Estabilidad y Calidad (Mantenimiento)

### 🩺 Testing
- [ ] **Unit Tests:**
    - [ ] UT de ViewModels (Splash, Staff, Login).
      - [ ] `MainViewModel`
      - [ ] `PartnerListViewModel`
      - [ ] `SplashViewModel`
      - [ ] `StaffMemberDetailViewModel`
      - [ ] `StaffMemberListViewModel`
    - [ ] UT de UseCases.
      - [ ] `GetAllPartnersUseCase`
      - [ ] `GetAllStaffMembersFlowUseCase`
      - [ ] `GetStaffMemberDetailFlowUseCase`
      - [ ] `SyncPartnersInfoUseCase`
      - [ ] `SyncStaffMemberInfoUseCase`
      - [ ] `SyncStaffMembersWorkingScheduleUseCase`
    - [ ] UT Repositories.
      - [ ] `PartnerRepository`
      - [ ] `StaffMemberRepository`
    - [ ] UT data sources.
      - [ ] `CSVDataSource`
    - [ ] UT data base.
      - [ ] `CemedeDataBase`
    - [ ] UT mappers.
      - [ ] `CsvParser`
      - [ ] `PartnerMapper`
      - [ ] `StaffMemberAndPartnersMapper`
    - [ ] UT de utils
      - [ ] Validar formateadores de fecha y hora (`DateTimeHandler`).
      - [ ] `PhonesHelper`

---

## 🎨 Notas de Diseño (UI/UX)
- **Colores:** Mantener la sutileza de los grises y el verde oliva de la marca.
- **Iconografía:** Usar recursos vectoriales propios (como el estetoscopio) para asegurar nitidez.
- **Desktop:** Asegurar que la UI sea adaptativa (no solo estirar la versión móvil).

---

## ✅ Finalizado
- [x] Configuración inicial de Kotlin Multiplatform 2.1.0+
  - [x] Implementación de Koin para DI
  - [x] Implementación a Room para persistencia local
- [x] Sincronización de Staff desde CSV (Google Sheets)
- [x] Listado y detalle de Staff
- [x] Listado y detalle de Socio
- [x] Comunicación entre detalles de Socio y Staff
- [x] Documentación técnica del proyecto
- [x] Seteo de íconos por plataforma (Android Manifest & Xcode Assets)
---
Regresar al [Índice](Index.md)
