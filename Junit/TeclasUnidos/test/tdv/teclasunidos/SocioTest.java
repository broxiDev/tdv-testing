package tdv.teclasunidos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tdv.teclasunidos.entities.Actividad;
import tdv.teclasunidos.entities.NombreMuyLargoException;
import tdv.teclasunidos.entities.Recurso;
import tdv.teclasunidos.entities.Socio;

class SocioTest {

    @Test
    void crearSocioValido() throws Exception {
        Socio socio = new Socio("pablo", 25, "Av. Siempre Viva 123", "12345678");
        assertEquals("pablo", socio.getNombre());
        assertEquals(25, socio.getEdad());
    }

    @Test
    void crearSocioNombreLargo_lanzaExcepcionNombreLargo() {
        assertThrows(NombreMuyLargoException.class, () -> {
            new Socio("UnNombre", 30, "Direccion", "87654321");
        });
    }

    @Test
    void crearSocio_dniValidoConMasDe7DigitosSinPunto() throws Exception{

        Socio socio = new Socio("Ana", 30, "Direccion", "2345678");
        assertFalse(socio.getDni().contains("."));
        assertEquals(7, socio.getDni().length());
    }

    @Test
    void toStringDeActividad_retornaNombreLugarConcatenadoConGuion(){
        Actividad actividad = new Actividad("Concierto", "Juan", "10", 15, "Belgrano", 20);
        assertEquals("Concierto-Belgrano", actividad.toString());
    }

    @Test
    void crearRecursoOficina_fallaAlCrearConNombreOficina() {
        Recurso recurso = new Recurso("Oficina", "Calle Principal 123");
        assertNull(recurso.getNombre());
        assertNull(recurso.getUbicacion());
    }

    // Agregá más tests acá
}

