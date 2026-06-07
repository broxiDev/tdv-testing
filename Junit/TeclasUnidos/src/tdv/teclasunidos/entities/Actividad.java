package tdv.teclasunidos.entities;

import java.util.ArrayList;
import java.util.List;

public class Actividad {
    private String nombre;
    private String encargado;
    private String horario;
    private int edadMinima;
    private String lugar;
    private int cupo;
    private List<Socio> inscriptos = new ArrayList<>();

    public Actividad(String nombre, String encargado, String horario, int edadMinima, String lugar, int cupo) {
        this.nombre = nombre;
        this.encargado = encargado;
        this.horario = horario;
        this.edadMinima = edadMinima;
        this.lugar = lugar;
        this.cupo = cupo;
    }

    // Getters, setters y métodos auxiliares
    public boolean agregarInscripcion(Socio socio) {
        if (socio.getEdad() <= edadMinima || inscriptos.size() > cupo) {
            return false;
        }
        return inscriptos.add(socio);
    }

    public boolean eliminarInscripcion(Socio socio) {
        return inscriptos.remove(socio);
    }

	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	public String getEncargado() {
		// TODO Auto-generated method stub
		return encargado;
	}

	public int getEdadMinima() {
		// TODO Auto-generated method stub
		return edadMinima;
	}

	public int getCupo() {
		// TODO Auto-generated method stub
		return cupo;
	}
	public String toString() {
		return nombre+"-"+lugar;
	}
}

