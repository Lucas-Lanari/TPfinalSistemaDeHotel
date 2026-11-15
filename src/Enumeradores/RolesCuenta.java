package Enumeradores;

public enum RolesCuenta {
    ADMINISTRADOR("Administrador"),STAFF("Clases.Staff"),CLIENTE("Cliente");

    private final String nombreBonito;

    RolesCuenta(String nombreBonito) {
        this.nombreBonito = nombreBonito;
    }

    public String getNombreBonito() {
        return nombreBonito;
    }
}
