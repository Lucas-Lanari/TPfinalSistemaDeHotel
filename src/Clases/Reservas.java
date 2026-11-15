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
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private EstadoReserva estado;

    public Reservas(Integer id, int idHabitacion, int idCuentaResponsable, LocalDateTime checkIn, LocalDateTime checkOut, EstadoReserva estado) {
        this.id = id;
        this.idHabitacion = idHabitacion;
        this.idCuentaResponsable = idCuentaResponsable;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.estado = estado;
        dniIntegrantes = new HashSet<>();
    }

    public Reservas(ResultSet resultado) throws SQLException {
        this(resultado.getInt("idReserva"),resultado.getInt("idHabitacion"),resultado.getInt("idCuentaResponsable"),resultado.getObject("checkIn", LocalDateTime.class),resultado.getObject("checkOut", LocalDateTime.class),EstadoReserva.valueOf(resultado.getString("estadoReserva")));
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

    public void agregarDniIntegrante(String dni) {
        dniIntegrantes.add(dni);
    }

    public void quitarDniIntegrante(String dni) {
        dniIntegrantes.remove(dni);
    }

    public HashSet<String> listarDniIntegrantes() {
        return dniIntegrantes;
    }
}
