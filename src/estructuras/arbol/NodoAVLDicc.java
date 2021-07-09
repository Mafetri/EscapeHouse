package estructuras.arbol;

public class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int alt;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

    public NodoAVLDicc(Comparable clave, Object dato,NodoAVLDicc izq, NodoAVLDicc der){
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izq;
        this.derecho = der;
        this.alt = 0;
    }

    public Comparable getClave(){
        return this.clave;
    }

    public Object getDato(){
        return this.dato;
    }
    
    public NodoAVLDicc getIzquierdo(){
        return this.izquierdo;
    }

    public NodoAVLDicc getDerecho(){
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

    public void setDato(Object dato){
        this.dato = dato;
    }

    public void setIzquierdo(NodoAVLDicc izq){
        this.izquierdo = izq;
    }

    public void setDerecho(NodoAVLDicc der){
        this.derecho = der;
    }
}

