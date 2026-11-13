import java.time.LocalDateTime;
import java.util.HashSet;

public class Reservas {
    private int id;
    private String habitacion;
    HashSet<String> dniIntegrantes;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private EstadoReserva estado;

    public Reservas(int id, String habitacion, LocalDateTime checkIn, LocalDateTime checkOut, EstadoReserva estado) {
        this.id = id;
        this.habitacion = habitacion;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.estado = estado;
        dniIntegrantes = new HashSet<>();
    }

    ///GETTERS Y SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
