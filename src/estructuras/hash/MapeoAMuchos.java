package estructuras.hash;
import estructuras.lista.Lista;

public class MapeoAMuchos {
    private static final int TAMANIO = 20;
    private NodoHashMapeoM[] tabla;
    private int cant;

    public MapeoAMuchos() {
        this.tabla = new NodoHashMapeoM[TAMANIO - 1];
        cant = 0;
    }
    
    // ---- Asociar ----
    public boolean asociar(Object elem, Object rango) {
        int pos = funcionHash(elem);
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;

        // Busco si existe en los nodos de la posicion hash de elem
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(elem);
            aux = aux.getEnlace();
        }

        // Si no existe el elem en la tabla, lo agrego con el nuevo rango
        if (!encontrado) {
            Lista lis = new Lista();
            lis.insertar(rango, lis.longitud()+1);
            this.tabla[pos] = new NodoHashMapeoM(elem, lis, this.tabla[pos]);
            this.cant++;
        }else{
            // Si existe le agrego el nuevo elemento del rango al elem
            Lista lis = this.tabla[pos].getRango();
            lis.insertar(rango, lis.longitud()+1);
            this.tabla[pos].setRango(lis);
        }

        return encontrado;
    }

    // ---- Asociar Lista ----
    public boolean asociarLista(Object elem, Lista lis) {
        int pos = funcionHash(elem);
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;

        // Busco si existe en los nodos de la posicion hash de elem
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(elem);
            aux = aux.getEnlace();
        }

        // Si no existe el elem en la tabla, lo agrego con el nuevo rango
        if (!encontrado) {
            this.tabla[pos] = new NodoHashMapeoM(elem, lis, this.tabla[pos]);
            this.cant++;
        }else{
            // Si existe le agrego el nuevo elemento del rango al elem
            this.tabla[pos].setRango(lis);
        }

        return encontrado;
    }

    // ---- Desasociar ----
    public boolean desasociar(Object elem, Object rango) {
        int pos = funcionHash(elem);
        int posNodo = 0;
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;

        // Busco el elem
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(elem);
            if(!encontrado){
                aux = aux.getEnlace();
                posNodo++;
            }
        }
        
        // Si fue encontrado
        if (encontrado) {
            // Elimino al elemento del rango del rango del dominio
            aux.getRango().eliminar(aux.getRango().localizar(rango));

            // Si en el rango no quedan elementos, elimino al dominio
            if(aux.getRango().esVacia()){
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
            
        }

        return encontrado;
    }

    // ---- Eliminar ----
    public boolean eliminar(Object elem) {
        int pos = funcionHash(elem);
        int posNodo = 0;
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;

        // Busco el elem
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(elem);
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
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;

        // Busco si existe en los nodos de la posicion hash de elem
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(elem);
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
        NodoHashMapeoM aux;
        int i = 1, cantRecorridos = 0;

        while(i < TAMANIO-1 && cantRecorridos < this.cant){
            if(this.tabla[i] != null){
                aux = this.tabla[i];
                while(aux != null){
                    lis.insertar(aux.getDominio(), 1);
                    cantRecorridos++;
                    aux = aux.getEnlace();
                }
            }
            i++;
        }

        return lis;
    }

    // ---- Obtener Valores ----
    public Lista obtenerValores(Object elem){
        int pos = funcionHash(elem);
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;
        Lista aRetornar = new Lista();

        // Busco si existe en los nodos de la posicion hash de elem
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(elem);
            if(!encontrado){
                aux = aux.getEnlace();
            }
        }

        // Si el nodo no es nulo, entonces elem existe en la tabla, por ende
        // retorna la lista del rango
        if(aux != null){
            aRetornar = aux.getRango();
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
}
