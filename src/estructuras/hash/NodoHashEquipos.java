package estructuras.hash;

import estructuras.datos.Equipo;

class NodoHashEquipos {
    private Object elem;
    private Equipo dato;
    private NodoHashEquipos enlace;
    
    //---- Constructor ----
    public NodoHashEquipos(Object elem, Equipo dato, NodoHashEquipos enlace){
        this.elem = elem;
        this.dato = dato;
        this.enlace = enlace;
    }
    
    //---- Modificadores ----
    public void setElem(Object elem){
        this.elem = elem;
    }
    public void setDato(Equipo dato){
        this.elem = dato;
    }
    public void setEnlace(NodoHashEquipos enlace){
        this.enlace = enlace;
    }
    
    //---- Observadores ----
    public Object getElem(){
        return elem;
    }
    public Equipo getDato(){
        return dato;
    }
    public NodoHashEquipos getEnlace(){
        return enlace;
    }
}
