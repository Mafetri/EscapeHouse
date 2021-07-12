import estructuras.arbol.*;
import estructuras.datos.*;
import estructuras.hash.*;
import estructuras.grafo.*;
import estructuras.lista.Lista;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.io.*;
import java.util.StringTokenizer;

public class EscapeRoom {
    public static void main(String[] args) {
        // Escaner de ingreso
        Scanner sc = new Scanner(System.in);

        // Estructuras
        GrafoEtiq casa = new GrafoEtiq();
        DiccionarioAVL habitaciones = new DiccionarioAVL();
        DiccionarioAVL desafios = new DiccionarioAVL();
        DiccionarioHash equipos = new DiccionarioHash();

        // Carteles de inicio
        cartelInicio();
        cartelOpcionesInicio();

        // Leectura de opcion inicio
        int opcion = sc.nextInt();

        if(opcion != 0){
            switch (opcion) {
                case 1: leectura("D:\\Archivos\\Documentos\\Facultad\\Estructuras de Datos\\Codigo\\EDAT2021VSC\\TPFinal\\src\\partidas\\PartidaNueva.txt", casa, habitaciones, desafios, equipos);break;
                case 2: leectura("D:\\Archivos\\Documentos\\Facultad\\Estructuras de Datos\\Codigo\\EDAT2021VSC\\TPFinal\\src\\partidas\\PartidaNueva.txt", casa, habitaciones, desafios, equipos);
            }

            do{
                cartelMenuPrincipal();
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1: abm(habitaciones, casa, desafios,equipos);break;
                    case 2: consultaHabitaciones(habitaciones, casa); break;
                    case 3: consultaDesafios(desafios); break;
                    case 4: consultaParticipantes(); break;
                    case 5: consultaGeneral();
                }
            }while(opcion != 0);


        }
        sc.close();
        cartelFinal();
        


    }

    // =================================
    //        Leectura de .txt
    // =================================
    public static void leectura(String ubicacion, GrafoEtiq casa, DiccionarioAVL habitaciones, DiccionarioAVL desafios, DiccionarioHash equipos){
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
                    case 'P': cargarPuerta(casa, linea);
                }
            }
            archivo.close();
        }catch(IOException e){
            System.out.println("| > Error " + e);
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
        do{
            System.out.print("| > Indique codigo de habitacion: ");
            codigo = sc.nextInt();
            existe = habitaciones.pertenece(codigo);
            if(existe){
                System.out.println("| > La habitacion con codigo " + codigo + " ya existe.");
            }
        }while(existe);
        System.out.print("| > Indique nombre de habitacion: ");
        nombre = sc.next();
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

        System.out.println("| > La habitacion " + nueva.toString() + " ha sido dado de alta");
    }
    public static void eliminarHabitacion(DiccionarioAVL habitaciones, GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        int codigo = 0;
        boolean exitoHab = false, exitoCasa = false;
        System.out.println("| > Indique codigo de habitacion a eliminar: ");
        codigo = sc.nextInt();

        // Elimino la habitacion de las habitaciones y de la casa
        exitoHab = habitaciones.eliminar(codigo);
        exitoCasa = casa.eliminarVertice(codigo);

        System.out.print("| > La habitacion con codigo " + codigo);
        if(exitoHab && exitoCasa){
            System.out.println(" ha sido eliminada.");
        }else{
            System.out.println(" no se ha encontrado.");
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
                } else if(opcion == 2){
                    System.out.print("| > Ingrese nueva planta: "); 
                    int planta = sc.nextInt(); 
                    ((Habitacion)habitaciones.recuperarDatos(codigo)).setPlanta(planta);
                } else if(opcion == 3){
                    System.out.print("| > Ingrese metros cuadrados: "); 
                    int metros = sc.nextInt(); 
                    ((Habitacion)habitaciones.recuperarDatos(codigo)).setMetrosCuadrados(metros);
                } else if(opcion == 4){
                    System.out.print("| > Indique si tiene salida: (s/n)");
                    char salida;
                    do{
                        salida = sc.next().toLowerCase().charAt(0);
                        if(salida == 's'){
                            ((Habitacion)habitaciones.recuperarDatos(codigo)).setSalida(true);
                        }else if(salida == 'n'){
                            ((Habitacion)habitaciones.recuperarDatos(codigo)).setSalida(false);
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
        do{
            System.out.print("| > Ingrese nombre del equipo: ");
            nombre = sc.nextLine();
            existe = equipos.pertenece(nombre);
            if(existe){
                System.out.println("| > El equipo " + nombre + " ya existe.");
            }
        }while(existe);
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
    }
    public static void eliminarEquipo(DiccionarioHash equipos){
        Scanner sc = new Scanner(System.in);
        String nombre = "";
        boolean exito = false;

        System.out.println("| > Ingrese nombre del equipo a eliminar: ");
        nombre = sc.nextLine();

        exito = equipos.eliminar(nombre);

        System.out.print("| > El equipo " + nombre);
        if(exito){
            System.out.println(" ha sido eliminado.");
        }else{
            System.out.println(" no se ha encontrado.");
        }
    }
    public static void modificarEquipo(DiccionarioHash equipos, DiccionarioAVL habitaciones){
        Scanner sc = new Scanner(System.in);
        int opcion = 0, habitacionActual;
        boolean existe;
        System.out.print("| > Ingrese nombre de equipo a modificar: ");
        String nombre = sc.nextLine();
        if(equipos.pertenece(nombre)){
            do{
                menuModificaionEquipo(nombre);
                opcion = sc.nextInt();
                if(opcion == 1){
                    System.out.print("| > Ingrese puntaje de salida: "); 
                    int puntajeSalida = sc.nextInt(); 
                    ((Equipo)equipos.recuperarDatos(nombre)).setPuntajeSalida(puntajeSalida);
                } else if(opcion == 2){
                    System.out.print("| > Ingrese puntaje total: "); 
                    int puntajeTotal = sc.nextInt(); 
                    ((Equipo)equipos.recuperarDatos(nombre)).setPuntajeTotal(puntajeTotal);
                } else if(opcion == 3){
                    System.out.print("| > Ingrese puntaje actual: "); 
                    int puntajeActual = sc.nextInt(); 
                    ((Equipo)equipos.recuperarDatos(nombre)).setPuntajeActual(puntajeActual);
                } else if(opcion == 4){
                    do{
                        System.out.print("| > Ingrese habitacion actual: ");
                        habitacionActual = sc.nextInt();
                        existe = habitaciones.pertenece(habitacionActual);
                        if(!existe){
                            System.out.println("| > La habitacion ingresada no existe.");
                        }
                    }while(!existe);
                    ((Equipo)equipos.recuperarDatos(nombre)).setHabitacion(habitacionActual);
                }
            }while(opcion != 0);
        }else{
            System.out.println("| > No existe el equipo ingresado.");
        }
    }
    public static void menuModificaionEquipo(String nombre){
        System.out.println("==================================================");
        System.out.println("|           MODIFICACION EQUIPO " + nombre);
        System.out.println("==================================================");
        System.out.println("| 1. Modificar puntaje de salida                 |");
        sleepMilisegundos(150);
        System.out.println("| 2. Modificar puntaje total                     |");
        sleepMilisegundos(150);
        System.out.println("| 3. Modificar puntaje actual                    |");
        sleepMilisegundos(150);
        System.out.println("| 4. Modificar habitacion actual                 |");
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
        do{
            System.out.print("| > Ingrese puntaje: ");
            puntaje = sc.nextInt();
            existe = desafios.pertenece(puntaje);
            if(existe){
                System.out.println("| > El desafio de " + puntaje + " puntos ya existe.");
            }
        }while(existe);
        
        // Luego creo el desafio con las variables llenadas con los tokens
        Desafio nuevo = new Desafio(puntaje, nombre, tipo);

        // Guardo en el el AVL de desafios el nuevo desafio con el nombre como clave y "nuevo" como dato
        desafios.insertar(puntaje, nuevo);

        System.out.println("| > El desafio " + nuevo.toString() + " ha sido dado de alta");
    }
    public static void eliminarDesafio(DiccionarioAVL desafios){
        Scanner sc = new Scanner(System.in);
        int puntaje = 0;
        boolean exito = false;

        System.out.println("| > Ingrese puntaje del desafio a eliminar: ");
        puntaje = sc.nextInt();

        exito = desafios.eliminar(puntaje);

        System.out.print("| > El desafio con un puntaje de " + puntaje);

        if(exito){
            System.out.println(" ha sido eliminado.");
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
                } else if(opcion == 2){
                    System.out.print("| > Ingrese tipo: "); 
                    String tipo = sc.nextLine(); 
                    ((Desafio)desafios.recuperarDatos(puntaje)).setNombre(tipo);
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
    public static void cargarPuertasManual(GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        // Variables de puerta
        int origen = 0, destino = 0, puntaje = 0;
        boolean existe, existeHabitacion;
        // Ingreso de datos, utiliza "mucho" tiempo de ejecucion, ya que recorre el grafo en orden n
        // para buscar si los datos ingresados pueden ser utilizados, pero da una mejor experiencia al usuario
        do{
            do{
                System.out.print("| > Ingrese codigo de la habitacion de origen: ");
                origen = sc.nextInt();
                existeHabitacion = casa.existeVertice(origen);
                if(!existeHabitacion){
                    System.out.println("| > No existe esa habitacion.                    |");
                }
            }while(!existeHabitacion);
            do{
                System.out.print("| > Ingrese el del destino: ");
                destino = sc.nextInt();
                existeHabitacion = casa.existeVertice(destino);
                if(!existeHabitacion){
                    System.out.println("| > No existe esa habitacion.                    |");
                }
            }while(!existeHabitacion);
            existe = casa.existeArco(origen, destino);
            if(existe){
                System.out.println("| > La puerta de la habitacion " + origen + " a la\n| habitacion " + destino + " ya existe.");
            }
        }while(existe);
        System.out.print("| > Ingrese puntaje necesario: ");
        puntaje = sc.nextInt();
        
        // Inserto la conexion entre las habitaciones
        casa.insertarArco(origen, destino, puntaje);

        System.out.println("| > La puerta de " + origen + " a " + destino + " ha sido dado de alta");
    }
    public static void eliminarPuerta(GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        int habOrigen = 0, habDestino = 0;
        boolean exito = false;

        exito = casa.eliminarArco(habOrigen, habDestino);

        System.out.print("| > La puerta de la habitacion " + habOrigen + " a\n | la habitacion " + habDestino);
        
        if(exito){
            System.out.println(" se ha eliminado. ");
        }else{
            System.out.println(" no se ha encontrado.");
        }
        
    }
    

    // =================================
    //   Altas, bajas y modificaciones
    // =================================
    public static void abm(DiccionarioAVL habitaciones, GrafoEtiq casa, DiccionarioAVL desafios, DiccionarioHash equipos){
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{
            menuABM();
            opcion = sc.nextInt();

            if(opcion == 1){
                menuTipoDeABM("|                   A L T A S                    |");
                opcion = sc.nextInt();
                switch(opcion){
                    case 1: cargarHabitacionManual(habitaciones, casa); break;
                    case 2: cargarDesafioManual(desafios); break;
                    case 3: cargarEquipoManual(equipos); break;
                    case 4: cargarPuertasManual(casa); break;
                }
            } else if(opcion == 2){
                menuTipoDeABM("|                   B A J A S                    |");
                opcion = sc.nextInt();
                switch(opcion){
                    case 1: eliminarHabitacion(habitaciones, casa); break;
                    case 2: eliminarDesafio(desafios); break;
                    case 3: eliminarEquipo(equipos); break;
                    case 4: eliminarPuerta(casa); break;
                }
            } else if(opcion == 3){
                menuTipoDeABM("|          M O D I F I C A C I O N E S           |");
                opcion = sc.nextInt();
                switch(opcion){
                    case 1: modificarHabitacion(habitaciones, casa); break;
                    
                }
                /*
                case 2: modificarDesafio(); break;
                    case 3: modificarEquipo(); break;
                    case 3: modificarPuertas(); break; */
                
            }
        }while(opcion != 0);
    }
    public static void menuABM(){
        System.out.println("==================================================");
        System.out.println("|            ALTAS, BAJAS Y MODIFICACIONES       |");
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
                case 2: habitacionesContiguas(casa); break;
                case 3: esPosibleLlegar(casa); break;
                case 4: sinPasar(casa);
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
    public static void esPosibleLlegar(GrafoEtiq casa){
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese numero de la primera habitacion: ");
        int hab1 = sc.nextInt();
        System.out.print("| > Ingrese numero de la segunda habitacion: ");
        int hab2 = sc.nextInt();
        System.out.print("| > Ingrese puntaje acumulado: ");
        int puntaje = sc.nextInt();
        System.out.println("|------------------------------------------------|");

        int puntajeNecesario = casa.etiquetaArco(hab1, hab2);
        if(puntajeNecesario >= 0 && puntajeNecesario - puntaje <= 0){
            System.out.println("| > Es posible llegar con " + puntaje + " puntos.");
        }else{
            System.out.println("| > No es posible llegar");
        }   
    }
    // ---- Sin Pasar Por ----
    public static void sinPasar(GrafoEtiq casa){
        // Entrada de datos
        Scanner sc = new Scanner(System.in);
        System.out.print("| > Ingrese numero de la primera habitacion: ");
        int hab1 = sc.nextInt();
        System.out.print("| > Ingrese numero de la segunda habitacion: ");
        int hab2 = sc.nextInt();
        System.out.print("| > Ingrese numero habitacion a saltear: ");
        int hab3 = sc.nextInt();
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
        System.out.println("|------------------------------------------------|"); 
    }

    // =========================
    //     Consulta Desafios
    // =========================
    public static void consultaDesafios(DiccionarioAVL desafios){
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{
            menuConsultaDesafios();
            opcion = sc.nextInt();

            switch(opcion){
                case 1: mostrarDesafio(desafios); break;
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
        System.out.println("| 3. Verificar desafio resuelto                  |");
        sleepMilisegundos(150);
        System.out.println("| 5. Mostrar desafios tipo                       |");
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
    
    // =========================
    //  Consulta Participantes
    // =========================
    public static void consultaParticipantes(){}
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
        int esperaRapida = 0;
        int esperaLenta = 0;
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
    public static void cartelFinal(){}
    public static void sleepMilisegundos(int tiempo){
        try{
            TimeUnit.MILLISECONDS.sleep(tiempo);
        }
        catch(Exception e) {}
    }
}
