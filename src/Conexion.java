import Clases.Reserva;

import java.sql.*;
import java.util.HashSet;

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

    public static Cuentas cargarCuenta(ResultSet resultadoCuenta) throws SQLException {

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

    public static ResultSet listarCuentasEliminadas() throws SQLException {

        String sql = "SELECT * FROM Cuentas WHERE estaEliminado = TRUE  ";

        ResultSet resultados;

        try (Connection conexion = crearConexion();
             Statement stmt = conexion.createStatement()) {

            resultados = stmt.executeQuery(sql);

        }

        return resultados;
    }

    public boolean crearCuenta(Cuentas cuenta) {
        if (cuenta.getId()!=null) // si ya fue agregado, fallar
            return false;

        String sqlGuardarCuenta = """
        INSERT IGNORE INTO cuentas
            (nombreUsuario, nombre, dni, contrasenia,
         estaEliminado, rol)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conexion = crearConexion()) {

            conexion.setAutoCommit(false);

            int idResultante;

            try {
                try (PreparedStatement ps = conexion.prepareStatement(sqlGuardarCuenta)) {

                    ps.setString(1, cuenta.getNombreUsuario());
                    ps.setString(2, cuenta.getNombre());
                    ps.setString(3, cuenta.getDni());
                    ps.setString(4, cuenta.getContrasenia());
                    ps.setBoolean(5, cuenta.getEstaEliminado());
                    ps.setString(6, cuenta.getRol());

                    if (ps.executeUpdate() == 0) {
                        conexion.rollback();
                        return false;
                    }

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            idResultante = rs.getInt(1);
                        } else {
                            conexion.rollback();
                            return false;
                        }
                    }

                }

                cuenta.setId(idResultante);

                if (cuenta instanceof Clientes cliente) {

                    String sqlGuardarDatoAdicional = """
                    INSERT IGNORE INTO DatosAdicionalesClientes
                    (idCuenta, paisOrigen, domicilioOrigen)
                    VALUES (?, ?, ?)
                    """;

                    try (PreparedStatement psDatoAdicional =
                                 conexion.prepareStatement(sqlGuardarDatoAdicional)) {

                        psDatoAdicional.setInt(1, cliente.getId());
                        psDatoAdicional.setString(2, cliente.getPaisOrigen());
                        psDatoAdicional.setString(3, cliente.getDomicilioOrigen());

                        if (psDatoAdicional.executeUpdate() == 0) {
                            conexion.rollback();
                            return false;
                        }
                    }


                } else if (cuenta instanceof Admins) {
                    // FUNCIONALIDAD FUTURA

                } else if (cuenta instanceof Staff) {
                    // FUNCIONALIDAD FUTURA

                } else {
                    conexion.rollback();
                    return false;
                }

                conexion.commit();
                return true;

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean actualizarCuenta(Cuentas cuenta) {
        if (cuenta.getId()==null)
            return false;

        String sqlActualizarCuenta = """
        UPDATE cuentas
        SET nombreUsuario = ?,
            nombre = ?,
            dni = ?,
            contrasenia = ?,
            estaEliminado = ?,
            rol = ?
        WHERE idCuenta = ?
        """;

        try (Connection conexion = crearConexion()) {

            conexion.setAutoCommit(false);

            try {
                try (PreparedStatement ps = conexion.prepareStatement(sqlActualizarCuenta)) {

                    ps.setString(1, cuenta.getNombreUsuario());
                    ps.setString(2, cuenta.getNombre());
                    ps.setString(3, cuenta.getDni());
                    ps.setString(4, cuenta.getContrasenia());
                    ps.setBoolean(5, cuenta.getEstaEliminado());
                    ps.setString(6, cuenta.getRol());
                    ps.setInt(7, cuenta.getId());

                    if (ps.executeUpdate() == 0) {
                        conexion.rollback();
                        return false;
                    }
                }

                if (cuenta instanceof Clientes cliente) {

                    String sqlActualizarDatoAdicional = """
                    INSERT INTO DatosAdicionalesClientes
                    (idCuenta, paisOrigen, domicilioOrigen)
                    VALUES (?, ?, ?)
                    ON CONFLICT (idCuenta)
                    DO UPDATE SET
                        paisOrigen = EXCLUDED.paisOrigen,
                        domicilioOrigen = EXCLUDED.domicilioOrigen
                    """;

                    try (PreparedStatement psDatoAdicional =
                                 conexion.prepareStatement(sqlActualizarDatoAdicional)) {

                        psDatoAdicional.setInt(1, cliente.getId());
                        psDatoAdicional.setString(2, cliente.getPaisOrigen());
                        psDatoAdicional.setString(3, cliente.getDomicilioOrigen());

                        if (psDatoAdicional.executeUpdate() == 0) {
                            conexion.rollback();
                            return false;
                        }
                    }

                } else if (cuenta instanceof Admins) {
                    // FUNCIONALIDAD FUTURA

                } else if (cuenta instanceof Staff) {
                    // FUNCIONALIDAD FUTURA

                } else {
                    conexion.rollback();
                    return false;
                }

                conexion.commit();
                return true;

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean eliminarCuenta(Cuentas cuenta, boolean eliminado) {
        if (cuenta.getId()==null)
            return false;

        String sqlActualizarCuenta = """
        UPDATE cuentas
        SET estaEliminado = ?
        WHERE idCuenta = ?
        """;

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sqlActualizarCuenta)) {

                    ps.setBoolean(1, eliminado);
                    ps.setInt(2, cuenta.getId());

                    if (ps.executeUpdate() == 0) {
                        return false;
                    }

                    cuenta.setEstaEliminado(eliminado);
                    return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public static Cuentas buscarCuenta(int idCuenta) throws SQLException {

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

    public static Cuentas buscarCuentaPorNombre(String nombre) throws SQLException {

        String sql = "SELECT * FROM Cuentas WHERE nombreUsuario = ?;";

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1,nombre);

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

    public static ResultSet buscarHabitacionesPorPiso(int piso) throws SQLException {

        String sql = "SELECT * FROM Habitaciones WHERE piso = ?";

        ResultSet resultados;

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1,piso);

            resultados = ps.executeQuery(sql);

        }

        return resultados;
    }

    public static ResultSet buscarHabitacionesRazonOcupada(RazonesOcupada razon) throws SQLException {

        String sql = "SELECT * FROM Habitaciones WHERE razonOcupada = ?";

        ResultSet resultados;

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1,razon.name());

            resultados = ps.executeQuery(sql);

        }

        return resultados;
    }

    public boolean crearHabitacion(Habitaciones habitacion) {
        if (habitacion.getId()!=null) // si ya fue agregado, fallar
            return false;

        String sqlGuardarCuenta = """
        INSERT IGNORE INTO Habitaciones
            (piso, numeroDepartamento, razonOcupada)
        VALUES (?, ?, ?)
        """;

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sqlGuardarCuenta)) {

            int idResultante;

            ps.setInt(1, habitacion.getPiso());
            ps.setInt(2, habitacion.getNumeroDepartamento());
            ps.setString(3, habitacion.getOcupacion().name());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idResultante = rs.getInt(1);
                } else {
                    return false;
                }
            }

            habitacion.setId(idResultante);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean actualizarHabitacion(Habitaciones habitacion) {
        if (habitacion.getId()==null) // no fue  agregado, fallar
            return false;

        String sqlGuardarCuenta = """
        UPDATE HABITACIONES SET
            piso = ?, numeroDepartamento = ?, razonOcupada = ?
        WHERE idHabitacion = ?
        """;

        try (Connection conexion = crearConexion();
             PreparedStatement ps = conexion.prepareStatement(sqlGuardarCuenta)) {

            int idResultante;

            ps.setInt(1, habitacion.getPiso());
            ps.setInt(2, habitacion.getNumeroDepartamento());
            ps.setString(3, habitacion.getOcupacion().name());

            ps.setInt(4, habitacion.getId());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idResultante = rs.getInt(1);
                } else {
                    return false;
                }
            }

            habitacion.setId(idResultante);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean eliminarHabitacion(Habitaciones habitacion) {

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


    public static HashSet<Reservas> listarReservas() throws SQLException {

        String sql = "SELECT * FROM Reservas";
        String sqlListarDniIntegrantes = "SELECT * FROM DniIntegrantes WHERE idDniIntegrantes = ?";

        HashSet<Reservas> out = new HashSet<>();

        try (Connection conexion = crearConexion();
             Statement stmt = conexion.createStatement()) {

            ResultSet resultados = stmt.executeQuery(sql);

            while (resultados.next()) {

                Reservas reserva = new Reservas(resultados);

                try (PreparedStatement ps = conexion.prepareStatement(sqlListarDniIntegrantes)) {
                    ps.setInt(1,reserva.getId());

                    ResultSet resultadoDnis = ps.executeQuery();

                    while (resultadoDnis.next()) {
                        reserva.agregarDniIntegrante(resultadoDnis.getString("dni"));
                    }


                }

                out.add(reserva);
            }
        }

        return out;
    }






}

/*
INSERT INTO cuentas (id, att1, att2)
VALUES (?, ?, ?)
ON CONFLICT(id) DO UPDATE SET
    att1 = excluded.att1,
    att2 = excluded.att2;
 */