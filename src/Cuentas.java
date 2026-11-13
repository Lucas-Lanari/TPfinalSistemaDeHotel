import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Cuentas {
    protected int id;
    protected String dni;
    protected String nombre;
    protected String nombreUsuario;
    protected String contrasenia;
    protected boolean estaEliminado;

    public Cuentas(int id, String dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.estaEliminado = estaEliminado;
    }

    public Cuentas(ResultSet resultadoCuenta) throws SQLException {
        this(
                resultadoCuenta.getInt("idCuenta"),
                resultadoCuenta.getString("dni"),
                resultadoCuenta.getString("nombre"),
                resultadoCuenta.getString("nombreUsuario"),
                resultadoCuenta.getString("contrasenia"),
                resultadoCuenta.getBoolean("estaEliminado")
        );
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public boolean getEstaEliminado() {
        return estaEliminado;
    }
}
