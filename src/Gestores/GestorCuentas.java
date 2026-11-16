package Gestores;

import Clases.Cuentas;
import Clases.Habitaciones;
import Conexiones.Conexion;
import Excepciones.*;

import java.sql.SQLException;
import java.util.HashSet;

public class GestorCuentas extends Contenedor<Cuentas> {

    private Cuentas cuentaActual; //para el logueo

    public GestorCuentas() throws SQLException {
        super();

        HashSet<Cuentas> cuentas = Conexion.listarCuentas();

        for (Cuentas cuenta : cuentas) {
            super.agregar(cuenta);
        }

    }

    public Cuentas buscarCuentaPorId(Integer id) {
        for (Cuentas actual: lista) {
            if (actual.getId().equals(id)) {
                return actual;
            }
        }

        return null;
    }

    public Cuentas buscarCuentaPorDni(String dni) {
        for (Cuentas actual: lista) {
            if (actual.getDni().equals(dni)) {
                return actual;
            }
        }

        return null;
    }

    public Cuentas buscarCuentaPorUsuario(String nombreUsuario) {
        for (Cuentas actual: lista) {
            if (actual.getNombreUsuario().equals(nombreUsuario)) {
                return actual;
            }
        }

        return null;
    }

    private boolean existeDniActivo(String dni) {
        for (Cuentas actual : lista) {
            if (!actual.getEstaEliminado() && actual.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }


    private boolean existeUsuarioActivo(String usuario) {
        for (Cuentas actual : lista) {
            if (!actual.getEstaEliminado() && actual.getNombreUsuario().equals(usuario)) {
                return true;
            }
        }
        return false;
    }


    public boolean crearCuenta(Cuentas nueva) throws DniExistenteException, UsuarioExistenteException {

        if (existeDniActivo(nueva.getDni())) {
            throw new DniExistenteException("El DNI ya está registrado.");
        }

        if (existeUsuarioActivo(nueva.getNombreUsuario())) {
            throw new UsuarioExistenteException("El nombre de usuario ya está registrado.");
        }

        if (!Conexion.crearCuenta(nueva))
            return false;


        agregar(nueva);
        return true;
    }

    public boolean modificarNombre(Integer id, String nombre) {
        for (Cuentas actual: lista) {
            if (actual.getId().equals(id)) {
                actual.setNombre(nombre);
                return true;
            }
        }

        return false;

    }

    public boolean modificarNombreUsuario(Integer id, String nombreUsuario) {
        for (Cuentas actual : lista) {
            if (actual.getId().equals(id)) {
                actual.setNombreUsuario(nombreUsuario);
                return true;
            }
        }
        return false;
    }

    public boolean modificarDni(Integer id, String dni) {
        for (Cuentas actual: lista) {
            if (actual.getId().equals(id)) {
                actual.setDni(dni);
                return true;
            }
        }

        return false;
    }

    public boolean modificarContrasenia(Integer id, String contrasenia) {
        for (Cuentas actual: lista) {
            if (actual.getId().equals(id)) {
                actual.setContrasenia(contrasenia);
                return true;
            }
        }

        return false;
    }

    public boolean modificarEstadoEliminado(Integer id, boolean nuevoEstado) {
        for (Cuentas actual: lista) {
            if (actual.getId().equals(id)) {
                actual.setEstaEliminado(nuevoEstado);
                return true;
            }
        }

        return false;
    }



    public Cuentas login(String nombreUsuario, String contrasenia) throws ErrorLoginException {
        Cuentas cuenta = buscarCuentaPorUsuario(nombreUsuario);

        if (cuenta == null) {
            throw new UsuarioInexistenteException("El usuario no existe.");
        }
        if (!cuenta.getContrasenia().equals(contrasenia)) {
            throw new ContraseniaIncorrectaException("Contraseña incorrecta.");
        }
        if (cuenta.getEstaEliminado()) {
            throw new UsuarioEliminadoException("El usuario fue eliminado.");
        }

        cuentaActual = cuenta;
        return cuenta;
    }

    public void logout(){
        cuentaActual=null;
    }

    public Cuentas getCuentaActual(){
        return cuentaActual;
    }


}
