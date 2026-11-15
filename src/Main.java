import Clases.Cuentas;
import Conexiones.Conexion;
import Enumeradores.GuiEstados;
import Gestores.GestorCuentas;
import java.sql.SQLException;
import Gestores.GestorHotel;

public class Main {
    public static void main(String[] args) {

        GestorHotel gestorHotel = new GestorHotel();

        try {
            Conexion.inicializar();

            for (Cuentas c : Conexion.listarCuentas()) {
                gestorHotel.agregarCuenta(c);
            }

            new Menu(gestorHotel).ejecutar();

        } catch (SQLException e) {
            System.out.println("Error con la base de datos: " + e.getMessage());
        }


    }
}