import java.sql.*;

public abstract class Conexion {

    private final static String url = "";
    private final static String name = "";
    private final static String pass = "";

    private static Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(url,name,pass);
    }

    // SIEMPRE INICIALIZAR AL PRINCIPIO DEL MAIN
    public static void inicializar() throws SQLException {

        try (Connection conexion = crearConexion();
             Statement stmt = conexion.createStatement()) {

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS Cuentas(
                    idCuenta INT PRIMARY KEY,
                    nombreUsuario VARCHAR(50) UNIQUE,
                    nombre VARCHAR(50),
                    dni VARCHAR(9) UNIQUE,
                    contrasenia VARCHAR(50),
                    estaEliminado BOOLEAN,
                    rol VARCHAR(20)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS DatosAdicionalesClientes(
                    idCuenta INT PRIMARY KEY,
                    paisOrigen VARCHAR(50),
                    domicilioOrigen VARCHAR(50),
                    CONSTRAINT fk_idCuenta
                        FOREIGN KEY (idCuenta)
                        REFERENCES Cuentas(idCuenta)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS Habitaciones(
                    idHabitacion INT PRIMARY KEY,
                    piso INT,
                    numeroDepartamento INT,
                    razonOcupada VARCHAR(50)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS DniIntegrantes(
                    idDniIntegrantes INT,
                    dni VARCHAR(9),
                    CONSTRAINT pk_idDniIntegrantes_dni  PRIMARY KEY(idDniIntegrantes,dni)
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS Reserva(
                    idReserva INT PRIMARY KEY,
                    idHabitacion INT,
                    CONSTRAINT fk_idHabitacion
                        FOREIGN KEY (idHabitacion)
                        REFERENCES Habitaciones(idHabitacion),
                    idCuentaResponsable INT,
                    CONSTRAINT fk_idCuentaResponsable
                        FOREIGN KEY (idCuentaResponsable)
                        REFERENCES Cuentas(idCuenta),
                    idDniIntegrantes INT,
                    CONSTRAINT fk_idDniIntegrantes
                        FOREIGN KEY (idDniIntegrantes)
                        REFERENCES DniIntegrantes(idDniIntegrantes),
                    checkIn DATE,
                    checkOut DATE,
                    estadoReserva VARCHAR(50)
                    );
                    """);

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