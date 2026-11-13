public enum EstadoReserva {
    TERMINADA("Terminada"), PENDIENTE("Pendiente"), ACTIVA("Activa");



    private String nombreBonito;

    EstadoReserva(String nombreBonito) {
        this.nombreBonito = nombreBonito;
    }

    public String getNombreBonito() {
        return nombreBonito;
    }
}
