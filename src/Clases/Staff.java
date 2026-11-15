package Clases;

import Enumeradores.RolesCuenta;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Staff extends Cuentas {

    public Staff(int id, String dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado) {
        super(id, dni, nombre, nombreUsuario, contrasenia, estaEliminado);
    }

    public Staff(ResultSet resultadoCuenta) throws SQLException {
        super(resultadoCuenta);
    }

    @Override
    public String getRol() {
        return RolesCuenta.STAFF.name();
    }

    @Override
    public boolean puedeCrearCuenta() {
        return false;
    }

    @Override
    public boolean puedeEliminarCuenta() {
        return false;
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
        return false;
    }

    @Override
    public boolean puedeVerReservas() {
        return true;
    }

}
