package tudai.tdv.test.testng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import tudai.tdv.java.EdadNopermitidaException;
import tudai.tdv.java.Persona;

public class TestPersona {

    // -------------------------------------------------------------------
    // DataProvider + test de edad
    // -------------------------------------------------------------------

    @DataProvider
    public Persona[] GeneradorPersona() {
        System.out.println("Genero casos");
        // Edades calculadas al 2026 para que el test pase
        return new Persona[] {
            new Persona("Juan",    "26.150.235", "1979-01-01", 47, true),
            new Persona("Pedro",   "27.280.234", "1980-02-01", 46, true),
            new Persona("Maria",   "28.184.259", "1981-03-01", 45, true),
            new Persona("Cecilia", "32.234.528", "1983-04-01", 43, true),
            new Persona("Carlos",  "33.124.235", "1985-04-01", 41, true),
            new Persona("Jose",    "35.345.534", "1987-04-01", 39, true)
        };
    }

    @Test(dataProvider = "GeneradorPersona", invocationCount = 99)
    public void testEdadBienCalculada(Persona p) {
        int edadReportada = p.getEdad();
        int edadReal = calcularEdad(p.getFechaNacimiento());
        Assert.assertTrue(edadReportada == edadReal,
            "Edad incorrecta para " + p.getNombre() + ": reportada=" + edadReportada + " real=" + edadReal);
    }

    // -------------------------------------------------------------------
    // Test con parametros desde testng.xml (usa @Optional como fallback)
    // -------------------------------------------------------------------

    @Test
    @Parameters({"nombre", "dni", "fnac"})
    public void testConParametros(@Optional("Pepe") String nombre,
                                  @Optional("20.345.678") String dni,
                                  @Optional("2001-2-3") String fNac) {
        Persona p = new Persona(nombre, dni, fNac, 0, false);
        System.out.println(p);
    }

    // -------------------------------------------------------------------
    // Test de excepcion: edad negativa
    // -------------------------------------------------------------------

    @Test(expectedExceptions = { EdadNopermitidaException.class })
    public void testNoPermitirEdadesNegativas() throws EdadNopermitidaException {
        Persona p = new Persona("Anibal", "26.150.235", "1979-01-01", 44, true);
        p.setEdad(-1);
    }

    // -------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------

    private int calcularEdad(String fechaNacimiento) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNac = sdf.parse(fechaNacimiento);
            Calendar calNac = Calendar.getInstance();
            Calendar calActual = Calendar.getInstance();
            calNac.setTime(fechaNac);
            calActual.setTime(new Date());
            int edad = calActual.get(Calendar.YEAR) - calNac.get(Calendar.YEAR);
            if (calNac.get(Calendar.MONTH) > calActual.get(Calendar.MONTH) ||
               (calNac.get(Calendar.MONTH) == calActual.get(Calendar.MONTH) &&
                calNac.get(Calendar.DAY_OF_MONTH) > calActual.get(Calendar.DAY_OF_MONTH))) {
                edad--;
            }
            return edad;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
