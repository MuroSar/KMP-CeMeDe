# 📋 Roadmap y Pendientes (To-Do)

Este documento centraliza las tareas pendientes, mejoras de UI y lógica de negocio, priorizadas según el impacto en la experiencia del usuario y la estabilidad del sistema.

---

## 🚀 Prioridad Alta (Crítico / Core UX)

### 🛠️ Corrección de Scroll - Detalle de Staff (StaffDetail) ✅
*Optimización de la experiencia de navegación en el perfil del profesional.*
- [x] **Lógica de scroll condicional:**
    - [x] Escenario A: Si no hay elementos activos (horarios/sesiones) -> **Deshabilitar scroll**.
    - [x] Escenario B: Elemento activo presente y visible en pantalla -> **Mantener scroll estático**.
    - [x] Escenario C: Elemento activo presente pero fuera del área visible -> **Habilitar/Ejecutar scroll automático** hasta el elemento.

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

## 🎨 Notas de Diseño (UI/UX)
- **Colores:** Mantener la sutileza de los grises y el verde oliva de la marca.
- **Iconografía:** Usar recursos vectoriales propios (como el estetoscopio) para asegurar nitidez.
- **Desktop:** Asegurar que la UI sea adaptativa (no solo estirar la versión móvil).

---
Regresar al [Índice](Index.md)
