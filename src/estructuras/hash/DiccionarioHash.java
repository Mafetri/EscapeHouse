/*
=================================================
|     Estructuras de Datos 2021                 |
=================================================
|      Clase:                                   |
|       > Diccionario Hash Abierto              |
|      Alumno:                                  |
|        > Manuel Felipe Triñanes (FAI-2738)    |
=================================================
*/

package estructuras.hash;
import estructuras.lista.Lista;

public class DiccionarioHash {
    private static final int TAMANIO = 20;
    private NodoHashDicc[] tabla;
    private int cant;

    public DiccionarioHash() {
        this.tabla = new NodoHashDicc[TAMANIO];
        cant = 0;
    }

    // ---- Insertar ----
    public boolean insertar(Object elem, Object dato) {
        int pos = funcionHash(elem);
        NodoHashDicc aux = this.tabla[pos];
        boolean encontrado = false;

        // Busco si existe en los nodos de la posicion hash de elem
        while (!encontrado && aux != null) {
            encontrado = aux.getElem().equals(elem);
            aux = aux.getEnlace();
        }

        // Si no lo encontro, entonces crea un nuevo nodo con elem y el enlace al otro
        // nodo que esta en la posicion
        if (!encontrado) {
            this.tabla[pos] = new NodoHashDicc(elem, dato, this.tabla[pos]);
            this.cant++;
        }

        return !encontrado;
    }

    // ---- Eliminar ----
    public boolean eliminar(Object elem) {
        int pos = funcionHash(elem);
        int posNodo = 0;
        NodoHashDicc aux = this.tabla[pos];
        boolean encontrado = false;

        // Busco el elem
        while (!encontrado && aux != null) {
            encontrado = aux.getElem().equals(elem);
            aux = aux.getEnlace();
            posNodo++;
        }
        
        // Si fue encontrado entonces lo elimino
        if (encontrado) {
            // Si el elemento esta en el primer nodo de la posicion de la tabla
            if (posNodo == 1) {
                // Y no tiene nodos anidados
                if (this.tabla[pos].getEnlace() == null) {
                    this.tabla[pos] = null;
                } else {
                    this.tabla[pos] = this.tabla[pos].getEnlace();
                }
            } else {
                aux = this.tabla[pos];
                for(int i = 1; i < posNodo; i++){
                    // Si el siguiente nodo es el nodo del elemento a eliminar
                    if(i+1 == posNodo){
                        // Entonces seteo el enlace del nodo alctual al enlace del elemento a eliminar
                        aux.setEnlace(aux.getEnlace().getEnlace());
                    }else{
                        // Si no lo es, avanzo a aux
                        aux = aux.getEnlace();
                    }
                }
            }
            
            this.cant--;
        }

        return encontrado;
    }

    // ---- Pertenece ----
    public boolean pertenece(Object elem){
        int pos = funcionHash(elem);
        NodoHashDicc aux = this.tabla[pos];
        boolean encontrado = false;

        // Busco si existe en los nodos de la posicion hash de elem
        while (!encontrado && aux != null) {
            encontrado = aux.getElem().equals(elem);
            aux = aux.getEnlace();
        }

        return encontrado;
    }

    // ---- Es Vacia ----
    public boolean esVacia(){
        return this.cant <= 0;
    }

    // ---- Listar ----
    public Lista listar(){
        Lista lis = new Lista();
        NodoHashDicc aux;
        int i = 1, cantRecorridos = 0;

        while(i < TAMANIO-1 && cantRecorridos < this.cant){
            if(this.tabla[i] != null){
                aux = this.tabla[i];
                while(aux != null){
                    lis.insertar(aux.getElem(), 1);
                    cantRecorridos++;
                    aux = aux.getEnlace();
                }
            }
            i++;
        }

        return lis;
    }

    // ---- Listar Datos ----
    public Lista listarDatos(){
        Lista lis = new Lista();
        NodoHashDicc aux;
        int i = 1, cantRecorridos = 0;

        while(i < TAMANIO-1 && cantRecorridos < this.cant){
            if(this.tabla[i] != null){
                aux = this.tabla[i];
                while(aux != null){
                    lis.insertar(aux.getDato(), 1);
                    cantRecorridos++;
                    aux = aux.getEnlace();
                }
            }
            i++;
        }

        return lis;
    }

    // ---- Recuperar Datos ----
    public Object recuperarDatos(Object elem){
        int pos = funcionHash(elem);
        NodoHashDicc aux = this.tabla[pos];
        boolean encontrado = false;
        Object aRetornar = null;

        // Busco si existe en los nodos de la posicion hash de elem
        while (!encontrado && aux != null) {
            encontrado = aux.getElem().equals(elem);
            if(encontrado){
                aRetornar = aux.getDato();
            }else{
                aux = aux.getEnlace();
            }
        }

        return aRetornar;
    }

    // ---- Funcion Hash ----
    private int funcionHash(Object elem){
        int pos = elem.hashCode();
        if(pos < 0){
            pos = (-1 * pos) % TAMANIO; 
        } else{
            pos = pos % TAMANIO;
        }
        return pos;
    }

    // ---- To string ----
    public String toString() {
        String aRetornar = "";
        NodoHashDicc aux;
        int cantRecorridos = 0;
        for(int i= 0; i < TAMANIO-1; i++){
            if(this.tabla[i] != null && cantRecorridos <= this.cant){
                cantRecorridos++;
                aRetornar += this.tabla[i].getElem().toString();
                aux = this.tabla[i].getEnlace();
                if(aux == null){
                    aRetornar += "\n";
                }else{
                    while(aux != null){
                        aRetornar += " - " + aux.getElem().toString();
                        aux = aux.getEnlace();
                    }
                    aRetornar += "\n";
                }
            }
        }
        return aRetornar;
    }
}
