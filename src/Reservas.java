import java.time.LocalDateTime;
import java.util.HashSet;

public class Reservas {
    private int id;
    private String habitacion;
    private HashSet<Clientes> integrantes = new HashSet<>();
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private EstadoReserva estado;

    public Reservas(int id, String habitacion, HashSet<Clientes> integrantes, LocalDateTime checkIn, LocalDateTime checkOut, EstadoReserva estado) {
        this.id = id;
        this.habitacion = habitacion;
        this.integrantes = integrantes;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.estado = estado;
    }

    ///GETTERS Y SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public HashSet<Clientes> getIntegrantes() {
        return integrantes;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setIntegrantes(HashSet<Clientes> integrantes) {
        this.integrantes = integrantes;
    }

    public String getHabitacion() {
        return habitacion;
    }
}
