package Clases;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ContenedorMapa<K, V> {

    protected Map<K, V> mapa;

    public ContenedorMapa() {
        mapa = new HashMap<>();
    }

    public void agregar(K clave, V valor) {
        mapa.put(clave, valor);
    }

    public V quitar(K clave) {
        return mapa.remove(clave);
    }

    public String listar() {
        StringBuilder respuesta = new StringBuilder();

        Iterator<Map.Entry<K, V>> it = mapa.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<K,V> entrada = it.next();
            V valor = entrada.getValue();
            respuesta.append(valor.toString());
            respuesta.append('\n');
        }

        return respuesta.toString();
    }
}
