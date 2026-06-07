# JUnit + TestNG Cheat Sheet

## Estructura básica de un test

```java
@Test
public void nombreDescriptivo_condicion_resultadoEsperado() {
    // Arrange — preparo los datos
    // Act     — ejecuto lo que quiero probar
    // Assert  — verifico el resultado
}
```

---

## Anotaciones

| JUnit 4            | JUnit 5              | TestNG                  | Cuándo corre                           |
|--------------------|----------------------|-------------------------|----------------------------------------|
| `@Test`            | `@Test`              | `@Test`                 | Marca un método como test              |
| `@Before`          | `@BeforeEach`        | `@BeforeMethod`         | Antes de **cada** test                 |
| `@After`           | `@AfterEach`         | `@AfterMethod`          | Después de **cada** test               |
| `@BeforeClass`     | `@BeforeAll`         | `@BeforeClass`          | Una sola vez antes de todos (static)   |
| `@AfterClass`      | `@AfterAll`          | `@AfterClass`           | Una sola vez después de todos (static) |
| —                  | —                    | `@BeforeTest`           | Antes de cada `<test>` del XML         |
| —                  | —                    | `@AfterTest`            | Después de cada `<test>` del XML       |
| —                  | —                    | `@BeforeSuite`          | Una sola vez al inicio de la suite     |
| —                  | —                    | `@AfterSuite`           | Una sola vez al final de la suite      |
| `@Ignore`          | `@Disabled`          | `@Test(enabled=false)`  | Saltea el test                         |
| `@Test(expected=)` | `assertThrows()`     | `@Test(expectedExceptions=)` | Espera que se lance una excepción |

---

## Assertions más usadas

```java
// JUnit 4 — import static org.junit.Assert.*;
// JUnit 5 — import static org.junit.jupiter.api.Assertions.*;

assertEquals(esperado, actual);          // son iguales
assertNotEquals(noEsperado, actual);     // son distintos
assertTrue(condicion);                   // es true
assertFalse(condicion);                  // es false
assertNull(objeto);                      // es null
assertNotNull(objeto);                   // no es null

// Con mensaje de error personalizado
assertEquals("mensaje si falla", esperado, actual);  // JUnit 4
assertEquals(esperado, actual, "mensaje si falla");  // JUnit 5

// Excepciones (JUnit 5)
assertThrows(MiExcepcion.class, () -> objeto.metodoQueExplota());

// JUnit 4 (viejo)
@Test(expected = MiExcepcion.class)
public void test() { objeto.metodoQueExplota(); }
```

---

## Orden de ejecución (queda grabado)

**JUnit 4 / JUnit 5**
```
@BeforeAll / @BeforeClass   ← una sola vez
    @BeforeEach / @Before   ← antes de cada test
        @Test
    @AfterEach / @After     ← después de cada test
@AfterAll / @AfterClass     ← una sola vez
```

**TestNG** (más niveles)
```
@BeforeSuite        ← una sola vez, inicio de todo
  @BeforeTest       ← antes de cada <test> del XML
    @BeforeClass    ← antes de la primera prueba de la clase
      @BeforeMethod ← antes de cada @Test
          @Test
      @AfterMethod  ← después de cada @Test
    @AfterClass     ← después de la última prueba de la clase
  @AfterTest        ← después de cada <test> del XML
@AfterSuite         ← una sola vez, fin de todo
```

---

## Test Suite (JUnit 4)

```java
@RunWith(Suite.class)
@Suite.SuiteClasses({ ClaseTest1.class, ClaseTest2.class })
public class MiSuite {}
```

---

## TestNG — DataProvider

```java
// Retorna array de arrays (múltiples parámetros por caso)
@DataProvider
public Object[][] Generador() {
    return new Object[][] {
        new Object[] { 1, "a" },
        new Object[] { 2, "b" },
    };
}

@Test(dataProvider = "Generador")
public void miTest(Integer n, String s) { ... }

// Retorna array de objetos (un objeto por caso)
@DataProvider
public Persona[] GeneradorPersona() {
    return new Persona[] { new Persona(...), new Persona(...) };
}

@Test(dataProvider = "GeneradorPersona")
public void miTest(Persona p) { ... }

// Repetir N veces
@Test(dataProvider = "GeneradorPersona", invocationCount = 3)
public void miTest(Persona p) { ... }
```

---

## TestNG — Parámetros desde XML

```java
// En la clase de test
@Test
@Parameters({"nombre", "edad"})
public void miTest(@Optional("valor default") String nombre,
                   @Optional("0") int edad) { ... }
```

```xml
<!-- En testng.xml -->
<test name="Mi test">
    <parameter name="nombre" value="Luis" />
    <parameter name="edad"   value="30" />
    <classes>
        <class name="paquete.MiTest" />
    </classes>
</test>
```

---

## TestNG — Excepciones

```java
// Forma 1: anotación
@Test(expectedExceptions = { MiExcepcion.class })
public void testExcepcion() throws MiExcepcion {
    objeto.metodoQueExplota();
}

// Forma 2: try-catch (más control)
@Test
public void testExcepcion() {
    try {
        objeto.metodoQueExplota();
        Assert.fail(); // si llega acá, no lanzó → falla
    } catch (MiExcepcion e) {
        // OK
    }
}
```

---

## TestNG — testng.xml básico

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Mi Suite" verbose="1">

    <parameter name="param-global" value="valor" />

    <test name="Mi Test">
        <parameter name="param-local" value="valor" />
        <classes>
            <class name="paquete.MiClaseTest">
                <methods>
                    <include name="soloEsteMetodo" />
                </methods>
            </class>
        </classes>
    </test>

</suite>
```

---

## TestNG — Assertions

```java
// import org.testng.Assert;
Assert.assertEquals(actual, esperado);
Assert.assertNotEquals(actual, noEsperado);
Assert.assertTrue(condicion);
Assert.assertFalse(condicion);
Assert.assertNull(objeto);
Assert.assertNotNull(objeto);
Assert.fail("mensaje"); // fuerza fallo del test
```

---

## pom.xml — dependencias

```xml
<!-- JUnit 4 -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>

<!-- JUnit 5 -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>

<!-- TestNG -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.0</version>
    <scope>test</scope>
</dependency>
```

---

## Reglas de oro

- Un test = una sola cosa a verificar
- El nombre del test dice qué hace, en qué condición y qué espera
- Si el test necesita más de 10 líneas, algo está mal en el diseño
- Los tests no dependen entre sí (cada uno arranca desde cero)
