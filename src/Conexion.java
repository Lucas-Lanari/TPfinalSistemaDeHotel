import java.sql.*;

public abstract class Conexion {

    private final static String url = "";
    private final static String name = "";
    private final static String pass = "";

    private static Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(url,name,pass);
    }

    public static void inicializar() throws SQLException {

        try (Connection conexion = crearConexion();
             Statement stmt = conexion.createStatement()) {

            stmt.execute(""); // CREATE TABLE IF NOT EXISTS

            stmt.execute(""); // CREATE TABLE IF NOT EXISTS

            stmt.execute(""); // CREATE TABLE IF NOT EXISTS

            stmt.execute(""); // CREATE TABLE IF NOT EXISTS

        }
    }

    public static ResultSet listarCuentas() throws SQLException {

        String sql = "SELECT * FROM Cuentas";

        ResultSet resultados;

        try (Connection conexion = crearConexion();
             Statement stmt = conexion.createStatement()) {

            resultados = stmt.executeQuery(sql);

        }

        return resultados;
    }

    public static String buscarCuentaPorId(int idCuenta) {

        String sql = "SELECT * FROM Cuentas WHERE idCuenta = ?;";

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1,idCuenta);

            ResultSet resultadoEncontrado = ps.executeQuery();

            if (!resultadoEncontrado.next())
                return null;

            String resultado = null; // CAMBIARLO A LA CLASE CUENTA

            // CODIGO PARA RECIBIR LOS DATOS DE CUENTA DESDE EL RESULTSET


            return resultado;

        } catch (SQLException e) {
            return null;
        }

    }

    public static String recibirDatosAdicionalesCliente(int idCuenta) {

        String sql = "SELECT * FROM Clientes WHERE idCuenta = ?;";

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1,idCuenta);

            ResultSet resultadoEncontrado = ps.executeQuery();

            if (!resultadoEncontrado.next())
                return null;

            String resultado = null; // CAMBIARLO A LA CLASE CUENTA

            // CODIGO PARA RECIBIR LOS DATOS DE CLIENTE DESDE EL RESULTSET


            return resultado;

        } catch (SQLException e) {
            return null;
        }

    }


}
