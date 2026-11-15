package Enumeradores;

public enum GuiEstados {
    INICIAR_SESION("Iniciar Sesión"),
    TERMINADO("Cerrando..."),
    MENU_CLIENTE("Menú de Cliente"),
    MENU_ADMIN("Menú de Administrador"),
    MENU_STAFF("Menú de Staff"),
    GESTION_RESERVAS("Reservas"),
    GESTION_HABITACIONES("Habitaciones");

    private String nombreBonito;

    GuiEstados(String nombreBonito) {
        this.nombreBonito = nombreBonito;
    }

    public String getNombreBonito() {
        return nombreBonito;
    }
}
