/*
=================================================
|     Estructuras de Datos 2021                 |
=================================================
|      Clase:                                   |
|       > Habitacion                            |
|      Alumno:                                  |
|        > Manuel Felipe Triñanes (FAI-2738)    |
=================================================
*/

package estructuras.datos;

public class Habitacion {
    private int codigo;
    private String nombre;
    private int planta;
    private int metrosCuadrados;
    private boolean salida;

    public Habitacion(int codigo, String nombre, int planta, int metrosCuadrados, boolean salida){
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metrosCuadrados = metrosCuadrados;
        this.salida = salida;
    }
    public int getCodigo() {
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public int getPlanta(){
        return planta;
    }
    public int getMetrosCuadrados(){
        return metrosCuadrados;
    }
    public boolean getSalida(){
        return salida;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setPlanta(int planta){
        this.planta = planta;
    }
    public void setMetrosCuadrados(int metrosCuadrados){
        this.metrosCuadrados = metrosCuadrados;
    }
    public void setSalida(boolean salida){
        this.salida = salida;
    }
    public String toString(){
        String enTexto = "";
        if(this.salida){
            enTexto = nombre + ", en la planta " + planta + ", con " + metrosCuadrados + "m2" + ", con salida";
        } else{
            enTexto = nombre + ", en la planta " + planta + ", con " + metrosCuadrados + "m2" + ", sin salida";
        }
        return enTexto;
    }
}
