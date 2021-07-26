/*
=================================================
|     Estructuras de Datos 2021                 |
=================================================
|      Clase:                                   |
|       > Desafio                               |
|      Alumno:                                  |
|        > Manuel Felipe Triñanes (FAI-2738)    |
=================================================
*/

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

