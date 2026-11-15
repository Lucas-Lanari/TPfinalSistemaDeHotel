package Gestores;

import Clases.Cuentas;

public class GestorCuentas extends Contenedor<Cuentas> {

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
        for (Cuentas actual: lista) {
            if (actual.getId().equals(id)) {
                actual.setNombre(nombreUsuario);
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
            }
        }

        return false;
    }



}
