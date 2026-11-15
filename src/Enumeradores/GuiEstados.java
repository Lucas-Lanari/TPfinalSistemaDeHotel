package Enumeradores;

public enum GuiEstados {
    INICIAR_SESION("Iniciar Sesión"),
    TERMINADO("Cerrando..."),
    MENU_PRINCIPAL("Menú Principal"),
    GESTION_RESERVAS("Reservas"),
    GESTION_HABITACIONES("Habitaciones"),
    GESTION_CUENTAS("Cuentas");

    private String nombreBonito;

    GuiEstados(String nombreBonito) {
        this.nombreBonito = nombreBonito;
    }

    public String getNombreBonito() {
        return nombreBonito;
    }
}
