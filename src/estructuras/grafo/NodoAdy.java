/*
=================================================
|     Estructuras de Datos 2021                 |
=================================================
|      Clase:                                   |
|       > Nodo Adyacente Grafo Etiquetado       |
|      Alumno:                                  |
|        > Manuel Felipe Triñanes (FAI-2738)    |
=================================================
*/

package estructuras.grafo;

class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;

    public NodoAdy(NodoVert unVertice, NodoAdy unSigAdyacente, int unaEtiqueta) {
        vertice = unVertice;
        sigAdyacente = unSigAdyacente;
        etiqueta = unaEtiqueta;
    }

    public NodoVert getVertice() {
        return vertice;
    }
    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }
    public int getEtiqueta() {
        return etiqueta;
    }
    public void setVertice(NodoVert nodo){
        vertice = nodo;
    }
    public void setSigAdyacente(NodoAdy nodo){
        sigAdyacente = nodo;
    }
    public void setEtiqueta(int unaEtiqueta){
        etiqueta = unaEtiqueta;
    }
}
