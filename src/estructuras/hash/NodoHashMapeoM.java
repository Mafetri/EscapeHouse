package estructuras.hash;
import estructuras.lista.Lista;
public class NodoHashMapeoM {
    private Object dominio;
    private Lista rango;
    private NodoHashMapeoM enlace;

    public NodoHashMapeoM(Object dominio, Lista rango, NodoHashMapeoM enlace){
        this.dominio = dominio;
        this.rango = rango;
        this.enlace = enlace;
    }
    public Object getDominio(){
        return dominio;
    }
    public Lista getRango(){
        return rango;
    }
    public NodoHashMapeoM getEnlace(){
        return enlace;
    }
    public void setDominio(Object dominio){
        this.dominio = dominio;
    }
    public void setRango(Lista rango){
        this.rango = rango;
    }
    public void setEnlace(NodoHashMapeoM enlace){
        this.enlace = enlace;
    }
}
