public enum RolesCuenta {
    ADMINISTRADOR("Administrador"),STAFF("Staff"),CLIENTE("Cliente");

    private final String nombreBonito;

    RolesCuenta(String nombreBonito) {
        this.nombreBonito = nombreBonito;
    }

    public String getNombreBonito() {
        return nombreBonito;
    }
}
