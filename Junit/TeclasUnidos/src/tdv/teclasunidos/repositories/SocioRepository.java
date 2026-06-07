package tdv.teclasunidos.repositories;

import java.util.*;

import tdv.teclasunidos.entities.Socio;

public class SocioRepository {
    private Map<String, Socio> socios = new HashMap<>();

    public void agregar(Socio socio) {
        socios.put(socio.getDni(), socio);
    }

    public void eliminar(String dni) {
        socios.remove(dni);
    }

    public Socio buscarPorDni(String dni) {
        return socios.get(dni);
    }

    public List<Socio> listar() {
        return new ArrayList<>(socios.values());
    }

    public void actualizar(Socio socio) {
        socios.put(socio.getDni(), socio);
    }

    public boolean eliminarPorDni(String dni) {
        return socios.remove(dni) != null;
    }
}
