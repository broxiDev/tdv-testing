package org.example;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class Persona {

	String nombre;
	String fechaNacimiento;
	String dni;
	int edad;
	boolean habilitadoParaVotar;

	public Persona(String nombre, String dni, String fechaNacimiento, int edad, boolean habilitadoParaVotar) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.edad = edad;
		this.habilitadoParaVotar = habilitadoParaVotar;
		this.dni=dni;
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		this.edad = calcularEdad();
	}
	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}
	public boolean isHabilitadoParaVotar() {
		return edad > 16;
	}
	public void setHabilitadoParaVotar(boolean habilitadoParaVotar) {
		this.habilitadoParaVotar = habilitadoParaVotar;
	}
	public String getDNI() {
		return dni;
	}
	
	


	private int calcularEdad() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNac = sdf.parse(fechaNacimiento);
            Date fechaActual = new Date();
            Calendar calNac = Calendar.getInstance();
            Calendar calActual = Calendar.getInstance();
            calNac.setTime(fechaNac);
            calActual.setTime(fechaActual);
            int edadCalc = calActual.get(Calendar.YEAR) - calNac.get(Calendar.YEAR);
            if (calNac.get(Calendar.MONTH) > calActual.get(Calendar.MONTH) ||
                (calNac.get(Calendar.MONTH) == calActual.get(Calendar.MONTH) &&
                 calNac.get(Calendar.DAY_OF_MONTH) > calActual.get(Calendar.DAY_OF_MONTH))) {
                edadCalc--;
            }
            return edadCalc;
        } catch (ParseException e) {
            return -1;
        }
    }

	
	

}
