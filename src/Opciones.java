public final class Opciones {


    private String nombreInterno;
    private String textoAMostrar;
    private Character teclaAPresionar;


    // para opciones que queda mejor si tienen una tecla especial
    public Opciones(String nombreInterno, String textoAMostrar, Character teclaAPresionar) {
        this.nombreInterno = nombreInterno;
        this.textoAMostrar = textoAMostrar;

        if (teclaAPresionar!=null)
            teclaAPresionar = Character.toUpperCase(teclaAPresionar);

        this.teclaAPresionar = teclaAPresionar;
    }

    // para opciones que no requieren una tecla especial
    public Opciones(String nombreInterno, String textoAMostrar) {
        this(nombreInterno, textoAMostrar, null);
    }

    // para el uso de equals() solamente
    public Opciones(String nombreInterno) {
        this(nombreInterno, null, null);
    }

    // para el uso de equals() solamente
    public Opciones(Character teclaAPresionar) {
        this(null, null, teclaAPresionar);
    }

    @Override
    public boolean equals(Object otro) {
        if (otro.getClass()!=this.getClass())
            return false;

        Opciones otroOp = (Opciones) otro;

        if (nombreInterno.equalsIgnoreCase(otroOp.getNombreInterno()))
            return true;

        if (teclaAPresionar!=null && otroOp.getTeclaAPresionar()!=null) {
            if (teclaAPresionar == otroOp.getTeclaAPresionar())
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return nombreInterno.hashCode();
    }

    // getters

    public String getNombreInterno() {
        return nombreInterno;
    }

    public String getTextoAMostrar() {
        return textoAMostrar;
    }

    public Character getTeclaAPresionar() {
        return teclaAPresionar;
    }

}
