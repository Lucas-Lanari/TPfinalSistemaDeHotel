import java.util.ArrayList;

public final class MenuOpciones {

    // textoOpciones y teclaOpciones comparten el mismo index para cada opcion.

    private ArrayList<String> textoOpciones;
    private ArrayList<Character> teclaOpciones;

    private ArrayList<Character> teclasDisponibles;


    // EDITABLE - teclas que el usuario tendrá que presionar para cada opcion. todas las letras deben estar en mayusculas

    private final static String teclasUsables = "ABCDEFGHIJKLMNOPQRSTUVXWYZ123456789";

    // inicializa (o re-inicializa) todos los valores del menu, por si el usuario quiere reutilizar la misma variable
    public void reiniciarMenu() {

        textoOpciones.clear();
        teclaOpciones.clear();

        // crear la lista de teclas disponibles (sacadas de teclasUsables)

        teclasDisponibles.clear();

        char letras[] = teclasUsables.toUpperCase().toCharArray();

        for (int i=0;i<letras.length;i++) {
            teclasDisponibles.add(letras[i]);
        }
    }

    public MenuOpciones() {
        textoOpciones = new ArrayList<>();
        teclaOpciones = new ArrayList<>();

        teclasDisponibles = new ArrayList<>();

        reiniciarMenu();
    }

    // teclaNecesaria puede ser null.
    // si teclaNecesaria es un caracter valido, entonces el usuario deberá tipear ese caracter hardcodeado en el codigo para seleccionar esa opcion.
    // si teclaNecesaria es null, entonces la letra se generará automaticamente en tiempo de ejecucion buscando en la lista de caracteres NO usados (teclasDisponibles).
    public void agregarOpcion(String textoOpcion, Character teclaNecesaria) {
        if (textoOpciones.contains(textoOpcion)) // si ya fue agregada, que no agregue de vuelta
            return;

        if (teclaNecesaria!=null) {

            teclaNecesaria = Character.toUpperCase(teclaNecesaria); // siempre mayusculas!

            // si la tecla ya fue utilizada para otra opcion
            if (teclaOpciones.contains(teclaNecesaria)) {
                // TODO: tirar exception que la letra ya fue utilizada
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

        for (Character letra : teclaOpciones) {

            if (letra!=null) {
                teclasMapeadas.add(letra);
            } else {

                Character teclaEncontrada = null;

                try {
                    teclaEncontrada = teclasDisponibles.get(idTeclaSiguiente);
                    idTeclaSiguiente++;

                } catch (IndexOutOfBoundsException e) {
                    // TODO: tirar excepcion que no quedaron mas letras para todas las opciones
                }

                teclasMapeadas.add(teclaEncontrada);

            }

        }

        return teclasMapeadas;
    }

    public String mostrarOpciones() {

        ArrayList<Character> letrasMapeadas = recibirMapeoTeclas();

        StringBuilder out = new StringBuilder();

        int idx = 0;

        for (String opcion : textoOpciones) {
            out.append(letrasMapeadas.get(idx));
            out.append(": ");
            out.append(opcion);
            out.append('\n');
            idx++;
        }

        return out.toString();
    }

    public String encontrarOpcion(char letraRecibida) {

        letraRecibida = Character.toUpperCase(letraRecibida);

        ArrayList<Character> letrasMapeadas = recibirMapeoTeclas();


        for (int i=0;i<letrasMapeadas.size();i++) {
            if (letrasMapeadas.get(i) == letraRecibida) {
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