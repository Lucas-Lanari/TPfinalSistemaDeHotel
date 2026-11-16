package Enumeradores;

public enum RazonesOcupada {
    LIBRE("Libre"), OCUPADA("Ocupada"), LIMPIANDO("Limpiando"), REPARANDO("Reparando"), DESINFECTANDO("Desinfectando"), SIN("Sin_Especificar");

    private String motivo;

    RazonesOcupada(String motivo) {
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }}
