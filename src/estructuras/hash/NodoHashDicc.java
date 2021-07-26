/*
=================================================
|     Estructuras de Datos 2021                 |
=================================================
|      Clase:                                   |
|       > Nodo Diccionario Hash                 |
|      Alumno:                                  |
|       > Manuel Felipe Triñanes (FAI-2738)     |
=================================================
*/

package estructuras.hash;

class NodoHashDicc {
    private Object elem;
    private Object dato;
    private NodoHashDicc enlace;
    
    //---- Constructor ----
    public NodoHashDicc(Object elem, Object dato, NodoHashDicc enlace){
        this.elem = elem;
        this.dato = dato;
        this.enlace = enlace;
    }
    
    //---- Modificadores ----
    public void setElem(Object elem){
        this.elem = elem;
    }
    public void setDato(Object dato){
        this.elem = dato;
    }
    public void setEnlace(NodoHashDicc enlace){
        this.enlace = enlace;
    }
    
    //---- Observadores ----
    public Object getElem(){
        return elem;
    }
    public Object getDato(){
        return dato;
    }
    public NodoHashDicc getEnlace(){
        return enlace;
    }
}
