
package tdv.teclasunidos;
import java.util.Scanner;

import tdv.teclasunidos.entities.Actividad;
import tdv.teclasunidos.entities.Recurso;
import tdv.teclasunidos.entities.Socio;
import tdv.teclasunidos.repositories.*;
import tdv.teclasunidos.services.*;

import java.time.LocalDateTime;

public class Main {
    private static final SocioRepository socioRepo = new SocioRepository();
    private static final ActividadRepository actividadRepo = new ActividadRepository();
    private static final RecursoRepository recursoRepo = new RecursoRepository();
    private static final InscripcionService inscripcionService = new InscripcionService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Club Social y Deportivo ---");
            System.out.println("1. Alta de Socio");
            System.out.println("2. Alta de Actividad");
            System.out.println("3. Inscribir Socio a Actividad");
            System.out.println("4. Listar Socios");
            System.out.println("5. Listar Actividades");
            System.out.println("7. Alta de Recurso");
System.out.println("8. Alta de Recurso");
System.out.println("9. Reservar Recurso");
System.out.println("10. Cancelar Reserva");
System.out.println("11. Salir");
            System.out.println("6. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    altaSocio(scanner);
                    break;
                case "2":
                    altaActividad(scanner);
                    break;
                case "3":
                    inscribirSocio(scanner);
                    break;
                case "4":
                    listarSocios();
                    break;
                case "5":
                    listarActividades();
                    break;
               case "6":
    eliminarSocio(scanner);
    break;
case "7":
    eliminarActividad(scanner);
    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }

    private static void eliminarActividad(Scanner scanner) {
		// TODO Auto-generated method stub
		
	}

	private static void eliminarSocio(Scanner scanner) {
		// TODO Auto-generated method stub
		
	}

	private static void altaSocio(Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = Integer.parseInt(scanner.nextLine());
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();

       try { Socio socio = new Socio(nombre, edad, direccion, dni);
       		socioRepo.agregar(socio);
       	} catch (Exception e) {
		// TODO: handle exception
       	}
        System.out.println("Socio agregado.");
    }

    private static void altaActividad(Scanner scanner) {
        System.out.print("Nombre de actividad: ");
        String nombre = scanner.nextLine();
        System.out.print("Encargado: ");
        String encargado = scanner.nextLine();
        System.out.print("Horario: ");
        String horario = scanner.nextLine();
        System.out.print("Edad mínima: ");
        int edadMinima = Integer.parseInt(scanner.nextLine());
        System.out.print("Lugar: ");
        String lugar = scanner.nextLine();
        System.out.print("Cupo: ");
        int cupo = Integer.parseInt(scanner.nextLine());

        Actividad actividad = new Actividad(nombre, encargado, horario, edadMinima, lugar, cupo);
        actividadRepo.agregar(actividad);
        System.out.println("Actividad agregada.");
    }

private static final ReservaService reservaService = new ReservaService();

private static void altaRecurso(Scanner scanner) {
    System.out.print("Nombre del recurso: ");
    String nombre = scanner.nextLine();
    System.out.print("Ubicación: ");
    String ubicacion = scanner.nextLine();

    Recurso recurso = new Recurso(nombre, ubicacion);
    recursoRepo.agregar(recurso);
    System.out.println("Recurso agregado.");
}

private static void reservarRecurso(Scanner scanner) {
    System.out.print("DNI del socio: ");
    String dni = scanner.nextLine();
    System.out.print("Nombre del recurso: ");
    String nombreRecurso = scanner.nextLine();
    System.out.print("Inicio (YYYY-MM-DDTHH:MM): ");
    LocalDateTime inicio = LocalDateTime.parse(scanner.nextLine());
    System.out.print("Fin (YYYY-MM-DDTHH:MM): ");
    LocalDateTime fin = LocalDateTime.parse(scanner.nextLine());

    Socio socio = socioRepo.buscarPorDni(dni);
    Recurso recurso = recursoRepo.buscarPorNombre(nombreRecurso);

    if (socio == null || recurso == null) {
        System.out.println("Socio o recurso no encontrado.");
        return;
    }

    boolean exito = reservaService.reservar(recurso, socio, inicio, fin);
    System.out.println(exito ? "Reserva realizada." : "Conflicto con otra reserva.");
}

private static void cancelarReserva(Scanner scanner) {
    System.out.print("DNI del socio: ");
    String dni = scanner.nextLine();
    System.out.print("Nombre del recurso: ");
    String nombreRecurso = scanner.nextLine();
    System.out.print("Inicio (YYYY-MM-DDTHH:MM): ");
    LocalDateTime inicio = LocalDateTime.parse(scanner.nextLine());
    System.out.print("Fin (YYYY-MM-DDTHH:MM): ");
    LocalDateTime fin = LocalDateTime.parse(scanner.nextLine());

    Socio socio = socioRepo.buscarPorDni(dni);
    Recurso recurso = recursoRepo.buscarPorNombre(nombreRecurso);

    if (socio == null || recurso == null) {
        System.out.println("Socio o recurso no encontrado.");
        return;
    }

    boolean exito = reservaService.cancelarReserva(recurso, socio, inicio, fin);
    System.out.println(exito ? "Reserva cancelada." : "Reserva no encontrada.");
}

    private static void inscribirSocio(Scanner scanner) {
        System.out.print("DNI del socio: ");
        String dni = scanner.nextLine();
        System.out.print("Nombre de la actividad: ");
        String nombreActividad = scanner.nextLine();

        Socio socio = socioRepo.buscarPorDni(dni);
        Actividad actividad = actividadRepo.buscarPorNombre(nombreActividad);

        if (socio == null) {
            System.out.println("Socio no encontrado.");
        } else if (actividad == null) {
            System.out.println("Actividad no encontrada.");
        } else {
            boolean resultado = inscripcionService.inscribir(socio, actividad);
            System.out.println(resultado ? "Inscripción exitosa." : "No se pudo inscribir (edad o cupo).");
        }
    }

    private static void listarSocios() {
        System.out.println("---- Socios ----");
        for (Socio s : socioRepo.listar()) {
            System.out.println(s.getDni() + " - " + s.getNombre() + " (" + s.getEdad() + " años)");
        }
    }

    private static void listarActividades() {
        System.out.println("---- Actividades ----");
        for (Actividad a : actividadRepo.listar()) {
            System.out.println(a.getNombre() + " - " + a.getEncargado() + ", Cupo: " +
                               a.getCupo() + ", Edad mínima: " + a.getEdadMinima());
        }
    }
}

