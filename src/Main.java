import Clases.Cuentas;
import Conexiones.Conexion;
import Enumeradores.GuiEstados;
import Gestores.GestorCuentas;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            Conexion.inicializar();

            GestorCuentas gestorCuentas = new GestorCuentas();
            for (Cuentas c : Conexion.listarCuentas()) {
                gestorCuentas.agregar(c);
            }

            new Menu(gestorCuentas).ejecutar();

        } catch (SQLException e) {
            System.out.println("Error con la base de datos: " + e.getMessage());
        }


    }
}