import estructuras.arbol.DiccionarioAVL;
public class Prueba {
    public static void main(String[] args){
        DiccionarioAVL prueba = new DiccionarioAVL();
    
        prueba.insertar(1, null);
        System.out.println(prueba.listarDatos().toString());

    }
}
