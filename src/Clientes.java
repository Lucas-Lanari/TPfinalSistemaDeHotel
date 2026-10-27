public class Clientes extends Cuentas{
    private String paisOrigen;
    private String domicilioOrigen;

    public Clientes(int id, int dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado, String paisOrigen, String domicilioOrigen) {
        super(id, dni, nombre, nombreUsuario, contrasenia, estaEliminado);
        this.paisOrigen = paisOrigen;
        this.domicilioOrigen = domicilioOrigen;
    }

    ///GETTERS Y SETTERS
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
