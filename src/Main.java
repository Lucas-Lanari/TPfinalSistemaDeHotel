import Clases.Cuentas;
import Conexiones.Conexion;
import Enumeradores.GuiEstados;
import Gestores.GestorCuentas;
import java.sql.SQLException;
import Gestores.GestorHotel;

public class Main {
    public static void main(String[] args) throws SQLException {

        GestorHotel gestorHotel;

        try {
            gestorHotel = new GestorHotel();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        try {
            Conexion.inicializar();

//            for (Cuentas c : Conexion.listarCuentas()) {
//                gestorHotel.crearCuenta(c);
//            }

            new Menu(gestorHotel).ejecutar();

        } catch (SQLException e) {
            System.out.println("Error con la base de datos: " + e.getMessage());
        }


    }
}