package Clases;

import Enumeradores.RolesCuenta;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admins extends Cuentas {

    public Admins(Integer id, String dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado) {
        super(id, dni, nombre, nombreUsuario, contrasenia, estaEliminado);

        permisoManejarReservas=true;
        permisoManejarCuentas=true;
        permisoVerHabitaciones = true;
        permisoManejarHabitaciones = true;
        permisoCrearCuentas = true;
        permisoCrearHabitaciones=true;
        permisoHacerBackup=true;

    }

    public Admins(ResultSet resultadoCuenta) throws SQLException {
        super(resultadoCuenta);
    }

    @Override
    public boolean isPermisoCrearHabitaciones() {
        return true;
    }

    @Override
    protected void asignarPermisos() {
        permisoManejarReservas = true;
        permisoManejarHabitaciones = true;
        permisoManejarCuentas = true;
        permisoVerHabitaciones = true;
        permisoCrearCuentas = true;
        permisoCrearHabitaciones = true;
        permisoHacerBackup = true;
    }

    @Override
    public String getRol() {
        return RolesCuenta.ADMINISTRADOR.name();
    }

    @Override
    public boolean isPermisoCrearCuentas() {
        return super.isPermisoCrearCuentas();
    }

    @Override
    public boolean isPermisoManejarCuentas() {
        return super.isPermisoManejarCuentas();
    }

    @Override
    public boolean isPermisoManejarHabitaciones() {
        return super.isPermisoManejarHabitaciones();
    }

    @Override
    public boolean isPermisoManejarReservas() {
        return super.isPermisoManejarReservas();
    }

    @Override
    public boolean isPermisoVerHabitaciones() {
        return super.isPermisoVerHabitaciones();
    }
}
