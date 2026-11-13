import java.sql.ResultSet;
import java.sql.SQLException;

public class Admins extends Cuentas{

    public Admins(int id, String dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado) {
        super(id, dni, nombre, nombreUsuario, contrasenia, estaEliminado);
    }

    public Admins(ResultSet resultadoCuenta) throws SQLException {
        super(resultadoCuenta);
    }

}
