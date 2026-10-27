public class Clientes {
    private int dni;
    private String nombre;
    private String paisOrigen;
    private String domicilioOrigen;

    public Clientes(int dni, String nombre, String paisOrigen, String domicilioOrigen) {
        this.dni = dni;
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
        this.domicilioOrigen = domicilioOrigen;
    }




    ///GETTERS Y SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getDni() {
        return dni;
    }

    public void setDomicilioOrigen(String domicilioOrigen) {
        this.domicilioOrigen = domicilioOrigen;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getDomicilioOrigen() {
        return domicilioOrigen;
    }
}
