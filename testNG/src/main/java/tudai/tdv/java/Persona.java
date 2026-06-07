package tudai.tdv.java;

public class Persona {

    private String nombre;
    private String dni;
    private String fechaNacimiento;
    private int edad;
    private boolean habilitadoParaVotar;

    public Persona(String nombre, String dni, String fechaNacimiento, int edad, boolean habilitadoParaVotar) {
        this.nombre = nombre;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.habilitadoParaVotar = habilitadoParaVotar;
    }

    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public int getEdad() { return edad; }
    public boolean isHabilitadoParaVotar() { return habilitadoParaVotar; }

    public void setEdad(int edad) throws EdadNopermitidaException {
        if (edad < 0) throw new EdadNopermitidaException("La edad no puede ser negativa");
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Persona{nombre='" + nombre + "', dni='" + dni +
               "', fechaNacimiento='" + fechaNacimiento +
               "', edad=" + edad + ", habilitadoParaVotar=" + habilitadoParaVotar + "}";
    }

}
