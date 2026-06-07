package tdv.teclasunidos.repositories;

import java.util.HashMap;
import java.util.Map;

import tdv.teclasunidos.entities.Recurso;


public class RecursoRepository {
    private Map<String, Recurso> recursos = new HashMap<>();
	public Recurso buscarPorNombre(String nombreRecurso) {
		// TODO Auto-generated method stub
		return null;
	}

	public void agregar(Recurso recurso) {
	     recursos.put(recurso.getNombre(),recurso);
		
	}

}
