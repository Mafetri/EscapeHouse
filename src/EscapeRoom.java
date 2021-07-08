import estructuras.*;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class EscapeRoom {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Carteles de inicio
        cartelInicio();
        cartelOpcionesInicio();

        // Leectura de opcion inicio
        int opcion = sc.nextInt();

        if(opcion != 0){
            switch (opcion) {
                case 1: leectura();
                case 2: cartelPartidasGuardadas();
            }
        }else{
            cartelFinal();
        }


    }

    public static void leectura(){}
    public static void cartelInicio(){
        String titulo = "E S C A P E   R O O M";
        String costado1 = "|              ";
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
    public static void cartelPartidasGuardadas(){

    }
    public static void cartelFinal(){}
    public static void sleepMilisegundos(int tiempo){
        try{
            TimeUnit.MILLISECONDS.sleep(tiempo);
        }
        catch(Exception e) {}
    }
}
