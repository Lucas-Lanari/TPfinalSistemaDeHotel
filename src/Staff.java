import java.sql.ResultSet;
import java.sql.SQLException;

public class Staff extends Cuentas{

    public Staff(int id, String dni, String nombre, String nombreUsuario, String contrasenia, boolean estaEliminado) {
        super(id, dni, nombre, nombreUsuario, contrasenia, estaEliminado);
    }

    public Staff(ResultSet resultadoCuenta) throws SQLException {
        super(resultadoCuenta);
    }

}
