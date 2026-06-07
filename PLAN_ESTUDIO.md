# Plan de Estudio — Técnicas de Documentación y Validación

## Lo que ya tenés (no repetir)

| Proyecto | Framework | Qué cubre |
|----------|-----------|-----------|
| `Junit/first-junit` | JUnit 4 | Ciclo de vida, Suite, cálculo de edad |
| `Junit/TeclasUnidos` | JUnit 5 | `assertThrows`, `@BeforeEach`, CRUD |
| `testNG/` | TestNG 7 | `@DataProvider`, `@Parameters`, `expectedExceptions`, ciclo de vida completo |
| `Junit/CHEATSHEET.md` | — | Referencia rápida JUnit 4 vs 5 |

---

## Lo que se toma en los parciales (patrón real)

Basado en los dos parciales vistos:

1. **TestNG — `@DataProvider`**: generar una colección de objetos para parametrizar tests
2. **Excepción esperada**: dos estilos — `expectedExceptions` (TestNG) y `assertThrows` (JUnit 5)
3. **Test de cálculo**: armar un escenario, calcular el resultado esperado a mano y comparar con `assertEquals`
4. **Transiciones de estado**: verificar que ciertos cambios de estado son válidos o inválidos
5. **Conceptual**: cuadrantes ágiles, deuda técnica, complejidad ciclomática

---

## Plan en 4 sesiones

### Sesión 1 — Correr lo que ya existe (1.5h)
**Objetivo**: ver los tests funcionando y entender qué hace cada uno.

- [ ] Abrir `testNG/` en IntelliJ → correr `testng.xml` → leer la consola
- [ ] Leer `DemoPrecedencia.java` → ver el orden de ejecución de los hooks
- [ ] Leer `TestPersona.java` → identificar los 3 patrones: DataProvider, Parameters, expectedExceptions
- [ ] Abrir `first-junit` → correr `PersonaTest` y `DemoPrecedencia`
- [ ] Abrir `TeclasUnidos` → correr `SocioTest` y `SocioRepositoryTest`

**Entregable**: sabés correr tests y leer los resultados.

---

### Sesión 2 — Escribir el patrón del parcial desde cero (2h)
**Objetivo**: escribir DataProvider + excepción sin mirar el código existente.

Crear `testNG/src/test/.../TestVenta.java` con:
- Un `@DataProvider` que genere 5 productos (nombre, precio, edadMinima)
- Un `@Test` que use ese DataProvider y verifique un cálculo de precio
- Un `@Test` que espere una excepción por stock insuficiente

Referencia si te trabás: `TestPersona.java`

---

### Sesión 3 — Transiciones de estado en JUnit 5 (1.5h)
**Objetivo**: practicar el patrón del parcial de la piscina.

Crear `Junit/TeclasUnidos/test/.../EstadoTest.java` con:
- Una clase o enum con estados: `Stopped`, `Heating`, `Draining`, `Refreshing`
- Test: desde `Stopped` se puede pasar a cualquier estado → `assertDoesNotThrow`
- Test: desde `Draining` a `Refreshing` sin pasar por `Stopped` → `assertThrows`

Referencia si te trabás: `SocioTest.java` (patrón de `assertThrows`)

---

### Sesión 4 — Teoría (45 min)
**Objetivo**: tener las respuestas conceptuales memorizadas.

#### Cuadrantes ágiles
- **Q1** (tecnología, soportan al equipo): Unit tests, integration tests → **JUnit y TestNG caen acá**
- **Q2** (negocio, soportan al equipo): tests funcionales, ejemplos, story tests
- **Q3** (negocio, critican el producto): UAT, exploratory testing
- **Q4** (tecnología, critican el producto): performance, load, security testing

#### Complejidad ciclomática
- **Desde testing**: cuántos casos de prueba mínimos necesitás (1 por cada camino independiente)
- **Desde calidad**: si es alta (>10), el código es difícil de mantener → indicio de deuda técnica

#### Deuda técnica — verdadero/falso típico del parcial
- Sí afecta el rendimiento del **desarrollo** ✓
- Alta complejidad ciclomática es indicio de deuda técnica ✓
- La complejidad ciclomática **no mide** performance en runtime ✗

---

## Archivos clave

| Qué buscar | Dónde |
|------------|-------|
| DataProvider con objetos | `testNG/src/test/.../TestPersona.java` |
| `expectedExceptions` | `TestPersona.java` → `testNoPermitirEdadesNegativas` |
| `assertThrows` (JUnit 5) | `TeclasUnidos/test/.../SocioTest.java` |
| `@BeforeEach` + CRUD | `TeclasUnidos/test/.../SocioRepositoryTest.java` |
| Ciclo de vida completo | `testNG/src/test/.../DemoPrecedencia.java` |
| Referencia rápida anotaciones | `CHEATSHEET.md` (este mismo directorio) |

---

## Chequeo final antes del parcial
- [ ] Escribir un `@DataProvider` de memoria
- [ ] Escribir un test de excepción de memoria (las dos formas)
- [ ] Explicar sin mirar: diferencia entre `@Before` (JUnit 4) y `@BeforeEach` (JUnit 5)
- [ ] Decir en qué cuadrante caen JUnit y TestNG y por qué
