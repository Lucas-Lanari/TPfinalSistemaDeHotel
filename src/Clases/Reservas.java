package Clases;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import Enumeradores.EstadoReserva;
import java.time.LocalDateTime;
import java.util.HashSet;


public class Reservas {
    private Integer id;
    private int idHabitacion;
    private int idCuentaResponsable;
    public HashSet<String> dniIntegrantes;
    private Date checkIn;
    private Date checkOut;
    private EstadoReserva estado;

    public Reservas(Integer id, int idHabitacion, int idCuentaResponsable, Date checkIn, Date checkOut, EstadoReserva estado) {
        this.id = id;
        this.idHabitacion = idHabitacion;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.estado = estado;
        dniIntegrantes = new HashSet<>();
    }

    public Reservas(ResultSet resultado) throws SQLException {
        this(resultado.getInt("idReserva"),resultado.getInt("idHabitacion"),resultado.getInt("idCuentaResponsable"),resultado.getDate("checkIn"),resultado.getDate("checkOut"),EstadoReserva.valueOf(resultado.getString("estadoReserva")));
    }

    ///GETTERS Y SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getIdCuentaResponsable() {
        return idCuentaResponsable;
    }

    public void setIdCuentaResponsable(int idCuentaResponsable) {
        this.idCuentaResponsable = idCuentaResponsable;
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

    public void agregarDniIntegrante(String dni) {
        dniIntegrantes.add(dni);
    }

    public void quitarDniIntegrante(String dni) {
        dniIntegrantes.remove(dni);
    }

    public HashSet<String> listarDniIntegrantes() {
        return dniIntegrantes;
    }

    public String listarIntegrantes() {
        StringBuilder integrantes = new StringBuilder();

        for(String dni : dniIntegrantes) {
            integrantes.append(dni);
            integrantes.append("\n");
        }

        return integrantes.toString();
    }

    @Override
    public String toString() {
        return "Reserva:\n"+
                "id reserva: "+ id +
                "\nid habitacion: "+ idHabitacion +
                "\nid CuentaResponsable: "+ idCuentaResponsable +
                "\n"+ this.listarDniIntegrantes() +
                "\nFecha check in: "+ checkIn +
                "\nFecha check out: "+ checkOut +
                "\nEstado de la reserva: "+ estado + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Reservas))
            return false;

        Reservas objReserva = (Reservas) obj;
        return this.id.equals(objReserva.id);
    }
}
