import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;

public class Reservas {
    private int id;
    private String habitacion;
    HashSet<String> dniIntegrantes;
    private Date checkIn;
    private Date checkOut;
    private EstadoReserva estado;

    public Reservas(int id, String habitacion, Date checkIn, Date checkOut, EstadoReserva estado) {
        this.id = id;
        this.habitacion = habitacion;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.estado = estado;
        dniIntegrantes = new HashSet<>();
    }

    public Reservas(ResultSet resultado) throws SQLException {
        this(resultado.getInt(1),resultado.getString(2),resultado.getDate(4),resultado.getDate(5),EstadoReserva.valueOf(resultado.getString(6)));
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

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
