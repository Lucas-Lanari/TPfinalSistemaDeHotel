import java.util.ArrayList;

public final class MenuOpciones {

    // textoOpciones y teclaOpciones comparten el mismo index para cada opcion.

    private ArrayList<String> textoOpciones;
    private ArrayList<Character> teclaOpciones;

    private ArrayList<Character> teclasDisponibles;


    // EDITABLE - teclas que el usuario tendrá que presionar para cada opcion. Todas las letras deben estar en mayúsculas

    private final static String teclasUsables = "ABCDEFGHIJKLMNOPQRSTUVXWYZ123456789";

    // inicializa (o reinicializa) todos los valores del menu, por si el usuario quiere reutilizar la misma variable
    public void reiniciarMenu() {

        textoOpciones.clear();
        teclaOpciones.clear();

        // crear la lista de teclas disponibles (sacadas de teclasUsables)

        teclasDisponibles.clear();

        char teclas[] = teclasUsables.toUpperCase().toCharArray();

        for (int i=0;i<teclas.length;i++) {
            teclasDisponibles.add(teclas[i]);
        }
    }

    public MenuOpciones() {
        textoOpciones = new ArrayList<>();
        teclaOpciones = new ArrayList<>();

        teclasDisponibles = new ArrayList<>();

        reiniciarMenu();
    }

    // teclaNecesaria puede ser null.
    // Si teclaNecesaria es un carácter válido, entonces el usuario deberá tipear ese carácter hardcodeado en el código para seleccionar esa opción.
    // Si teclaNecesaria es null, entonces la tecla se generará automáticamente en tiempo de ejecución buscando en la lista de caracteres NO usados (teclasDisponibles).
    public void agregarOpcion(String textoOpcion, Character teclaNecesaria) {
        if (textoOpciones.contains(textoOpcion)) // si ya fue agregada, que no agregue de vuelta
            return;

        if (teclaNecesaria!=null) {

            teclaNecesaria = Character.toUpperCase(teclaNecesaria); // siempre mayúsculas!

            // si la tecla ya fue utilizada para otra opcion
            if (teclaOpciones.contains(teclaNecesaria)) {
                throw new TeclaYaUtilizadaException("La tecla ya fue utilizada por otra opción en este menu.");
            }

            // quitar la tecla de la lista de teclas disponibles
            teclasDisponibles.remove(teclaNecesaria);
        }

        textoOpciones.add(textoOpcion);
        teclaOpciones.add(teclaNecesaria);
    }

    public void agregarOpcion(String opcion) {
        agregarOpcion(opcion,null);
    }

    private ArrayList<Character> recibirMapeoTeclas() {

        ArrayList<Character> teclasMapeadas = new ArrayList<>();

        int idTeclaSiguiente = 0;

        for (Character tecla : teclaOpciones) {

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

        for (String opcion : textoOpciones) {
            out.append(teclasMapeadas.get(idx));
            out.append(": ");
            out.append(opcion);
            out.append('\n');
            idx++;
        }

        return out.toString();
    }

    public String encontrarOpcion(char teclaRecibida) {

        teclaRecibida = Character.toUpperCase(teclaRecibida);

        ArrayList<Character> teclasMapeadas = recibirMapeoTeclas();


        for (int i=0;i<teclasMapeadas.size();i++) {
            if (teclasMapeadas.get(i) == teclaRecibida) {
                return textoOpciones.get(i);
            }
        }

        return null;
    }

}

/*
    MenuOpciones menu = new MenuOpciones();

    menu.agregarOpcion("hola");
    menu.agregarOpcion("holo");
    menu.agregarOpcion("salir",'X');

    System.out.println(menu.mostrarOpciones());
    System.out.println(menu.encontrarOpcion('b')); // retorna "holo"
    System.out.println(menu.encontrarOpcion('x')); // retorna "salir"
 */