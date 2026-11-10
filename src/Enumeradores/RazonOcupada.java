package Enumeradores;

public enum RazonOcupada {

    LIBRE("Libre"), LIMPIANDO("Limpiando"), REPARANDO("Reparando"), DESINFECTANDO("Desinfectando"), SIN("Sin_Especificar");

    private String motivo;

    RazonOcupada(String motivo) {
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
