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

            stmt.execute("""
                    
                    
                    """); // CREATE TABLE IF NOT EXISTS

            stmt.execute("""
                    
                    
                    """); // CREATE TABLE IF NOT EXISTS

            stmt.execute("""
                    
                    
                    """); // CREATE TABLE IF NOT EXISTS

            stmt.execute("""
                    
                    
                    """); // CREATE TABLE IF NOT EXISTS

            stmt.execute("""
                    
                    
                    """); // CREATE TABLE IF NOT EXISTS

        }
    }

    // CUENTAS

    private static Cuentas cargarCuenta(ResultSet resultadoCuenta) throws SQLException {

        Cuentas retorno = null;




        // codigo para cargar datos adicionales para cada tipo de cuenta

        int idCuenta = resultadoCuenta.getInt("idCuenta");
        RolesCuenta rol = RolesCuenta.valueOf(resultadoCuenta.getString("rol"));

        String sqlDatosAdicionalesCLiente = "SELECT * FROM DatosAdicionalesClientes WHERE idCuenta = ?;";

        try (Connection conexion = crearConexion()) {

            switch(rol) {
                case ADMINISTRADOR:
                    // FUNCIONALIDAD FUTURA

                    retorno = new Admins(resultadoCuenta);
                    break;
                case STAFF:
                    // FUNCIONALIDAD FUTURA

                    retorno = new Staff(resultadoCuenta);
                    break;
                case CLIENTE:
                    try (PreparedStatement ps = conexion.prepareStatement(sqlDatosAdicionalesCLiente)) {
                        ps.setInt(1,idCuenta);
                        ResultSet resultadoDatoAdicional = ps.executeQuery();

                        String paisOrigen = null;
                        String domicilioOrigen = null;

                        if (resultadoDatoAdicional.next()) {
                            retorno = new Clientes(resultadoCuenta,resultadoDatoAdicional);
                        } else {
                            retorno = new Clientes(resultadoCuenta);
                        }

                    }
                    break;


                default:
                    break;
            }
        }

        return retorno;
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

    public static String buscarCuentaPorId(int idCuenta) throws SQLException {

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

        }

    }

    // HABITACIONES

    public static ResultSet listarHabitaciones() throws SQLException {

        String sql = "SELECT * FROM Habitaciones";

        ResultSet resultados;

        try (Connection conexion = crearConexion();
             Statement stmt = conexion.createStatement()) {

            resultados = stmt.executeQuery(sql);

        }

        return resultados;
    }

    public static String buscarHabitacionPorId(int idHabitacion) throws SQLException {

        String sql = "SELECT * FROM Habitaciones WHERE idHabitacion = ?;";

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1,idHabitacion);

            ResultSet resultadoEncontrado = ps.executeQuery();

            if (!resultadoEncontrado.next())
                return null;

            String resultado = null; // CAMBIARLO A LA CLASE HABITACION

            // CODIGO PARA RECIBIR LOS DATOS DE HABITACION DESDE EL RESULTSET


            return resultado;

        }

    }

}
