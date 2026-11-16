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
    protected void asignarPermisos() {
        permisoManejarReservas = true;
        permisoManejarHabitaciones = true;
        permisoManejarCuentas = true;
        permisoVerHabitaciones = true;
        permisoCrearCuentas = false;
        permisoCrearHabitaciones = false;
        permisoHacerBackup = false;
    }


}
