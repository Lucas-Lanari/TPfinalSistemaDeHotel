public class Pasajero extends Usuario{
    String nombre;
    int edad;
    String numeroDeTelefono;

    ///CONSTRUCTOR
    public Pasajero(String nombreUsuario, String contrasena, String nombre, int edad, String numeroDeTelefono) {
        super(nombreUsuario, contrasena);
        this.nombre = nombre;
        this.edad = edad;
        this.numeroDeTelefono = numeroDeTelefono;
    }

    ///SETTERS Y GETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    public void setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }
}
