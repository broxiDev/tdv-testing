package tdv.teclasunidos.entities;

import java.time.LocalDateTime;

public class Reserva {
    private Recurso recurso;
    private Socio socio;
    private LocalDateTime inicio;
    private LocalDateTime fin;

    public Reserva(Recurso recurso, Socio socio, LocalDateTime inicio, LocalDateTime fin) {
        this.recurso = recurso;
        this.socio = socio;
        this.inicio = inicio;
        this.fin = fin;
    }

    public boolean seSuperpone(LocalDateTime otroInicio, LocalDateTime otroFin) {
        return inicio.isBefore(otroFin) && otroInicio.isBefore(fin);
    }

    public Recurso getRecurso() { return recurso; }
    public Socio getSocio() { return socio; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFin() { return fin; }
}
