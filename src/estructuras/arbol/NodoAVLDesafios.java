package estructuras.arbol;

import estructuras.datos.Desafio;

public class NodoAVLDesafios{
    private Comparable clave;
    private Desafio dato;
    private int alt;
    private NodoAVLDesafios izquierdo;
    private NodoAVLDesafios derecho;

    public NodoAVLDesafios(Comparable clave, Desafio dato,NodoAVLDesafios izq, NodoAVLDesafios der){
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izq;
        this.derecho = der;
        this.alt = 0;
    }

    public Comparable getClave(){
        return this.clave;
    }

    public Desafio getDato(){
        return this.dato;
    }
    
    public NodoAVLDesafios getIzquierdo(){
        return this.izquierdo;
    }

    public NodoAVLDesafios getDerecho(){
        return this.derecho;
    }

    public int getAltura(){
        return this.alt;
    }

    public void recalcularAltura() {
        int altD = -1;
        int altI = -1;

        if (this.derecho != null) {
            altD = this.derecho.getAltura();
        }

        if (this.izquierdo != null) {
            altI = this.izquierdo.getAltura();
        }

        this.alt = Math.max(altI, altD) + 1;
    }
    
    public void setClave(Comparable clave){
        this.clave = clave;
    }

    public void setDato(Desafio dato){
        this.dato = dato;
    }

    public void setIzquierdo(NodoAVLDesafios izq){
        this.izquierdo = izq;
    }

    public void setDerecho(NodoAVLDesafios der){
        this.derecho = der;
    }
}

