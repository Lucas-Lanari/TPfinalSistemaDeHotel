void main() {

    Scanner scanner = new Scanner(System.in);

    GuiEstados estado = GuiEstados.INICIAR_SESION;

    while (estado != GuiEstados.TERMINADO) {

        System.out.println(estado.getNombreBonito());

        MenuOpciones menu = new MenuOpciones();

        // código para mostrar los mensajes y agregar opciones de menu

        switch (estado) {

            case INICIAR_SESION:
                break;

            // ...

            default:
                break;
        }

        System.out.println(menu.mostrarOpciones());

        System.out.print("? ");


        Character accion;

        try {
            accion = scanner.nextLine().charAt(0);
        } catch (IndexOutOfBoundsException e) {
            accion = '-';
        }

        // código para recibir la acción del usuario

        switch (estado) {

            case INICIAR_SESION:
                break;

            default:
                break;
        }








    }
}
