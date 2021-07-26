/*
=================================================
|     Estructuras de Datos 2021                 |
=================================================
|      Clase:                                   |
|       > Nodo Vertice Grafo                    |
|      Alumno:                                  |
|        > Manuel Felipe Triñanes (FAI-2738)    |
=================================================
*/

package estructuras.grafo;

class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;
    
    public NodoVert(Object unElemento, NodoVert siguienteVertice, NodoAdy primerAdyacente) {
        elem = unElemento;
        sigVertice = siguienteVertice;
        primerAdy = primerAdyacente;
    }
    public Object getElem() {
        return elem;
    }
    public NodoVert getSigVertice() {
        return sigVertice;
    }
    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
    public void setElem(Object elemento){
        elem = elemento;
    }
    public void setSigVertice(NodoVert siguienteVertice){
        sigVertice = siguienteVertice;
    }
    public void setPrimerAdy(NodoAdy primerAdyacente){
        primerAdy = primerAdyacente;
    }
}