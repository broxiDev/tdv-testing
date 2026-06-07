package tdv.teclasunidos.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tdv.teclasunidos.entities.Socio;

class SocioRepositoryTest {

    private SocioRepository repo;

    @BeforeEach
    void setUp() {
        repo = new SocioRepository();
    }

    private Socio nuevoSocio(String nombre, int edad, String dni) throws Exception {
        return new Socio(nombre, edad, "Direccion", dni);
    }

    @Test
    void agregarYBuscarPorDni_devuelveSocio() throws Exception {
        Socio socio = nuevoSocio("Pablo", 25, "123");

        repo.agregar(socio);

        Socio encontrado = repo.buscarPorDni("123");
        assertNotNull(encontrado);
        assertEquals("Pablo", encontrado.getNombre());
    }

    @Test
    void eliminar_quitaSocioDelRepositorio() throws Exception {
        Socio socio = nuevoSocio("Ana", 30, "456");
        repo.agregar(socio);

        repo.eliminar("456");

        assertNull(repo.buscarPorDni("456"));
    }

    @Test
    void listar_devuelveTodosLosSocios() throws Exception {
        repo.agregar(nuevoSocio("A", 20, "1"));
        repo.agregar(nuevoSocio("B", 21, "2"));

        assertEquals(2, repo.listar().size());
    }

    @Test
    void actualizar_reemplazaElSocioConMismoDni() throws Exception {
        repo.agregar(nuevoSocio("Original", 20, "789"));

        Socio actualizado = nuevoSocio("Actualizado", 20, "789");
        repo.actualizar(actualizado);

        Socio encontrado = repo.buscarPorDni("789");
        assertNotNull(encontrado);
        assertEquals("Actualizado", encontrado.getNombre());
    }

    @Test
    void eliminarPorDni_devuelveTrueYEliminaCuandoExiste() throws Exception {
        repo.agregar(nuevoSocio("Luz", 24, "999"));

        boolean eliminado = repo.eliminarPorDni("999");

        assertTrue(eliminado);
        assertNull(repo.buscarPorDni("999"));
    }
}
