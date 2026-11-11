public enum GuiEstados {
    INICIAR_SESION("Iniciar Sesión"),
    TERMINADO("Cerrando...");


    private String nombreBonito;

    GuiEstados(String nombreBonito) {
        this.nombreBonito = nombreBonito;
    }

    public String getNombreBonito() {
        return nombreBonito;
    }
}
