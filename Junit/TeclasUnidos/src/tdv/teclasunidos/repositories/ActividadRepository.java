package tdv.teclasunidos.repositories;

import java.util.*;

import tdv.teclasunidos.entities.Actividad;

public class ActividadRepository {
    private Map<String, Actividad> actividades = new HashMap<>();

    public void agregar(Actividad actividad) {
        actividades.put(actividad.getNombre(), actividad);
    }

    public void eliminar(String nombre) {
        actividades.remove(nombre);
    }

    public Actividad buscarPorNombre(String nombre) {
        return actividades.get(nombre);
    }

    public List<Actividad> listar() {
        return new ArrayList<>(actividades.values());
    }

    public void actualizar(Actividad actividad) {
        actividades.put(actividad.getNombre(), actividad);
    }
    public boolean eliminarPorNombre(String nombre) {
    return ((Collection<Actividad>) actividades).removeIf(a -> a.getNombre().equalsIgnoreCase(nombre));
}
 public int cantidad() {
	 return actividades.size();
 }
}

