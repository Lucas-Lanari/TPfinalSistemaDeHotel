package Clases;

import Enumeradores.RolesCuenta;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admins extends Cuentas {

    public Admins(int id, String dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado) {
        super(id, dni, nombre, nombreUsuario, contrasenia, estaEliminado);

        permisoManejarReservas=true;
        permisoCrearCuenta=true;
        permisoEliminarCuenta=true;
        permisoVerHabitaciones = true;

    }

    public Admins(ResultSet resultadoCuenta) throws SQLException {
        super(resultadoCuenta);
    }

    @Override
    public String getRol() {
        return RolesCuenta.ADMINISTRADOR.name();
    }

    @Override
    public boolean puedeCrearCuenta() {
        return true;
    }

    @Override
    public boolean puedeEliminarCuenta() {
        return true;
    }

    @Override
    public boolean puedeCrearReserva() {
        return true;
    }

    @Override
    public boolean puedeModificarReserva() {
        return true;
    }

    @Override
    public boolean puedeCancelarReserva() {
        return true;
    }

    @Override
    public boolean puedeHacerCheckInOut() {
        return true;
    }

    @Override
    public boolean puedeHacerBackup() {
        return true;
    }

    @Override
    public boolean puedeVerReservas() {
        return true;
    }

}
