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

        int idCuenta = resultadoCuenta.getInt("idCuenta");
        RolesCuenta rol = RolesCuenta.valueOf(resultadoCuenta.getString("rol"));

        String sqlDatosAdicionalesCLiente = "SELECT * FROM DatosAdicionalesClientes WHERE idCuenta = ?;";

        try (Connection conexion = crearConexion()) {

            // cargar datos adicionales para cada tipo de cuenta

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

    public static Cuentas buscarCuentaPorId(int idCuenta) throws SQLException {

        String sql = "SELECT * FROM Cuentas WHERE idCuenta = ?;";

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1,idCuenta);

            ResultSet resultadoEncontrado = ps.executeQuery();

            if (!resultadoEncontrado.next())
                return null;

            return cargarCuenta(resultadoEncontrado);

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

    public static Habitaciones buscarHabitacionPorId(int idHabitacion) throws SQLException {

        String sql = "SELECT * FROM Habitaciones WHERE idHabitacion = ?;";

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1,idHabitacion);

            ResultSet resultadoEncontrado = ps.executeQuery();

            if (!resultadoEncontrado.next())
                return null;

            return new Habitaciones(resultadoEncontrado);

        }

    }

}

/*
INSERT INTO cuentas (id, att1, att2)
VALUES (?, ?, ?)
ON CONFLICT(id) DO UPDATE SET
    att1 = excluded.att1,
    att2 = excluded.att2;
 */