package estructuras.arbol;

import estructuras.datos.Habitacion;

public class NodoAVLHabitaciones {
    private Comparable clave;
    private Habitacion dato;
    private int alt;
    private NodoAVLHabitaciones izquierdo;
    private NodoAVLHabitaciones derecho;

    public NodoAVLHabitaciones(Comparable clave, Habitacion dato,NodoAVLHabitaciones izq, NodoAVLHabitaciones der){
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izq;
        this.derecho = der;
        this.alt = 0;
    }

    public Comparable getClave(){
        return this.clave;
    }

    public Habitacion getDato(){
        return this.dato;
    }
    
    public NodoAVLHabitaciones getIzquierdo(){
        return this.izquierdo;
    }

    public NodoAVLHabitaciones getDerecho(){
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

    public void setDato(Habitacion dato){
        this.dato = dato;
    }

    public void setIzquierdo(NodoAVLHabitaciones izq){
        this.izquierdo = izq;
    }

    public void setDerecho(NodoAVLHabitaciones der){
        this.derecho = der;
    }
}
