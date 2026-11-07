import java.util.ArrayList;
import java.util.LinkedHashSet;

public final class MenuOpciones {

    private LinkedHashSet<Opciones> opciones; // ordenado por orden de agregación y no permite repeticiones

    private ArrayList<Character> teclasDisponibles;


    // EDITABLE - teclas que el usuario tendrá que presionar para cada opcion. Todas las letras deben estar en mayúsculas

    private final static String teclasUsables = "ABCDEFGHIJKLMNOPQRSTUVXWYZ123456789";

    // inicializa (o reinicializa) todos los valores del menu, por si el usuario quiere reutilizar la misma variable
    public void reiniciarMenu() {

        opciones.clear();

        // crear la lista de teclas disponibles (sacadas de teclasUsables)

        teclasDisponibles.clear();

        char teclas[] = teclasUsables.toUpperCase().toCharArray();

        for (int i=0;i<teclas.length;i++) {
            teclasDisponibles.add(teclas[i]);
        }
    }

    public MenuOpciones() {
        opciones = new LinkedHashSet<>();

        teclasDisponibles = new ArrayList<>();

        reiniciarMenu();
    }

    // teclaNecesaria puede ser null.
    // Si teclaNecesaria es un carácter válido, entonces el usuario deberá tipear ese carácter hardcodeado en el código para seleccionar esa opción.
    // Si teclaNecesaria es null, entonces la tecla se generará automáticamente en tiempo de ejecución buscando en la lista de caracteres NO usados (teclasDisponibles).
    public void agregarOpcion(String textoOpcion, String nombreInternoOpcion, Character teclaNecesaria) {
        if (opciones.contains(new Opciones(nombreInternoOpcion))) // si ya fue agregada, que no agregue de vuelta
            return;

        if (teclaNecesaria!=null) {

            teclaNecesaria = Character.toUpperCase(teclaNecesaria); // siempre mayúsculas!

            // si la tecla ya fue utilizada para otra opcion
            if (opciones.contains(new Opciones(teclaNecesaria))) {
                throw new TeclaYaUtilizadaException("La tecla ya fue utilizada por otra opción en este menu.");
            }

            // quitar la tecla de la lista de teclas disponibles
            teclasDisponibles.remove(teclaNecesaria);
        }

        opciones.add(new Opciones(nombreInternoOpcion, textoOpcion, teclaNecesaria));
    }

    public void agregarOpcion(String textoOpcion, String nombreInternoOpcion) {
        agregarOpcion(textoOpcion, nombreInternoOpcion,null);
    }

    private ArrayList<Character> recibirMapeoTeclas() {

        ArrayList<Character> teclasMapeadas = new ArrayList<>();

        int idTeclaSiguiente = 0;

        for (Opciones opcion : opciones) {

            Character tecla = opcion.getTeclaAPresionar();

            if (tecla!=null) {
                teclasMapeadas.add(tecla);
            } else {

                Character teclaEncontrada = null;

                try {
                    teclaEncontrada = teclasDisponibles.get(idTeclaSiguiente);
                    idTeclaSiguiente++;

                } catch (IndexOutOfBoundsException e) {
                    throw new SobrecargaTeclasException("Demasiadas opciones para el menu (quedó sin teclas para que el usuario presione)");
                }

                teclasMapeadas.add(teclaEncontrada);

            }

        }

        return teclasMapeadas;
    }

    public String mostrarOpciones() {

        ArrayList<Character> teclasMapeadas = recibirMapeoTeclas();

        StringBuilder out = new StringBuilder();

        int idx = 0;
        for (Opciones opcion : opciones) {
            out.append(teclasMapeadas.get(idx));
            out.append(": ");
            out.append(opcion.getTextoAMostrar());
            out.append('\n');

            idx++;
        }

        return out.toString();
    }

    public String encontrarOpcion(char teclaRecibida) {

        teclaRecibida = Character.toUpperCase(teclaRecibida);

        ArrayList<Character> teclasMapeadas = recibirMapeoTeclas();

        int idx = 0;
        for (Opciones opcion : opciones) {
            if (teclaRecibida == teclasMapeadas.get(idx))
                return opcion.getNombreInterno();

            idx++;
        }

        return null;
    }

}

/*
    MenuOpciones menu = new MenuOpciones();

    menu.agregarOpcion("hola que tal", "hola");
    menu.agregarOpcion("holo? No, es hola!", "holo");
    menu.agregarOpcion("Salir", "salir", 'X');

    System.out.println(menu.mostrarOpciones());
    System.out.println(menu.encontrarOpcion('b')); // retorna "holo"
    System.out.println(menu.encontrarOpcion('x')); // retorna "salir"
 */