/*
=================================================
|     Estructuras de Datos 2021                 |
=================================================
|      Clase:                                   |
|       > Nodo Lista                            |
|      Alumno:                                  |
|       > Manuel Felipe Triñanes (FAI-2738)     |
=================================================
*/

package estructuras.lista;

class Nodo {
    private Object elem;
    private Nodo enlace;
    
    //---- Constructor ----
    public Nodo(Object elem, Nodo enlace){
        this.elem = elem;
        this.enlace = enlace;
    }
    
    //---- Modificadores ----
    public void setElem(Object elem){
        this.elem = elem;
    }
    
    public void setEnlace(Nodo enlace){
        this.enlace = enlace;
    }
    
    //---- Observadores ----
    public Object getElem(){
        return elem;
    }
    
    public Nodo getEnlace(){
        return enlace;
    }
}
