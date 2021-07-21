import estructuras.arbol.DiccionarioAVL;
public class Prueba {
    public static void main(String[] args){
        DiccionarioAVL prueba = new DiccionarioAVL();
    
        prueba.insertar(1, null);
        prueba.insertar(2, null);
        prueba.insertar(3, null);
        prueba.insertar(4, null);
        prueba.insertar(5, null);
        prueba.insertar(6, null);
        prueba.insertar(7, null);


        System.out.println(prueba.toString());

        prueba.eliminar(1);
        prueba.eliminar(4);
        System.out.println(prueba.toString());
    }
}
