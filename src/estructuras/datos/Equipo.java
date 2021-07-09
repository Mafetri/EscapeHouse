package estructuras.datos;

public class Equipo {
    private String nombre;
    private int puntajeSalida;
    private int puntajeTotal;
    private int habitacionActual;
    private int puntajeActual; 

    public Equipo(String nombre, int puntajeSalida, int puntajeTotal, int habitacionActual, int puntajeActual){
        this.nombre = nombre;
        this.puntajeSalida = puntajeSalida;
        this.puntajeTotal = puntajeTotal;
        this.habitacionActual = habitacionActual;
        this.puntajeActual = puntajeActual;
    }
    public String getNombre(){
        return nombre;
    }
    public int getPuntajeSalida(){
        return puntajeSalida;
    }
    public int getPuntajeTotal(){
        return puntajeTotal;
    }
    public int getHabitacionActual(){
        return habitacionActual;
    }
    public int getPuntajeActual(){
        return puntajeActual;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setPuntajeSalida(int puntajeSalida){
        this.puntajeSalida = puntajeSalida;
    }
    public void setPuntajeTotal(int puntajeTotal){
        this.puntajeTotal = puntajeTotal;
    }
    public void setHabitacion(int habitacionActual){
        this.habitacionActual = habitacionActual;
    }
    public void setPuntajeAcutal(int puntajeActual){
        this.puntajeActual = puntajeActual;
    }

    
}
