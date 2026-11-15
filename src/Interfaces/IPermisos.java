package Interfaces;

public interface IPermisos {
    boolean puedeCrearCuenta();
    boolean puedeEliminarCuenta();
    boolean puedeCrearReserva();
    boolean puedeModificarReserva();
    boolean puedeCancelarReserva();
    boolean puedeHacerCheckInOut();
    boolean puedeHacerBackup();
}
