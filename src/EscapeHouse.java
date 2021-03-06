/*
==================================================
|   Trabajo Final de Estructuras de Datos 2021   |
==================================================
|      Clase:                                    |
|       > Escape House                           |
|      Alumno:                                   |
|       > Manuel Felipe Triñanes (FAI-2738)      |
==================================================
*/

import estructuras.arbol.*;
import estructuras.datos.*;
import estructuras.hash.*;
import estructuras.grafo.*;
import estructuras.lista.Lista;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.SimpleDateFormat;

public class EscapeHouse {
    public static void main(String[] args) {
        // Estructuras
        GrafoEtiq casa = new GrafoEtiq();
        DiccionarioAVL habitaciones = new DiccionarioAVL();
        DiccionarioAVL desafios = new DiccionarioAVL();
        DiccionarioHash equipos = new DiccionarioHash();
        MapeoAMuchos desafiosResueltos = new MapeoAMuchos();

        // Inicio del log
        Date fecha = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        escritura("\n================================ " + formatter.format(fecha) + " ================================", true);
        
        // Leectura de txt
        leectura("D:\\Archivos\\Documentos\\Facultad\\.Materias no actuales\\Materias Cursadas\\Estructuras de Datos (Prom - Desarollo)\\Codigo\\EDAT2021VSC\\TPFinal\\src\\partidas\\PartidaNueva.txt", casa, habitaciones, desafios, equipos, desafiosResueltos);
        
        // Carteles de inicio
        cartelInicio();

        // Escaner de ingreso
        Scanner sc = new Scanner(System.in);
        int opcion;

        do{
            cartelMenuPrincipal();
            opcion = sc.nextInt();

            switch (opcion) {
                case 1: abm(habitaciones, casa, desafios, equipos, desafiosResueltos); break;
                case 2: consultaHabitaciones(habitaciones, casa); break;
                case 3: consultaDesafios(desafios, desafiosResueltos, equipos); break;
                case 4: consultaParticipantes(equipos, desafiosResueltos, desafios, habitaciones, casa); break;
                case 5: consultaGeneral(habitaciones, casa, desafios, equipos, desafiosResueltos);
            }
        }while(opcion != 0);
        
        sc.close();
        cartelFinal();
        escritura("================================== FIN DE EJECUCION =================================", true);
    }

    
    // =================================
    //        Leectura y escritura
    //              de .txt
    // =================================
    public static void leectura(String ubicacion, GrafoEtiq casa, DiccionarioAVL habitaciones, DiccionarioAVL desafios, DiccionarioHash equipos, MapeoAMuchos desafiosResueltos){
        try{
            BufferedReader archivo = new BufferedReader(new FileReader(ubicacion));
            String linea;
            // En cada iteracion, guardo en la variable linea el string una linea de texto
            while((linea = archivo.readLine()) != null){
                // Analizo a que categoria pertenece (grupo, habitacion, desafio, etc)
                char categoria = linea.charAt(0);
                linea = linea.substring(2);

                // Segun la categoria lo mando a su respectivo modulo de llenado de la estructura correspondiente
                switch(categoria){
                    case 'H': cargarHabitacion(habitaciones, casa,linea); break;
                    case 'E': cargarEquipo(equipos, linea); break;
                    case 'D': cargarDesafio(desafios, linea); break;
                    case 'P': cargarPuerta(casa, linea); break;
                    case 'C': cargarDesafiosResueltos(desafiosResueltos, linea);
                }
            }
            archivo.close();
        }catch(IOException e){
            System.out.println("| > Error: " + e);
        }
    }
    public static void escritura(String frase, boolean seguir){
        try{
            BufferedWriter archivo = new BufferedWriter(new FileWriter("D:\\Archivos\\Documentos\\Facultad\\.Materias no actuales\\Materias Cursadas\\Estructuras de Datos (Prom - Desarollo)\\Codigo\\EDAT2021VSC\\TPFinal\\src\\partidas\\log.txt", seguir));
            archivo.write(frase);
            archivo.newLine();
            archivo.close();
        }catch(IOException e){
            System.out.println("| > Error: " + e);
        }
    }


