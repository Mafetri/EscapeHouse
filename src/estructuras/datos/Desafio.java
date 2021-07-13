package estructuras.datos;

public class Desafio {
    private int puntaje;
    private String nombre;
    private String tipo;

    public Desafio(int puntaje, String nombre, String tipo){
        this.puntaje = puntaje;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    public int getPuntaje(){
        return puntaje;
    }
    public String getNombre(){
        return nombre;
    }
    public String getTipo(){
        return tipo;
    }
    public void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public String toString(){
        return nombre + " de tipo " + tipo;
    }
}

