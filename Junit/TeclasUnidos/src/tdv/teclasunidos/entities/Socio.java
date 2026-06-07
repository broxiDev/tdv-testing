package tdv.teclasunidos.entities;

public class Socio {
    private String nombre;
    private int edad;
    private String direccion;
    private String dni;

    public Socio(String nombre, int edad, String direccion, String dni) throws NombreMuyLargoException, EdadInvalidaException {
    	if (nombre.length()>51) 
    		throw new NombreMuyLargoException();
        this.nombre = nombre;
        if (edad <0 ||edad >100) 
        	throw new EdadInvalidaException();
        this.edad = edad;
        this.direccion = direccion;
        
        this.dni = dni;
    }

	public int getEdad() {
		return edad;
	}

	public String getDni() {
		return dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getNombre() {
		return nombre;
	}

// Getters y setters
}

