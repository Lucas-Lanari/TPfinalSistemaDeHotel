public class Habitaciones {
    private int id;
    private int piso;
    private int numeroDepartamento;
    private RazonesOcupada ocupacion;

    public Habitaciones(int id, int piso, int numeroDepartamento, RazonesOcupada ocupacion) {
        this.id = id;
        this.piso = piso;
        this.numeroDepartamento = numeroDepartamento;
        this.ocupacion = ocupacion;
    }


    ///GETTERS Y SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroDepartamento() {
        return numeroDepartamento;
    }

    public void setNumeroDepartamento(int numeroDepartamento) {
        this.numeroDepartamento = numeroDepartamento;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public RazonesOcupada getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(RazonesOcupada ocupacion) {
        this.ocupacion = ocupacion;
    }
}
