import estructuras.*;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class EscapeRoom {
    public static void main(String[] args) {
        // Escaner de ingreso
        Scanner sc = new Scanner(System.in);

        // Estructuras
        GrafoEtiq casa = new GrafoEtiq();
        DiccionarioAVL habitaciones = new DiccionarioAVL(), desafios = new DiccionarioAVL();
        HashAbierto equipos = new HashAbierto(), desafiosCompletados = new HashAbierto();

        // Carteles de inicio
        cartelInicio();
        cartelOpcionesInicio();

        // Leectura de opcion inicio
        int opcion = sc.nextInt();

        if(opcion != 0){
            switch (opcion) {
                case 1: leectura("\\estructuras\\partidas\\PartidaNueva.txt", casa, habitaciones, desafios, equipos, desafiosCompletados);break;
                case 2: leectura("\\estructuras\\partidas\\PartidaGuardada.txt", casa, habitaciones, desafios, equipos, desafiosCompletados);
            }


            do{
                cartelMenuPrincipal();
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1: abm();break;
                    case 2: consultaHabitaciones(habitaciones, casa); break;
                    case 3: consultaDesafios(); break;
                    case 4: consultaParticipantes(); break;
                    case 5: consultaGeneral();
                }
            }while(opcion != 0);


        }
        cartelFinal();
        


    }

    public static void leectura(String ubicacion, GrafoEtiq casa, DiccionarioAVL habitaciones, DiccionarioAVL desafios, HashAbierto equipos, HashAbierto desafiosCompletados){
        habitaciones.insertar(1, "Cocina, 1era planta, 25m2, sin salida");
        casa.insertarVertice(1);
        casa.insertarVertice(2);
        casa.insertarArco(1, 2, 35);
    }
    
    // =================================
    //   Altas, bajas y modificaciones
    // =================================
    public static void abm(){

    }
    
    // =========================
    //   Consulta Habitaciones
    // =========================
    public static void consultaHabitaciones(DiccionarioAVL habitaciones, GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{
            menuConsultaHabitaciones();
            opcion = sc.nextInt();

            switch(opcion){
                case 1: mostrarHabitacion(habitaciones); break;
                case 2: habitacionesContiguas(casa); break;
            }
        }while(opcion != 0);
        

    }
    // ---- Menu ----
    public static void menuConsultaHabitaciones(){
        System.out.println("| 1. Mostrar habitacion                          |");
        sleepMilisegundos(150);
        System.out.println("| 2. Habitaciones contiguas                      |");
        sleepMilisegundos(150);
        System.out.println("| 3. Es posible llegar                           |");
        sleepMilisegundos(150);
        System.out.println("| 4. Maximo puntaje                              |");
        sleepMilisegundos(150);
        System.out.println("| 5. Sin pasar por                               |");
        sleepMilisegundos(150);
        System.out.println("| 0. Finalizar                                   |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    // ---- Mostrar Habitaciones ----
    public static void mostrarHabitacion(DiccionarioAVL habitaciones){
        // Pregunto que habitacion se quiere consultar
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese numero de habitacion: ");
        int numero = sc.nextInt();
        System.out.println("|------------------------------------------------|");

        // Busco en el arbol de habitaciones los datos de la habitacion
        Object dato = habitaciones.recuperarDatos(numero);
        
        // Si la habitacion existe, muestro sus datos
        if(dato != null){
            System.out.println("| La habitacion con clave " + numero + " es el/la " + dato.toString());
        }else{
            System.out.println("| La habitacion con clave " + numero + " no existe.");
        }
        
        System.out.println("|------------------------------------------------|");
    }
    // ---- Habitaciones Contiguas ----
    public static void habitacionesContiguas(GrafoEtiq casa){
        // Pregunto que habitacion se quiere consultar
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese numero de habitacion: ");
        int numero = sc.nextInt();
        System.out.println("|------------------------------------------------|");
        System.out.println("| La habitacion " + numero+ " es contigua con " + casa.nodosAdyacentes(numero));
        System.out.println("|------------------------------------------------|");
    }
    // ---- Es Posible Llegar ----

    // ---- Sin Pasar Por ----


    // =========================
    //     Consulta Desafios
    // =========================
    public static void consultaDesafios(){}

    // =========================
    //  Consulta Participantes
    // =========================
    public static void consultaParticipantes(){}

    // =========================
    //      Consulta General
    // =========================
    public static void consultaGeneral(){}


    // =========================
    //         Carteles
    // =========================
    public static void cartelInicio(){
        String titulo = "E S C A P E   H O U S E";
        String costado1 = "|            ";
        String costado2 = "             |";
        int esperaRapida = 15;
        int esperaLenta = 80;
        for(int i = 0; i <50; i++){
            System.out.print("=");
            sleepMilisegundos(esperaRapida);
        }
        System.out.println();
        for(int i = 0; i < costado1.length(); i++){
            System.out.print(costado1.charAt(i));
            sleepMilisegundos(esperaRapida);
        }
        for(int i = 0; i < titulo.length(); i++){
            System.out.print(titulo.charAt(i));
            sleepMilisegundos(esperaLenta);
        }
        for(int i = 0; i < costado2.length(); i++){
            System.out.print(costado2.charAt(i));
            sleepMilisegundos(esperaRapida);
        }
        System.out.println();
        for(int i = 0; i <50; i++){
            System.out.print("=");
            sleepMilisegundos(esperaRapida);
        }
        System.out.println();
    }
    public static void cartelOpcionesInicio(){
        System.out.println("| 1. Iniciar partida nueva                       |");
        sleepMilisegundos(150);
        System.out.println("| 2. Iniciar partida guardada                    |");
        sleepMilisegundos(150);
        System.out.println("| 0. Finalizar                                   |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    public static void cartelMenuPrincipal(){
        System.out.println("| 1. Altas, Bajas y Modificaciones               |");
        sleepMilisegundos(150);
        System.out.println("| 2. Consulta sobre habitaciones                 |");
        sleepMilisegundos(150);
        System.out.println("| 3. Consultas sobre desafíos                    |");
        sleepMilisegundos(150);
        System.out.println("| 4. Consultas sobre equipos participantes       |");
        sleepMilisegundos(150);
        System.out.println("| 5. Consulta general                            |");
        sleepMilisegundos(150);
        System.out.println("| 0. Finalizar                                   |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    public static void cartelFinal(){}
    public static void sleepMilisegundos(int tiempo){
        try{
            TimeUnit.MILLISECONDS.sleep(tiempo);
        }
        catch(Exception e) {}
    }
}
