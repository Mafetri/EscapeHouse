package estructuras.datos;

public class Desafio {
    private String nombre;
    private String tipo;

    public Desafio(String nombre, String tipo){
        this.nombre = nombre;
        this.tipo = tipo;
    }
    public String getNombre(){
        return nombre;
    }
    public String getPlanta(){
        return tipo;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setPlanta(String planta){
        this.tipo = tipo;
    }
    public String toString(){
        return nombre + " de tipo " + tipo;
    }
}

