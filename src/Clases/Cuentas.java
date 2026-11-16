package Clases;

import Interfaces.IPermisos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class Cuentas implements Comparable<Cuentas>, IPermisos {
    protected Integer id;
    protected String dni;
    protected String nombre;
    protected String nombreUsuario;
    protected String contrasenia;
    protected boolean estaEliminado;

    protected boolean permisoCrearCuenta;
    protected boolean permisoEliminarCuenta;
    protected boolean permisoCrearReserva;
    protected boolean permisoModificarReserva;
    protected boolean permisoCancelarReserva;
    protected boolean permisoCrearHabitacion;
    protected boolean permisoEliminarHabitacion;

    public Cuentas(Integer id, String dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.estaEliminado = estaEliminado;
    }

    public Cuentas(ResultSet resultadoCuenta) throws SQLException {
        this(
                resultadoCuenta.getInt("idCuenta"),
                resultadoCuenta.getString("dni"),
                resultadoCuenta.getString("nombre"),
                resultadoCuenta.getString("nombreUsuario"),
                resultadoCuenta.getString("contrasenia"),
                resultadoCuenta.getBoolean("estaEliminado")
        );
    }

    public Integer getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public boolean getEstaEliminado() {
        return estaEliminado;
    }

    public abstract String getRol();


    public void setId(int id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setEstaEliminado(boolean estaEliminado) {
        this.estaEliminado = estaEliminado;
    }

    public boolean isPermisoCrearCuenta() {
        return permisoCrearCuenta;
    }

    public boolean isPermisoEliminarCuenta() {
        return permisoEliminarCuenta;
    }

    public boolean isPermisoCrearReserva() {
        return permisoCrearReserva;
    }

    public boolean isPermisoModificarReserva() {
        return permisoModificarReserva;
    }

    public boolean isPermisoCancelarReserva() {
        return permisoCancelarReserva;
    }

    public boolean isPermisoCrearHabitacion() {
        return permisoCrearHabitacion;
    }

    public void setPermisoCrearHabitacion(boolean permisoCrearHabitacion) {
        this.permisoCrearHabitacion = permisoCrearHabitacion;
    }

    public boolean isPermisoEliminarHabitacion() {
        return permisoEliminarHabitacion;
    }

    public void setPermisoEliminarHabitacion(boolean permisoEliminarHabitacion) {
        this.permisoEliminarHabitacion = permisoEliminarHabitacion;
    }

    @Override
    public String toString() {
        return "id: "+ id +
                "\ndni: "+ dni +
                "\nnombre: "+ nombre +
                "\nnombre usuario: "+ nombreUsuario +
                "\ncontrasenia: "+ contrasenia +
                "\nestado eliminado: "+ estaEliminado +"\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cuentas))
            return false;

        Cuentas cuentaObj = (Cuentas) obj;
        return this.id.equals(cuentaObj.id);
    }

    @Override
    public int compareTo(Cuentas c) {
        return this.id.compareTo(c.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