    // =================================
    //       Carga/Baja/Modificaion
    //           de Datos
    // =================================
    // ----  Habitacion ---- 
    public static void cargarHabitacion(DiccionarioAVL habitaciones, GrafoEtiq casa,String linea){
        // Variables de habitacion
        int codigo = 0, planta = 0, metrosCuadrados = 0;
        String nombre = "";
        boolean salida = false;

        // Token guarda las cadenas de Strings entre los ;
        StringTokenizer token = new StringTokenizer(linea, ";");

        // cantTokens guarda la cantidad de tokens guardados
        int cantTokens = token.countTokens();

        // tokenAcutal guarda el token actual para analizar a que variable pertenece
        String tokenActual;

        // Desde 0 hasta la cantidad de tokens, analiza token por token y segun el valor de
        // i (veces que ya guardo cosas) determina a que variable le corresponde el token
        for(int i = 0; i < cantTokens; i++){
            tokenActual = token.nextToken();
            switch(i){
                case 0: codigo = Integer.parseInt(tokenActual); break;
                case 1: nombre = tokenActual; break;
                case 2: planta = Integer.parseInt(tokenActual); break;
                case 3: metrosCuadrados = Integer.parseInt(tokenActual); break;
                case 4: salida = Boolean.parseBoolean(tokenActual);
            }
        }

        // Luego creo la habitacion con las variables llenadas con los tokens
        Habitacion nueva = new Habitacion(codigo, nombre, planta, metrosCuadrados,salida);

        // Guardo en el el AVL de habitaciones la nueva habitacion con el codigo como clave y "nueva" como dato
        habitaciones.insertar(codigo, nueva);

        // Agrego la habitacion a la casa
        casa.insertarVertice(codigo);
    }
    public static void cargarHabitacionManual(DiccionarioAVL habitaciones, GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        int codigo = 0, planta = 0, metrosCuadrados = 0;
        String nombre = "";
        char salida;
        boolean tieneSalida = false, existe;
        
        System.out.print("| > Indique codigo de habitacion: ");
        codigo = sc.nextInt();
        sc.nextLine();
        existe = habitaciones.pertenece(codigo);
        if(existe){
            System.out.println("| > La habitacion con codigo " + codigo + " ya existe.");
        }else{
            System.out.print("| > Indique nombre de habitacion: ");
            nombre = sc.nextLine();
            System.out.print("| > Indique planta de habitacion: ");
            planta = sc.nextInt();
            System.out.print("| > Indique metros cuadrados: ");
            metrosCuadrados = sc.nextInt();
            System.out.print("| > Tiene salida? (s/n): ");
            do{
                salida = sc.next().toLowerCase().charAt(0);
                if(salida == 's'){
                    tieneSalida = true;
                }else if(salida == 'n'){
                    tieneSalida = false;
                }else{
                    System.out.println("| > Error, ingrese 'S' o 's' si tiene salida\n| o 'N' o 'n' si no tiene salida");
                }
            }while(salida != 's' && salida != 'n');
            
            // Luego creo la habitacion con las variables llenadas con los tokens
            Habitacion nueva = new Habitacion(codigo, nombre, planta, metrosCuadrados, tieneSalida);
            
            // Guardo en el el AVL de habitaciones la nueva habitacion con el codigo como clave y "nueva" como dato
            habitaciones.insertar(codigo, nueva);
    
            // Agrego la habitacion a la casa
            casa.insertarVertice(codigo);
    
            // Mensaje de confirmacion y escritura en el log
            escritura("| > La habitacion " + nueva.toString() + " ha sido dado de alta.", true);
            System.out.println("| > La habitacion " + nueva.toString() + " ha sido dado de alta");
        }
    }
    public static void eliminarHabitacion(DiccionarioAVL habitaciones, GrafoEtiq casa, DiccionarioHash equipos){
        Scanner sc = new Scanner(System.in);
        int codigo = 0;
        int i = 1;
        String opcion = "s";
        System.out.print("| > Indique codigo de habitacion a eliminar: ");
        codigo = sc.nextInt();

        // Elimino la habitacion de las habitaciones y de la casa
        if(habitaciones.pertenece(codigo)){
            Lista listaEquipos = equipos.listarDatos();

            // Elimina de la listaEquipos, los equipos que no esten en la habitacion
            while(listaEquipos.recuperar(i) != null){
                if(((Equipo)listaEquipos.recuperar(i)).getHabitacionActual() == codigo){
                    i++;
                }else{
                    listaEquipos.eliminar(i);
                }
            }
            // Si en la lista quedo mas de un elemento, entonces aviso al usuario que equipos estan en la habitacion
            if(i > 1){
                System.out.print("| > Los integrantes de " + ((Equipo)listaEquipos.recuperar(1)).getNombre() + ", ");
                listaEquipos.eliminar(1);
                while(!listaEquipos.esVacia()){
                    System.out.print(((Equipo)listaEquipos.recuperar(1)).getNombre() + ", ");
                    listaEquipos.eliminar(1);
                }
                System.out.print(" estan en esa habitacion, desea eliminarla? (s/n): ");
                opcion = sc.next();
            }
            if(opcion.trim().toLowerCase().equals("s")){
                habitaciones.eliminar(codigo);
                if(casa.eliminarVertice(codigo)){
                    System.out.println("| > La habitacion con codigo " + codigo + " ha sido eliminada.");
                    escritura("| > La habitacion con codigo " + codigo + " ha sido eliminada.", true);
                }
            }else{
                System.out.println("| > Eliminacion cancelada.");
            }
        }else{
            System.out.println("| > La habitacion con codigo " + codigo + " no existe.");
        }
    }
    public static void modificarHabitacion(DiccionarioAVL habitaciones, GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        int codigo = 0, opcion = 0;
        System.out.print("| > Ingrese codigo de habitacion a modificar: ");
        codigo = sc.nextInt();
        if(habitaciones.pertenece(codigo)){
            do{
                menuModificaionHabitacion(codigo);
                opcion = sc.nextInt();
                if(opcion == 1){
                    System.out.print("| > Ingrese nuevo nombre: "); 
                    String nombre = sc.nextLine(); 
                    ((Habitacion)habitaciones.recuperarDatos(codigo)).setNombre(nombre);
                    System.out.println("| > La habitacion " + codigo + " cambio su nombre a " + nombre + ".");
                    escritura("| > La habitacion " + codigo + " cambio su nombre a " + nombre + ".", true);
                } else if(opcion == 2){
                    System.out.println("| > Ingrese nueva planta: "); 
                    int planta = sc.nextInt(); 
                    ((Habitacion)habitaciones.recuperarDatos(codigo)).setPlanta(planta);
                    System.out.println("| > La habitacion " + codigo + " cambio de planta a " + planta + ".");
                    escritura("| > La habitacion " + codigo + " cambio de planta a " + planta + ".", true);
                } else if(opcion == 3){
                    System.out.print("| > Ingrese metros cuadrados: "); 
                    int metros = sc.nextInt(); 
                    ((Habitacion)habitaciones.recuperarDatos(codigo)).setMetrosCuadrados(metros);
                    System.out.println("| > La habitacion " + codigo + " paso a tener " + metros + " metros cuadrados.");
                    escritura("| > La habitacion " + codigo + " paso a tener " + metros + " metros cuadrados.", true);
                } else if(opcion == 4){
                    System.out.print("| > Indique si tiene salida: (s/n)");
                    char salida;
                    do{
                        salida = sc.next().toLowerCase().charAt(0);
                        if(salida == 's'){
                            ((Habitacion)habitaciones.recuperarDatos(codigo)).setSalida(true);
                            System.out.println("| > La habitacion " + codigo + " ahora tiene salida");
                            escritura("| > La habitacion " + codigo + " ahora tiene salida", true);
                        }else if(salida == 'n'){
                            ((Habitacion)habitaciones.recuperarDatos(codigo)).setSalida(false);
                            System.out.println("| > La habitacion " + codigo + " ahora no tiene salida");
                            escritura("| > La habitacion " + codigo + " ahora no tiene salida", true);
                        }else{
                            System.out.println("| > Error, ingrese 'S' o 's' si tiene salida\n| o 'N' o 'n' si no tiene salida");
                        }
                    }while(salida != 's' && salida != 'n');
                }
            }while(opcion != 0);
        }else{
            System.out.println("| > No existe la habitacion ingresada.");
        }
    }
    public static void menuModificaionHabitacion(int codigo){
        System.out.println("==================================================");
        System.out.println("|           MODIFICACION HABITACION " + codigo + "           |");
        System.out.println("==================================================");
        System.out.println("| 1. Modificar nombre                            |");
        sleepMilisegundos(150);
        System.out.println("| 2. Modificar planta                            |");
        sleepMilisegundos(150);
        System.out.println("| 3. Modificar metros cuadrados                  |");
        sleepMilisegundos(150);
        System.out.println("| 4. Modificar salida                            |");
        sleepMilisegundos(150);
        System.out.println("| 0. <-- Volver <--                              |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    // ----  Equipo ---- 
    public static void cargarEquipo(DiccionarioHash equipos, String linea){
        // Variables de equipo
        String nombre = "";
        int puntajeSalida = 0, puntajeTotal = 0, puntajeActual = 0, habitacionActual = 0;

        // Token guarda las cadenas de Strings entre los ;
        StringTokenizer token = new StringTokenizer(linea, ";");

        // cantTokens guarda la cantidad de tokens guardados
        int cantTokens = token.countTokens();

        // tokenAcutal guarda el token actual para analizar a que variable pertenece
        String tokenActual;

        // Desde 0 hasta la cantidad de tokens, analiza token por token y segun el valor de
        // i (veces que ya guardo cosas) determina a que variable le corresponde el token
        for(int i = 0; i < cantTokens; i++){
            tokenActual = token.nextToken();
            switch(i){
                case 0: nombre = tokenActual; break;
                case 1: puntajeSalida = Integer.parseInt(tokenActual); break;
                case 2: puntajeTotal = Integer.parseInt(tokenActual); break;
                case 3: habitacionActual = Integer.parseInt(tokenActual); break;
                case 4: puntajeActual = Integer.parseInt(tokenActual);
            }
        }

        // Luego creo el equipo con las variables llenadas con los tokens
        Equipo nuevo = new Equipo(nombre, puntajeSalida, puntajeTotal, habitacionActual, puntajeActual);

        // Guardo en el hash de equipos el nuevo equipo con el nombre como clave y "nuevo" como dato
        equipos.insertar(nombre, nuevo);
    }
    public static void cargarEquipoManual(DiccionarioHash equipos){
        Scanner sc = new Scanner(System.in);
        // Variables de equipo
        String nombre = "";
        int puntajeSalida = 0, puntajeTotal = 0, puntajeActual = 0, habitacionActual = 0;
        boolean existe;
        
        System.out.print("| > Ingrese nombre del equipo: ");
        nombre = sc.nextLine();
        existe = equipos.pertenece(nombre);
        if(existe){
            System.out.println("| > El equipo " + nombre + " ya existe.");
        }else{
            System.out.print("| > Ingrese puntaje de salida: ");
            puntajeSalida = sc.nextInt();
            System.out.print("| > Ingrese puntaje total: ");
            puntajeTotal = sc.nextInt();
            System.out.print("| > Ingrese puntaje actual: ");
            puntajeActual = sc.nextInt();
            System.out.print("| > Ingrese habitacion actual: ");
            habitacionActual = sc.nextInt();
    
            // Luego creo el equipo con las variables llenadas con los tokens
            Equipo nuevo = new Equipo(nombre, puntajeSalida, puntajeTotal, habitacionActual, puntajeActual);

            // Guardo en el hash de equipos el nuevo equipo con el nombre como clave y "nuevo" como dato
            equipos.insertar(nombre, nuevo);

            System.out.println("| > El equipo " + nuevo.toString() + " ha sido dado de alta");
            escritura("| > El equipo " + nuevo.toString() + " ha sido dado de alta.", true);
        }
    }
    public static void eliminarEquipo(DiccionarioHash equipos, MapeoAMuchos desafiosResueltos){
        Scanner sc = new Scanner(System.in);
        String nombre = "";
        boolean exito = false;

        System.out.print("| > Ingrese nombre del equipo a eliminar: ");
        nombre = sc.nextLine();

        // Elimino al equipo del diccionario de equipos y lo elimino del mapeo de desafios resueltos
        exito = equipos.eliminar(nombre);
        desafiosResueltos.eliminar(nombre);

        System.out.print("| > El equipo " + nombre);
        if(exito){
            System.out.println(" ha sido eliminado.");
            escritura("| > El equipo " + nombre + " ha sido eliminado.", true);
        }else{
            System.out.println(" no se ha encontrado.");
        }
    }
    public static void modificarEquipo(DiccionarioHash equipos, DiccionarioAVL habitaciones, MapeoAMuchos desafiosResueltos){
        Scanner sc = new Scanner(System.in);
        int opcion = 0, habitacionActual;
        boolean existe;
        System.out.print("| > Ingrese nombre de equipo a modificar: ");
        String nombre = sc.nextLine();
        if(equipos.pertenece(nombre)){
            Equipo equipo = (Equipo)equipos.recuperarDatos(nombre);
            do{
                menuModificaionEquipo(nombre);
                opcion = sc.nextInt();
                if(opcion == 1){
                    System.out.print("| > Ingrese puntaje de salida: "); 
                    int puntajeSalida = sc.nextInt(); 
                    equipo.setPuntajeSalida(puntajeSalida);
                    System.out.println("| > El equipo " + nombre + " ahora tiene un puntaje de salida de " + puntajeSalida + " puntos.");
                    escritura("| > El equipo " + nombre + " ahora tiene un puntaje de salida de " + puntajeSalida + " puntos.", true);
                } else if(opcion == 2){
                    System.out.print("| > Ingrese puntaje total: "); 
                    int puntajeTotal = sc.nextInt(); 
                    equipo.setPuntajeTotal(puntajeTotal);
                    System.out.println("| > El equipo " + nombre + " ahora tiene un puntaje total de " + puntajeTotal + " puntos.");
                    escritura("| > El equipo " + nombre + " ahora tiene un puntaje total de " + puntajeTotal + " puntos.", true);
                } else if(opcion == 3){
                    System.out.print("| > Ingrese puntaje actual: "); 
                    int puntajeActual = sc.nextInt(); 
                    equipo.setPuntajeActual(puntajeActual);
                    System.out.println("| > El equipo " + nombre + " ahora tiene un puntaje actual de " + puntajeActual + " puntos.");
                    escritura("| > El equipo " + nombre + " ahora tiene un puntaje actual de " + puntajeActual + " puntos.", true);
                } else if(opcion == 4){
                    System.out.print("| > Ingrese habitacion: ");
                    habitacionActual = sc.nextInt();
                    existe = habitaciones.pertenece(habitacionActual);
                    if(!existe){
                        System.out.println("| > La habitacion ingresada no existe.");
                    }else{
                        equipo.setHabitacion(habitacionActual);
                        System.out.println("| > El equipo " + nombre + " ahora se encuentra en la habitacion " + habitacionActual);
                        escritura("| > El equipo " + nombre + " ahora se encuentra en la habitacion " + habitacionActual, true);
                    }
                } else if(opcion == 5){
                    System.out.print("| > Ingrese puntaje de desafio a quitar: ");
                    int puntaje = sc.nextInt();
                    boolean jugoDesafio = desafiosResueltos.desasociar(nombre, puntaje);
                    if(jugoDesafio){
                        equipo.setPuntajeTotal(equipo.getPuntajeTotal() - puntaje);
                        System.out.println("| > El equipo " + nombre + " ahora tiene un puntaje total de " + equipo.getPuntajeTotal() + " y ha resuelto los desafios "  + desafiosResueltos.obtenerValores(nombre).toString());
                        escritura("| > El equipo " + nombre + " se le ha quitado el desafio " + puntaje + " quedando con un total de " + equipo.getPuntajeTotal(), true);
                    }else{
                        System.out.println("| > El equipo " + nombre + " no ha jugado al desafio con puntaje " + puntaje);
                    }
                }
            }while(opcion != 0);
        }else{
            System.out.println("| > No existe el equipo ingresado.");
        }
    }
    public static void menuModificaionEquipo(String nombre){
        System.out.println("==================================================");
        System.out.println("|           MODIFICACION EQUIPO " + nombre.toUpperCase());
        System.out.println("==================================================");
        System.out.println("| 1. Modificar puntaje de salida                 |");
        sleepMilisegundos(150);
        System.out.println("| 2. Modificar puntaje total                     |");
        sleepMilisegundos(150);
        System.out.println("| 3. Modificar puntaje actual                    |");
        sleepMilisegundos(150);
        System.out.println("| 4. Modificar habitacion actual                 |");
        sleepMilisegundos(150);
        System.out.println("| 5. Quitar desafio resuelto y su respectivo     |");
        System.out.println("| puntaje del puntaje total                      |");
        sleepMilisegundos(150);
        System.out.println("| 0. <-- Volver <--                              |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    // ----  Desafios ----
    public static void cargarDesafio(DiccionarioAVL desafios, String linea){
        // Variables de desafio
        String nombre = "", tipo = "";
        int puntaje = 0;

        // Token guarda las cadenas de Strings entre los ;
        StringTokenizer token = new StringTokenizer(linea, ";");

        // cantTokens guarda la cantidad de tokens guardados
        int cantTokens = token.countTokens();

        // tokenAcutal guarda el token actual para analizar a que variable pertenece
        String tokenActual;

        // Desde 0 hasta la cantidad de tokens, analiza token por token y segun el valor de
        // i (veces que ya guardo cosas) determina a que variable le corresponde el token
        for(int i = 0; i < cantTokens; i++){
            tokenActual = token.nextToken();
            switch(i){
                case 0: puntaje = Integer.parseInt(tokenActual); break;
                case 1: nombre = tokenActual; break;
                case 2: tipo = tokenActual; 
            }
        }

        // Luego creo el desafio con las variables llenadas con los tokens
        Desafio nuevo = new Desafio(puntaje, nombre, tipo);

        // Guardo en el el AVL de desafios el nuevo desafio con el nombre como clave y "nuevo" como dato
        desafios.insertar(puntaje, nuevo);
    }
    public static void cargarDesafioManual(DiccionarioAVL desafios){
        Scanner sc = new Scanner(System.in);
        // Variables de desafio
        String nombre = "", tipo = "";
        int puntaje = 0;
        boolean existe;

        System.out.print("| > Ingrese nombre del desafio: ");
        nombre = sc.nextLine();
        System.out.print("| > Ingrese tipo: ");
        tipo = sc.nextLine();
        System.out.print("| > Ingrese puntaje: ");
        puntaje = sc.nextInt();
        existe = desafios.pertenece(puntaje);
        if(existe){
            System.out.println("| > El desafio de " + puntaje + " puntos ya existe.");
        }else{
            // Luego creo el desafio con las variables llenadas con los tokens
            Desafio nuevo = new Desafio(puntaje, nombre, tipo);

            // Guardo en el el AVL de desafios el nuevo desafio con el nombre como clave y "nuevo" como dato
            desafios.insertar(puntaje, nuevo);

            System.out.println("| > El desafio " + nuevo.toString() + " ha sido dado de alta");
            escritura("| > El desafio " + nuevo.toString() + " ha sido dado de alta", true);
        }
    }
    public static void eliminarDesafio(DiccionarioAVL desafios){
        Scanner sc = new Scanner(System.in);
        int puntaje = 0;
        boolean exito = false;

        System.out.print("| > Ingrese puntaje del desafio a eliminar: ");
        puntaje = sc.nextInt();

        exito = desafios.eliminar(puntaje);

        System.out.print("| > El desafio con un puntaje de " + puntaje);

        if(exito){
            System.out.println(" ha sido eliminado.");
            escritura("| > El desafio con un puntaje de " + puntaje + " ha sido eliminado.", true);
        }else{
            System.out.println(" no se ha encontrado.");
        }
        
    }
    public static void modificarDesafio(DiccionarioAVL desafios){
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        System.out.print("| > Ingrese puntaje del desafio: ");
        int puntaje = sc.nextInt();
        
        if(desafios.pertenece(puntaje)){
            do{
                menuModificacionDesafio(puntaje);
                opcion = sc.nextInt();
                if(opcion == 1){
                    System.out.print("| > Ingrese nombre: "); 
                    String nombre = sc.nextLine(); 
                    ((Desafio)desafios.recuperarDatos(puntaje)).setNombre(nombre);
                    System.out.println("| > El desafio de " + puntaje + " puntos ahora se llama " + nombre + ".");
                    escritura("| > El desafio de " + puntaje + " puntos ahora se llama " + nombre + ".", true);
                } else if(opcion == 2){
                    System.out.print("| > Ingrese tipo: "); 
                    String tipo = sc.next(); 
                    ((Desafio)desafios.recuperarDatos(puntaje)).setNombre(tipo);
                    System.out.println("| > El desafio de " + puntaje + " puntos ahora es de tipo " + tipo + ".");
                    escritura("| > El desafio de " + puntaje + " puntos ahora es de tipo " + tipo + ".", true);
                } 
            }while(opcion != 0);
        }else{
            System.out.println("| > No existe el desafio ingresado.");
        }
    }
    public static void menuModificacionDesafio(int puntaje){
        System.out.println("==================================================");
        System.out.println("|           MODIFICACION DESAFIO " + puntaje);
        System.out.println("==================================================");
        System.out.println("| 1. Modificar nombre                            |");
        sleepMilisegundos(150);
        System.out.println("| 2. Modificar tipo                              |");
        sleepMilisegundos(150);
        System.out.println("| 0. <-- Volver <--                              |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    // ----  Puertas ----
    public static void cargarPuerta(GrafoEtiq casa, String linea){
        // Variables de puerta
        int origen = 0, destino = 0, puntaje = 0;

        // Token guarda las cadenas de Strings entre los ;
        StringTokenizer token = new StringTokenizer(linea, ";");

        // cantTokens guarda la cantidad de tokens guardados
        int cantTokens = token.countTokens();

        // tokenAcutal guarda el token actual para analizar a que variable pertenece
        String tokenActual;

        // Desde 0 hasta la cantidad de tokens, analiza token por token y segun el valor de
        // i (veces que ya guardo cosas) determina a que variable le corresponde el token
        for(int i = 0; i < cantTokens; i++){
            tokenActual = token.nextToken();
            switch(i){
                case 0: origen = Integer.parseInt(tokenActual); break;
                case 1: destino = Integer.parseInt(tokenActual); break;
                case 2: puntaje = Integer.parseInt(tokenActual); 
            }
        }

        // Inserto la conexion entre las habitaciones
        casa.insertarArco(origen, destino, puntaje);
    }
    public static void cargarPuertasManual(GrafoEtiq casa, DiccionarioAVL habitaciones){
        Scanner sc = new Scanner(System.in);
        // Variables de puerta
        int origen = 0, destino = 0, puntaje = 0;
        boolean existe, existeHabitacion;
        // Ingreso de datos, utiliza "mucho" tiempo de ejecucion, ya que recorre el grafo en orden n
        // para buscar si los datos ingresados pueden ser utilizados, pero da una mejor experiencia al usuario
        System.out.print("| > Ingrese codigo de la habitacion de origen: ");
        origen = sc.nextInt();
        existeHabitacion = habitaciones.pertenece(origen);
        if(!existeHabitacion){
            System.out.println("| > No existe esa habitacion.                    |");
        }else{
            System.out.print("| > Ingrese el del destino: ");
            destino = sc.nextInt();
            existeHabitacion = habitaciones.pertenece(destino);
            if(!existeHabitacion){
                System.out.println("| > No existe esa habitacion.                    |");
            }else{
                existe = casa.existeArco(origen, destino);
                if(existe){
                    System.out.println("| > La puerta de la habitacion " + origen + " a la\n| habitacion " + destino + " ya existe.");
                }else{
                    System.out.print("| > Ingrese puntaje necesario: ");
                    puntaje = sc.nextInt();
                    
                    // Inserto la conexion entre las habitaciones
                    casa.insertarArco(origen, destino, puntaje); 

                    System.out.println("| > La puerta de " + origen + " a " + destino + " con un puntaje de " + puntaje + " puntos ha sido dada de alta.");
                    escritura("| > La puerta de " + origen + " a " + destino + " con un puntaje de " + puntaje + " puntos ha sido dada de alta.", true);
                }
            }   
        }
    }
    public static void eliminarPuerta(GrafoEtiq casa, DiccionarioAVL habitaciones){
        Scanner sc = new Scanner(System.in);
        int habOrigen = 0, habDestino = 0;
        boolean exito = false;

        System.out.print("| > Ingrese origen de la puerta: ");
        habOrigen = sc.nextInt();
        if(habitaciones.pertenece(habOrigen)){
            System.out.print("| > Ingrese destino de la puerta: ");
            habDestino = sc.nextInt();
            
            if(habitaciones.pertenece(habDestino)){
                System.out.print("| > La puerta de la habitacion " + habOrigen + " a\n| la habitacion " + habDestino);
                
                if(casa.eliminarArco(habOrigen, habDestino)){
                    System.out.println(" se ha eliminado. ");
                    escritura("| > La puerta de la habitacion " + habOrigen + " a la habitacion " + habDestino + " ha sido eliminada.", true);
                }else{
                    System.out.println(" no se ha encontrado.");
                }
            }else{
                System.out.println("| > La habitacion de destino " + habDestino + " no existe.");
            }
        }else{
            System.out.println("| > La habitacion de origen " + habOrigen + " no existe.");
        }
    }
    public static void modificarPuerta(GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        boolean existe;
        int origen, destino;
        
        System.out.print("| > Ingrese origen de la puerta: ");
        origen = sc.nextInt();
        System.out.print("| > Ingrese destino de la puerta: ");
        destino = sc.nextInt();
        existe = casa.existeArco(origen, destino);
        if(!existe){
            System.out.println("| > La puerta de la habitacion " + origen + " a la " + destino + " no existe.");
        }else{
            System.out.print("| > Ingrese nuevo puntaje de la puerta: ");
            int nuevoPuntaje = sc.nextInt();
            casa.cambiarEtiqueta(origen, destino, nuevoPuntaje);
            System.out.println("| > La puerta de " + origen + " a " + destino + " ha cambiado su puntaje a " + nuevoPuntaje + ".");
            escritura("| > La puerta de " + origen + " a " + destino + " ha cambiado su puntaje a " + nuevoPuntaje + ".", true);
        }
    }
    // ---- Desafios Resueltos ----
    public static void cargarDesafiosResueltos(MapeoAMuchos desafiosResueltos, String linea){
        // Variables de mapeo
        String nombre = "";
        Lista rango = new Lista();

        // Token guarda las cadenas de Strings entre los ;
        StringTokenizer token = new StringTokenizer(linea, ";");

        // cantTokens guarda la cantidad de tokens guardados
        int cantTokens = token.countTokens();

        // tokenAcutal guarda el token actual para analizar a que variable pertenece
        String tokenActual;

        // Desde 0 hasta la cantidad de tokens, analiza token por token y segun el valor de
        // i (veces que ya guardo cosas) determina a que variable le corresponde el token
        for(int i = 0; i < cantTokens; i++){
            tokenActual = token.nextToken();
            if(i == 0){
                nombre = tokenActual;
            }else{
                rango.insertar(Integer.parseInt(tokenActual), 1);
            }
        }

        // Inserto la conexion entre las habitaciones
        desafiosResueltos.asociarLista(nombre, rango);
    }


    // =================================
    //   Altas, bajas y modificaciones
    // =================================
    public static void abm(DiccionarioAVL habitaciones, GrafoEtiq casa, DiccionarioAVL desafios, DiccionarioHash equipos, MapeoAMuchos desafiosResueltos){
        Scanner sc = new Scanner(System.in);
        int opcion, opcionPrincipal;
        do{
            menuABM();
            opcionPrincipal = sc.nextInt();

            if(opcionPrincipal == 1){
                do{
                    menuTipoDeABM("|                   A L T A S                    |");
                    opcion = sc.nextInt();
                    switch(opcion){
                        case 1: cargarHabitacionManual(habitaciones, casa); break;
                        case 2: cargarDesafioManual(desafios); break;
                        case 3: cargarEquipoManual(equipos); break;
                        case 4: cargarPuertasManual(casa, habitaciones);
                    }
                }while(opcion != 0);
                
            } else if(opcionPrincipal == 2){
                do{
                    menuTipoDeABM("|                   B A J A S                    |");
                    opcion = sc.nextInt();
                    switch(opcion){
                        case 1: eliminarHabitacion(habitaciones, casa, equipos); break;
                        case 2: eliminarDesafio(desafios); break;
                        case 3: eliminarEquipo(equipos, desafiosResueltos); break;
                        case 4: eliminarPuerta(casa, habitaciones);
                    }
                }while(opcion != 0);
            } else if(opcionPrincipal == 3){
                do{
                    menuTipoDeABM("|          M O D I F I C A C I O N E S           |");
                    opcion = sc.nextInt();
                    switch(opcion){
                        case 1: modificarHabitacion(habitaciones, casa); break;
                        case 2: modificarDesafio(desafios); break;
                        case 3: modificarEquipo(equipos, habitaciones, desafiosResueltos); break;
                        case 4: modificarPuerta(casa);
                    }
                }while(opcion != 0);
            }
        }while(opcionPrincipal != 0);
    }
    public static void menuABM(){
        System.out.println("==================================================");
        System.out.println("|          ALTAS, BAJAS Y MODIFICACIONES         |");
        System.out.println("==================================================");
        System.out.println("| 1. Dar de alta                                 |");
        sleepMilisegundos(150);
        System.out.println("| 2. Dar de baja                                 |");
        sleepMilisegundos(150);
        System.out.println("| 3. Modificar                                   |");
        sleepMilisegundos(150);
        System.out.println("| 0. <-- Volver <--                              |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    public static void menuTipoDeABM(String titulo){
        System.out.println("==================================================");
        System.out.println(titulo);
        System.out.println("==================================================");
        System.out.println("| 1. Habitaciones                                |");
        sleepMilisegundos(150);
        System.out.println("| 2. Desafios                                    |");
        sleepMilisegundos(150);
        System.out.println("| 3. Equipos                                     |");
        sleepMilisegundos(150);
        System.out.println("| 4. Puertas                                     |");
        sleepMilisegundos(150);
        System.out.println("| 0. <-- Volver <--                              |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
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
                case 2: habitacionesContiguas(casa, habitaciones); break;
                case 3: esPosibleLlegar(casa, habitaciones); break;
                case 4: sinPasar(casa, habitaciones);break;
                case 5: mostrarTodasHabitaciones(habitaciones);
            }
        }while(opcion != 0);
    }
    // ---- Menu ----
    public static void menuConsultaHabitaciones(){
        System.out.println("==================================================");
        System.out.println("|            H A B I T A C I O N E S             |");
        System.out.println("==================================================");
        System.out.println("| 1. Mostrar habitacion                          |");
        sleepMilisegundos(150);
        System.out.println("| 2. Habitaciones contiguas                      |");
        sleepMilisegundos(150);
        System.out.println("| 3. Es posible llegar                           |");
        sleepMilisegundos(150);
        System.out.println("| 4. Sin pasar por                               |");
        sleepMilisegundos(150);
        System.out.println("| 5. Mostrar todas                               |");
        sleepMilisegundos(150);
        System.out.println("| 0. <-- Volver <--                              |");
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
            System.out.println("| La habitacion con clave " + numero + " es el/la " + ((Habitacion)dato).toString());
        }else{
            System.out.println("| La habitacion con clave " + numero + " no existe.");
        }
        
        System.out.println("|------------------------------------------------|");
    }
    // ---- Habitaciones Contiguas ----
    public static void habitacionesContiguas(GrafoEtiq casa, DiccionarioAVL habitaciones){
        // Pregunto que habitacion se quiere consultar
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese numero de habitacion: ");
        int numero = sc.nextInt();
        if(habitaciones.pertenece(numero)){
            int i = 1;
            Lista tuplas = casa.nodosAdyacentes(numero);
            if(tuplas.longitud() > 0){
                System.out.println("|------------------------------------------------|");
                System.out.println("| > La habitacion " + numero  + " es contigua con: ");
                while(!tuplas.esVacia()){
                    System.out.println("| --> " + ((Lista)tuplas.recuperar(i)).recuperar(1) + " con un puntaje necesario de " + ((Lista)tuplas.recuperar(i)).recuperar(2) + " puntos.");
                    tuplas.eliminar(1);
                }
            }else{
                System.out.println("| > La habitacion " + numero + " no tiene habitaciones contiguas.");
            }
            
        }else{
            System.out.println("| > La habitacion " + numero + " no existe.");
        }
    }
    // ---- Es Posible Llegar ---- 
    public static void esPosibleLlegar(GrafoEtiq casa, DiccionarioAVL habitaciones){
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese numero de la primera habitacion: ");
        int hab1 = sc.nextInt();
        if(habitaciones.pertenece(hab1)){
            System.out.print("| > Ingrese numero de la segunda habitacion: ");
            int hab2 = sc.nextInt();
            if(habitaciones.pertenece(hab2)){
                System.out.print("| > Ingrese puntaje acumulado: ");
                int puntaje = sc.nextInt();
                System.out.println("|------------------------------------------------|");
        
                if(casa.existeCaminoEtiquetado(hab1, hab2, puntaje)){
                    System.out.println("| > Es posible llegar con " + puntaje + " puntos.");
                }else{
                    System.out.println("| > No es posible llegar");
                }  
            }else{
                System.out.println("| > La habitacion " + hab2 + " no existe.");
            }
        }else{
            System.out.println("| > La habitacion " + hab1 + " no existe.");
        }
    }
    // ---- Sin Pasar Por ----
    public static void sinPasar(GrafoEtiq casa, DiccionarioAVL habitaciones){
        // Entrada de datos
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese numero de la primera habitacion: ");
        int hab1 = sc.nextInt();
        if(habitaciones.pertenece(hab1)){
            System.out.print("| > Ingrese numero de la segunda habitacion: ");
            int hab2 = sc.nextInt();
            if(habitaciones.pertenece(hab2)){
                System.out.print("| > Ingrese numero habitacion a saltear: ");
                int hab3 = sc.nextInt();
                if(habitaciones.pertenece(hab3)){
                    System.out.print("| > Ingrese puntaje acumulado: ");
                    int puntaje = sc.nextInt();
                    System.out.println("|------------------------------------------------|");
            
                    // Lista con los posibles caminos
                    Lista caminosPosibles = casa.caminosSinPasarPor(hab1, hab2, hab3, puntaje);
            
                    // Muestra los caminos posibles
                    if(caminosPosibles.longitud() > 0){
                        System.out.println("| > Los caminos posibles para ir de la habitacion " + hab1 + " a la " + hab2);
                        System.out.println("| salteando la habitacion " + hab3 + ", y con " + puntaje + " puntos son: ");
                        for(int i=1; i<=caminosPosibles.longitud();i++){
                            System.out.println("| --> " + caminosPosibles.recuperar(i).toString());
                        }
                    }else{
                        System.out.println("| > No hay caminos posibles para ir de la habitacion " + hab1 + " a la " + hab2);
                        System.out.println("| salteando la habitacion " + hab3 + ", y con " + puntaje + " puntos.");
                    }
                }else{
                    System.out.println("| > La habitacion ingresada no existe.");
                }
            }else{
                System.out.println("| > La habitacion ingresada no existe.");
            }
        }else{
            System.out.println("| > La habitacion ingresada no existe.");
        }
    }
    // ---- Mostrar Todas las Habitaciones ----
    public static void mostrarTodasHabitaciones(DiccionarioAVL habitaciones){
        if(habitaciones.vacio()){
            System.out.println("| > No hay habitaciones cargadas en el sistema.");
        }else{
            Lista todas = habitaciones.listarDatos();
            System.out.println("| > Las habitaciones operativas son: ");
            for(int i = 1; i <= todas.longitud(); i++){
                System.out.println("| --> " + ((Habitacion)todas.recuperar(i)).toString());
            }
        }   
    }


    // =========================
    //     Consulta Desafios
    // =========================
    public static void consultaDesafios(DiccionarioAVL desafios, MapeoAMuchos desafiosResueltos, DiccionarioHash equipos){
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{
            menuConsultaDesafios();
            opcion = sc.nextInt();

            switch(opcion){
                case 1: mostrarDesafio(desafios); break;
                case 2: mostrarDesafiosResueltos(equipos, desafiosResueltos); break;
                case 3: mostrarDesafiosTipo(desafios); break;
                case 4: mostrarTodosDesafios(desafios);
            }
        }while(opcion != 0);
    }
    // --- Menu ----
    public static void menuConsultaDesafios(){
        System.out.println("==================================================");
        System.out.println("|              D E S A F I O S                   |");
        System.out.println("==================================================");
        System.out.println("| 1. Mostrar informacion de desafio              |");
        sleepMilisegundos(150);
        System.out.println("| 2. Mostrar desafios resueltos                  |");
        sleepMilisegundos(150);
        System.out.println("| 3. Mostrar desafios tipo                       |");
        sleepMilisegundos(150);
        System.out.println("| 4. Mostrar todos los desafios                  |");
        sleepMilisegundos(150);
        System.out.println("| 0. <-- Volver <--                              |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    // --- Mostrar desafio ----
    public static void mostrarDesafio(DiccionarioAVL desafios){
        // Pregunto que desafio se quiere consultar
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese puntaje del desafio: ");
        int numero = sc.nextInt();
        System.out.println("|------------------------------------------------|");

        // Busco en el arbol de desafios los datos del desafio
        Object dato = desafios.recuperarDatos(numero);
        
        // Si el desafio existe, muestro sus datos
        if(dato != null){
            System.out.println("| > El desafio con clave " + numero + " es el " + ((Desafio)dato).toString());
        }else{
            System.out.println("| > El desafio con clave " + numero + " no existe.");
        }
        
        System.out.println("|------------------------------------------------|");
    }
    // ---- Mostrar Desafios Resueltos ---- 
    public static void mostrarDesafiosResueltos(DiccionarioHash equipos, MapeoAMuchos desafiosResueltos){
        Scanner sc = new Scanner(System.in);
        String nombre;
        System.out.print("| > Ingrese nombre del equipo: ");
        nombre = sc.nextLine();
        if(!equipos.pertenece(nombre)){
            System.out.println("| > El equipo no existe. ");
        }else{
            System.out.println("| > Los desafios resueltos por el equipo " + nombre + " son: " + desafiosResueltos.obtenerValores(nombre).toString());
        }
    }
    // ---- Mostrar Desafios Tipo ----
    public static void mostrarDesafiosTipo(DiccionarioAVL desafios){
        Scanner sc = new Scanner(System.in);
        int puntaje1, puntaje2;
        String tipo;
        Desafio desafioActual;

        System.out.print("| > Ingrese puntaje minimo: ");
        puntaje1 = sc.nextInt();
        System.out.print("| > Ingrese puntaje maximo: ");
        puntaje2 = sc.nextInt();
        System.out.print("| > Ingrese tipo: ");
        tipo = sc.next();

        // Guardo en la lista desafiosRango los datos de los desafios con el puntaje entre puntaje1 y puntaje2
        Lista desafiosRango = desafios.listarRangoDatos(puntaje1, puntaje2);

        System.out.println("| > Los desafios con un puntaje entre " + puntaje1 + " y el puntaje " + puntaje2 + " son: ");

        // Recorro la lista y pregunto si el tipo de cada dato de la lista es igual con el solicitado, si es asi, imprime todos los datos por pantalla
        for(int i = 1; i <= desafiosRango.longitud(); i++){
            desafioActual = ((Desafio)desafiosRango.recuperar(i));
            if(desafioActual != null && desafioActual.getTipo().equals(tipo)){
                System.out.println("| --> " + desafioActual.toString() + " con un puntaje de " + desafioActual.getPuntaje() + ". ");
            }
        }
    }
    // ---- Mostrar Todos ----
    public static void mostrarTodosDesafios(DiccionarioAVL desafios){
        if(desafios.vacio()){
            System.out.println("| > No hay desafios cargados en el sistema.");
        }else{
            Lista todos = desafios.listarDatos();
            System.out.println("| > Los deafios operativos son: ");
            for(int i = 1; i <= todos.longitud(); i++){
                System.out.println("| --> " + ((Desafio)todos.recuperar(i)).toString());
            }
        }
    }


    // =========================
    //  Consulta Participantes
    // =========================
    public static void consultaParticipantes(DiccionarioHash equipos, MapeoAMuchos desafiosResueltos, DiccionarioAVL desafios, DiccionarioAVL habitaciones, GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{
            menuConsultaParticipantes();
            opcion = sc.nextInt();

            switch(opcion){
                case 1: mostrarEquipo(equipos, desafiosResueltos); break;
                case 2: jugarDesafio(equipos, desafiosResueltos, desafios); break;
                case 3: pasarHabitacion(equipos, habitaciones, casa); break;
                case 4: puedeSalir(equipos, habitaciones); break;
                case 5: mostrarTodosEquipos(equipos);
            }
        }while(opcion != 0);
    }
    // --- Menu ----
    public static void menuConsultaParticipantes(){
        System.out.println("==================================================");
        System.out.println("|          P A R T I C I P A N T E S             |");
        System.out.println("==================================================");
        System.out.println("| 1. Mostrar informacion de equipo               |");
        sleepMilisegundos(150);
        System.out.println("| 2. Jugar desafio                               |");
        sleepMilisegundos(150);
        System.out.println("| 3. Pasar a habitacion                          |");
        sleepMilisegundos(150);
        System.out.println("| 4. Puede salir                                 |");
        sleepMilisegundos(150);
        System.out.println("| 5. Mostrar todos                               |");
        sleepMilisegundos(150);
        System.out.println("| 0. <-- Volver <--                              |");
        System.out.print("|");
        for(int i = 0; i <48; i++){
            System.out.print("-");
            sleepMilisegundos(15);
        }
        System.out.println("|");
        sleepMilisegundos(150);
        System.out.print("| > Ingrese opcion: ");
    }
    // ---- Mostrar equipo ----
    public static void mostrarEquipo(DiccionarioHash equipos, MapeoAMuchos desafiosResueltos){
        // Pregunto que equipo se quiere consultar
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese nombre del equipo: ");
        String nombre = sc.nextLine();
        System.out.println("|------------------------------------------------|");

        Equipo equipoIngresado = (Equipo)equipos.recuperarDatos(nombre);
        if(equipoIngresado != null){
            if(desafiosResueltos.obtenerValores(nombre).esVacia()){
                System.out.println("| > " + equipoIngresado.toString() + "\n| no ha completado ningun desafio. ");
            }else{
                System.out.println("| > " + equipoIngresado.toString() + "\n| y ha completado los desafios " + desafiosResueltos.obtenerValores(nombre).toString());
            }
        }else{
            System.out.println("| > El equipo no existe.");
        }
    } 
    // ---- Jugar Desafio ----
    public static void jugarDesafio(DiccionarioHash equipos, MapeoAMuchos desafiosResueltos, DiccionarioAVL desafios){
        // Pregunto que equipo se quiere consultar
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese nombre del equipo: ");
        String nombre = sc.nextLine();
        if(equipos.pertenece(nombre)){
            System.out.print("| > Ingrese puntaje del desafio: ");
            int puntaje = sc.nextInt();
            if(desafios.pertenece(puntaje)){
                System.out.println("|------------------------------------------------|");
                // Si el desafio no esta en la lista de desafios resueltos por el equipo
                if(desafiosResueltos.obtenerValores(nombre).localizar(puntaje) == -1){
                    // Asocio el nuevo desafio al equipo
                    desafiosResueltos.asociar(nombre, puntaje);
                    // Le agrego al equipo el puntaje de este desafio al puntaje acutal del equipo
                    Equipo equipo = (Equipo)equipos.recuperarDatos(nombre);
                    equipo.setPuntajeActual(equipo.getPuntajeActual() + puntaje);

                    System.out.println("| > El equipo " + nombre + " acaba de resolver el desafio " + ((Desafio)desafios.recuperarDatos(puntaje)).toString());
                    escritura("| > El equipo " + nombre + " acaba de resolver el desafio de " + puntaje + " puntos." , true);
                }else{
                    System.out.println("| > El equipo " + nombre + " ya ha resuelto el desafio " + ((Desafio)desafios.recuperarDatos(puntaje)).toString());
                }
            }else{
                System.out.println("| > No existe el desafio ingresado.");
            }
        }else{
            System.out.println("| > No existe el equipo ingresado.");
        }

    }
    // ---- Pasar Habitacion ----
    public static void pasarHabitacion(DiccionarioHash equipos, DiccionarioAVL habitaciones, GrafoEtiq casa){
        // Pregunto que equipo quiere pasar de habitacion
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese nombre del equipo: ");
        String nombre = sc.nextLine();
        if(equipos.pertenece(nombre)){
            System.out.print("| > Ingrese la habitacion a la cual el equipo quiere ir: ");
            int hab = sc.nextInt();
            if(habitaciones.pertenece(hab)){
                Equipo equipo = (Equipo)equipos.recuperarDatos(nombre);
                // Si la habitacion actual del equipo tiene puerta a la habitacion a la cual se quiere ir
                int habActual = equipo.getHabitacionActual();
                if(habitaciones.pertenece(habActual)){
                    if(casa.existeArco(habActual, hab)){
                        if(equipo.getPuntajeActual() >= casa.etiquetaArco(habActual, hab)){
                            // El equipo pasa de habitacion
                            equipo.setHabitacion(hab);
                            // Sumo el puntaje actual al total y reinicio el actual
                            equipo.setPuntajeTotal(equipo.getPuntajeTotal() + equipo.getPuntajeActual());
                            equipo.setPuntajeActual(0);
                            System.out.println("| > El equipo " + nombre + " ha pasado a la habitacion " + hab + ". ");
                            escritura("| > El equipo " + nombre + " ha pasado a la habitacion " + hab + ". ", true);
                        }else{
                            System.out.println("| > El equipo tiene " + equipo.getPuntajeActual() + " puntos actuales de " + casa.etiquetaArco(habActual, hab) + " puntos necesarios.");
                        }
                    }else{
                        System.out.println("| > La habitacion actual " + habActual + " no tiene puerta a " + hab);
                    }
                }else{
                    System.out.println("| > El equipo no esta en una habitacion existente\n| debe moverlo de forma manual en modificar equipo.");
                }
            }else{
                System.out.println("| > La habitacion " + hab + " no existe.");
            }
        }else{
            System.out.println("| > El equipo " + nombre + " no existe.");
        }

    }
    // ---- Puede Salir ----
    public static void puedeSalir(DiccionarioHash equipos, DiccionarioAVL habitaciones){
        // Pregunto que equipo quiere pasar de habitacion
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese nombre del equipo: ");
        String nombre = sc.nextLine();
        System.out.println("|------------------------------------------------|");

        if(equipos.pertenece(nombre)){
            Equipo equipo = (Equipo)equipos.recuperarDatos(nombre);
            Habitacion habEquipo = (Habitacion)habitaciones.recuperarDatos(equipo.getHabitacionActual());
            if(habEquipo != null){
                if(habEquipo.getSalida()){
                    if(equipo.getPuntajeTotal() >= equipo.getPuntajeSalida()){
                        System.out.println("| > El equipo " + nombre + " puede salir de la casa!");
                    }else{
                        System.out.println("| > El equipo " + nombre + " tiene " + equipo.getPuntajeTotal() + " puntos\n| de " + equipo.getPuntajeSalida() + " puntos necesarios para salir.");
                    }
                }else{
                    System.out.println("| > El equipo no esta en una habitacion con salida.");
                }
            }else{
                System.out.println("| > El equipo no esta en una habitacion existente\n| debe moverlo de forma manual en modificar equipo.");
            }
        }else{
            System.out.println("| > El equipo no existe.");
        }
    }
    // ---- Mostrar Todos ----
    public static void mostrarTodosEquipos(DiccionarioHash equipos){
        if(equipos.esVacia()){
            System.out.println("| > No hay equipos cargados en el sistema.");
        }else{
            Lista todos = equipos.listarDatos();
            System.out.println("| > Los deafios operativos son: ");
            for(int i = 1; i <= todos.longitud(); i++){
                System.out.println("| --> " + ((Equipo)todos.recuperar(i)).toString());
            }
        }
    }


    // =========================
    //      Consulta General
    // =========================
    public static void consultaGeneral(DiccionarioAVL habitaciones, GrafoEtiq casa, DiccionarioAVL desafios, DiccionarioHash equipos, MapeoAMuchos desafiosResueltos){
        System.out.println("==================================================");
        System.out.println("|        E S T A D O  D E L  S I S T E M A       |");        
        System.out.println("==================================================");
        System.out.println("|             H A B I T A C I O N E S            |");
        System.out.println("|------------------------------------------------|");
        System.out.println(habitaciones.toString());
        System.out.println("|------------------------------------------------|");
        System.out.println("|                    C A S A                     |");
        System.out.println("|------------------------------------------------|");
        System.out.println(casa.toString());
        System.out.println("|------------------------------------------------|");
        System.out.println("|             D E S A F I O S                    |");
        System.out.println("|------------------------------------------------|");
        System.out.println(desafios.toString());
        System.out.println("|------------------------------------------------|");
        System.out.println("|                E Q U I P O S                   |");
        System.out.println("|------------------------------------------------|");
        System.out.println(equipos.toString());
        System.out.println("|------------------------------------------------|");
        System.out.println("|      D E S A F I O S   R E S U E L T O S       |");
        System.out.println("|------------------------------------------------|");
        System.out.println(desafiosResueltos.toString());
    }


    // =========================
    //         Carteles
    // =========================
    public static void cartelInicio(){
        String titulo = "E S C A P E   H O U S E";
        String costado1 = "|            ";
        String costado2 = "             |";
        int esperaRapida = 10;
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
    public static void cartelMenuPrincipal(){
        System.out.println("==================================================");
        System.out.println("|           M E N U   P R I N C I P A L          |");
        System.out.println("==================================================");
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
    public static void cartelFinal(){
        String nombre = "Manuel Felipe Triñanes (FAI-2738)";
        System.out.println("==================================================\n\n");
        for(int i = 0; i <50; i++){
            System.out.print("=");
            sleepMilisegundos(10);
        }
        System.out.println("\n|           R E A L I Z A D O  P O R             |");
        for(int i = 0; i <50; i++){
            System.out.print("=");
            sleepMilisegundos(10);
        }
        System.out.print("\n|       ");
        for(int i = 0; i < nombre.length(); i++){
            System.out.print(nombre.charAt(i));
            sleepMilisegundos(80);
        }
        System.out.println("        |");
        for(int i = 0; i <50; i++){
            System.out.print("=");
            sleepMilisegundos(10);
        }
        System.out.println("");
    }
    public static void sleepMilisegundos(int tiempo){
        try{
            TimeUnit.MILLISECONDS.sleep(tiempo);
        }
        catch(Exception e) {}
    }
}
