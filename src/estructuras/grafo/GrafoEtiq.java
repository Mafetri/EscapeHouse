/*
=================================================
|     Estructuras de Datos 2021                 |
=================================================
|      Clase:                                   |
|       > Grafo Etiquetado Dinamico             |
|         (no esta pensado para multigrafos)    |
|      Alumnos:                                 |
|        > Manuel Felipe Triñanes (FAI-2738)    |
=================================================
*/

package estructuras.grafo;
import estructuras.lista.Lista;

public class GrafoEtiq {
    private NodoVert inicio = null;

    // ---- Ubicar Vertice ----
    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        if (buscado != null) {
            while (aux != null && !aux.getElem().equals(buscado)) {
                aux = aux.getSigVertice();
            }
        }
        return aux;
    }

    // ---- Insertar Vertice ----
    public boolean insertarVertice(Object elem) {
        boolean exito = false;
        if (elem != null) {
            NodoVert aux = this.ubicarVertice(elem);
            if (aux == null) {
                this.inicio = new NodoVert(elem, this.inicio, null);
                exito = true;
            }
        }
        return exito;
    }

    // ---- Eliminar Vertice ----
    public boolean eliminarVertice(Object elem) {
        boolean exito = false;
        if (elem != null && this.inicio != null) {
            NodoVert verticeAnterior = this.inicio;

            // Para cada vertice de la lista de vertice
            while (verticeAnterior != null) {
                // Caso especial que el elemento a eliminar es el inicio
                if (this.inicio.getElem().equals(elem)) {
                    this.inicio = this.inicio.getSigVertice();
                }

                // Busco al vertice anterior de elem para eliminar a elem
                if (verticeAnterior.getSigVertice() != null && verticeAnterior.getSigVertice().getElem().equals(elem)) {
                    verticeAnterior.setSigVertice(verticeAnterior.getSigVertice().getSigVertice());
                    exito = true;
                }

                eliminarAdyacente(verticeAnterior, elem);

                verticeAnterior = verticeAnterior.getSigVertice();
            }
        }
        return exito;
    }

    // ---- Insertar Arco ----
    public boolean insertarArco(Object origen, Object destino, int etiqueta) {
        boolean exito = false;
        // Si la etiquieta no es nula
        if (this.inicio != null) {
            // Busco al vertice de "origen" y el de "destino"
            NodoVert verticeOrigen = ubicarVertice(origen);
            NodoVert verticeDestino = ubicarVertice(destino);

            // Si ambos existe en la lista de vertices
            if (verticeOrigen != null && verticeDestino != null) {
                // Le agrego en la primera adyacencia de la lista un nuevo nodo adyacente con el
                // vertice de destino
                verticeOrigen.setPrimerAdy(new NodoAdy(verticeDestino, verticeOrigen.getPrimerAdy(), etiqueta));
                // Le agrego en la primera adyacencia de la lista un nuevo nodo adyacente con el
                // vertice de origen
                verticeDestino.setPrimerAdy(new NodoAdy(verticeOrigen, verticeDestino.getPrimerAdy(), etiqueta));

                exito = true;
            }
        }
        return exito;
    }

    // ---- Eliminar Arco ----
    public boolean eliminarArco(Object origen, Object destino) {
        boolean exito = false;

        if (this.inicio != null) {
            NodoVert verticeOrigen = ubicarVertice(origen);
            NodoVert verticeDestino = ubicarVertice(destino);

            if (verticeOrigen != null && verticeDestino != null) {
                if(eliminarAdyacente(verticeOrigen, destino)){
                    if(eliminarAdyacente(verticeDestino, origen)){
                        exito = true;
                    }
                }
            }
        }

        return exito;
    }

    // ---- Eliminar Adyacente ----
    // Busca dentro de los nodos adyacentes a nodo, el vertice que tenga como elemento a elem
    // y lo elimina de la lista de adyacencia del nodo
    private boolean eliminarAdyacente(NodoVert nodo, Object elem) {
        boolean exito = false;
        if(nodo != null){
            NodoAdy adyOrigen = nodo.getPrimerAdy();
            if (adyOrigen != null) {
                // Caso especial adyecente en primer lugar
                if (adyOrigen.getVertice().getElem().equals(elem)) {
                    nodo.setPrimerAdy(adyOrigen.getSigAdyacente());
                } else {
                    // Si no esta en la primera posicion, lo busco
                    while (adyOrigen != null) {
                        // Si el siguiente adyacente es el elemento origen lo borro
                        if (adyOrigen.getSigAdyacente() != null && adyOrigen.getSigAdyacente().getVertice().getElem().equals(elem)) {
                            nodo = adyOrigen.getSigAdyacente().getVertice();
                            adyOrigen.setSigAdyacente(adyOrigen.getSigAdyacente().getSigAdyacente());
                            adyOrigen = null;
                            exito = true;
                        } else {
                            adyOrigen = adyOrigen.getSigAdyacente();
                        }
                    }
                }
            }
        }
        return exito;
    }

    // ---- Existe Vertice ----
    public boolean existeVertice(Object nodo){
        return ubicarVertice(nodo) != null;
    }

    // ---- Existe Arco ----
    public boolean existeArco(Object origen, Object destino ){
        boolean existe = false;

        if(this.inicio != null && origen != null && destino != null){
            NodoVert nodoOrigen = ubicarVertice(origen);
            if(nodoOrigen != null){
                NodoAdy adyOrigen = nodoOrigen.getPrimerAdy();
                while (adyOrigen != null) {
                    // Si el siguiente adyacente es el elemento origen lo borro
                    if (adyOrigen.getVertice().getElem().equals(destino)) {
                        existe = true;
                        adyOrigen = null;
                    } else {
                        adyOrigen = adyOrigen.getSigAdyacente();
                    }
                }
            }
        }

        return existe;
    }

    // ---- Etiqueta Arco ----
    public int etiquetaArco(Object origen, Object destino ){
        int etiq = -1;
        boolean encontrado = false;
        if(this.inicio != null && origen != null && destino != null){
            NodoVert nodoOrigen = ubicarVertice(origen);
            if(nodoOrigen != null){
                NodoAdy adyOrigen = nodoOrigen.getPrimerAdy();
                while (adyOrigen != null && !encontrado) {
                    // Si el siguiente adyacente es el elemento origen lo borro
                    if (adyOrigen.getVertice().getElem().equals(destino)) {
                        etiq = adyOrigen.getEtiqueta();
                        encontrado = true;
                    } else {
                        adyOrigen = adyOrigen.getSigAdyacente();
                    }
                }
            }
        }
        return etiq;
    }

    // ---- Es Vacio ----
    public boolean esVacio() {
        return this.inicio == null;
    }

    // ---- nodosAdyacentes ----
    public String nodosAdyacentes(Object clave){
        String enTexto = "";
        NodoVert verticeClave = ubicarVertice(clave);
        if(verticeClave != null){
            NodoAdy adyClave = verticeClave.getPrimerAdy();
            while(adyClave != null){
                enTexto += adyClave.getVertice().getElem().toString() + " con un puntaje necesario de " + adyClave.getEtiqueta() + " ";
                if (adyClave.getSigAdyacente() != null) {
                    enTexto += ", ";
                } else {
                    enTexto += ". ";
                }
                adyClave = adyClave.getSigAdyacente();
            }
        }
        return enTexto;
    }

    // ---- Existe Camino Etiquetado ----
    public boolean existeCaminoEtiquetado(Object origen, Object destino, int puntaje){
        boolean exito = false;

        NodoVert auxOrigen = null;
        NodoVert auxDestino = null;
        NodoVert aux = this.inicio;

        while(((auxOrigen == null) || (auxDestino == null)) && (aux != null)){
            if(aux.getElem().equals(origen)){
                auxOrigen = aux;
            }
            if(aux.getElem().equals(destino)){
                auxDestino = aux;
            }
            aux = aux.getSigVertice();
        }

        if(auxOrigen != null && auxDestino != null){
            Lista visitados = new Lista();
            exito = existeCaminoEtiquetadoAux(auxOrigen, destino, visitados, 0, puntaje);
        }

        return exito;
    }
    private boolean existeCaminoEtiquetadoAux(NodoVert nodo, Object dest, Lista vis, int puntajeAcumulado, int puntaje){
        boolean exito = false;
        if(nodo != null && puntajeAcumulado <= puntaje){
            if(nodo.getElem().equals(dest)){
                exito = true;
            }else{
                vis.insertar(nodo.getElem(), vis.longitud()+1);
                NodoAdy ady = nodo.getPrimerAdy();
                while(!exito && ady != null){
                    if(vis.localizar(ady.getVertice().getElem()) < 0){
                        exito = existeCaminoEtiquetadoAux(ady.getVertice(), dest, vis, puntajeAcumulado+ady.getEtiqueta(), puntaje);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    // ---- Caminos sin pasar por ----
    // Devuelve una lista de caminos posibles desde el origen al destino sin pasar por "noPasar" y con
    // una suma de etiquetas no mayor a puntajeActual
    public Lista caminosSinPasarPor(Object origen, Object destino, Object noPasar, int puntajeActual){
        Lista todosLosCaminos = new Lista();

        NodoVert auxOrigen = null;
        NodoVert auxDestino = null;
        NodoVert aux = this.inicio;

        while(((auxOrigen == null) || (auxDestino == null)) && (aux != null)){
            if(aux.getElem().equals(origen)){
                auxOrigen = aux;
            }
            if(aux.getElem().equals(destino)){
                auxDestino = aux;
            }
            aux = aux.getSigVertice();
        }

        if(auxOrigen != null && auxDestino != null){
            Lista visitados = new Lista();
            caminosSinPasarPorAux(auxOrigen, destino, noPasar, visitados, todosLosCaminos, 0, puntajeActual);
        }

        return todosLosCaminos;
    }
    private void caminosSinPasarPorAux(NodoVert nodo, Object dest, Object noPasar, Lista vis, Lista todosLosCaminos, int puntajeNecesario, int puntajeActual){
        if(nodo != null && !nodo.getElem().equals(noPasar) && puntajeNecesario <= puntajeActual){
            if(nodo.getElem().equals(dest)){
                vis.insertar(nodo.getElem(), vis.longitud()+1);
                todosLosCaminos.insertar(vis.clone(), todosLosCaminos.longitud()+1);
            }else{
                vis.insertar(nodo.getElem(), vis.longitud()+1);
                NodoAdy ady = nodo.getPrimerAdy();
                while(ady != null){
                    if(vis.localizar(ady.getVertice().getElem()) < 0){
                        caminosSinPasarPorAux(ady.getVertice(), dest, noPasar, vis, todosLosCaminos, puntajeNecesario+ady.getEtiqueta(), puntajeActual);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            vis.eliminar(vis.localizar(nodo.getElem()));
        }
    }

    // ---- To String ----
    public String toString() {
        String enTexto = "";
        NodoVert vertice = this.inicio;
        NodoAdy adyacente;

        while (vertice != null) {
            adyacente = vertice.getPrimerAdy();
            enTexto += vertice.getElem().toString() + ": ";
            while (adyacente != null) {
                enTexto += adyacente.getVertice().getElem().toString() + "(" + adyacente.getEtiqueta() + ")";
                if (adyacente.getSigAdyacente() != null) {
                    enTexto += ", ";
                } else {
                    enTexto += ". ";
                }
                adyacente = adyacente.getSigAdyacente();
            }
            enTexto += "\n";
            vertice = vertice.getSigVertice();
        }
        return enTexto;
    }

    // ---- Cambiar Etiquieta ----
    public boolean cambiarEtiqueta(Object origen, Object destino, int etiqueta){
        boolean exito = false;
        if(this.inicio != null && origen != null && destino != null){
            NodoVert nodoOrigen = ubicarVertice(origen);
            NodoVert nodoDestino = null;
            // Si existe el origen
            if(nodoOrigen != null){
                // Cambio la etiqueta del nodo que apunte al destino
                NodoAdy adyOrigen = nodoOrigen.getPrimerAdy();
                while (adyOrigen != null && !exito) {
                    if (adyOrigen.getVertice().getElem().equals(destino)) {
                        // Cambio la etiqueta y guardo en nodoDestino el vertice del ady
                        nodoDestino = adyOrigen.getVertice();
                        adyOrigen.setEtiqueta(etiqueta);
                        exito = true;
                    } else {
                        adyOrigen = adyOrigen.getSigAdyacente();
                    }
                }

                if(exito){
                    exito = false;
                    // Y cambio la etiqueta del nodo que apunte al origen, si es que hay
                    NodoAdy adyDestino = nodoDestino.getPrimerAdy();
                    while (adyDestino != null && !exito) {
                        if (adyDestino.getVertice().getElem().equals(origen)) {
                            adyDestino.setEtiqueta(etiqueta);
                            exito = true;
                        } else {
                            adyDestino = adyDestino.getSigAdyacente();
                        }
                    }
                }
            }
        }
        return exito;
    }
}
