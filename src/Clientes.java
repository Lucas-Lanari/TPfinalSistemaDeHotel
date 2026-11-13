import java.sql.ResultSet;
import java.sql.SQLException;

public class Clientes extends Cuentas{
    private String paisOrigen;
    private String domicilioOrigen;

    public Clientes(int id, String dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado, String paisOrigen, String domicilioOrigen) {
        super(id, dni, nombre, nombreUsuario, contrasenia, estaEliminado);

        this.paisOrigen = paisOrigen;
        this.domicilioOrigen = domicilioOrigen;
    }

    public Clientes(ResultSet resultadoCuenta, ResultSet resultadoDatosAdicional) throws SQLException {
        super(resultadoCuenta);

        this.paisOrigen = resultadoDatosAdicional.getString("paisOrigen");
        this.domicilioOrigen = resultadoDatosAdicional.getString("domicilioOrigen");
    }

    public Clientes(ResultSet resultadoCuenta) throws SQLException {
        super(resultadoCuenta);

        this.paisOrigen = null;
        this.domicilioOrigen = null;
    }


    ///GETTERS (sin setters: read-only)
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
