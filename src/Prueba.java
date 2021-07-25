import estructuras.arbol.DiccionarioAVL;
public class Prueba {
    public static void main(String[] args){
        DiccionarioAVL prueba = new DiccionarioAVL();
    
        prueba.insertar(1, null);
        prueba.insertar(2, null);
        prueba.insertar(8, null);
        prueba.insertar(4, null);
        prueba.insertar(9, null);
        prueba.insertar(5, null);
        prueba.insertar(6, null);
        prueba.insertar(7, null);
        prueba.insertar(10, null);
        prueba.insertar(11, null);
        prueba.insertar(3, null);
        prueba.insertar(16, null);
        prueba.insertar(12, null);
        prueba.insertar(18, null);
        prueba.insertar(14, null);
        prueba.insertar(15, null);
        prueba.insertar(17, null);
        prueba.insertar(13, null);
        prueba.insertar(19, null);
        prueba.insertar(20, null);
        prueba.insertar(21, null);
        prueba.eliminar(18);
        prueba.eliminar(21);
        prueba.eliminar(9);
        prueba.eliminar(11);
        prueba.eliminar(15);
        prueba.eliminar(17);
        System.out.println(prueba.toString());
        prueba.eliminar(20);
        System.out.println(prueba.toString());
    }
}
