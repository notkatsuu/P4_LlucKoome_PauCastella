package Testing;


import Estructura.Acb;
import Estructura.AcbEnll;
import Jugador.Jugador;
import jconsole.JConsole;


public class Main{

    public enum Menus {
        opcions, entrarPosicio, entrarPuntuacio, mostrarArbre
    }
         
    public static JConsole console;
    
    public static void main(String[] args) throws Exception {
        Acb<E> arbre = new AcbEnll();                  // no entenc lo de la E
        Comparable c;
        initConsole();

        switch (sistemaPrints(Menus.opcions)) {
            case 1:
                arbre.inserir(Jugador(1,1));
            case 2:
                sistemaPrints(Menus.opcions);
        }

    }

    private static Object Jugador(int i, int j) {
        return null;
    }

    public static void initConsole() {
        console = new JConsole(80, 30);
        console.setCursorVisible(false);
        console.setTitle("P4_PauCastella_LlucKoome");
    }

    public static int sistemaPrints(Menus actual) {
        int val = -1;
        switch (actual) {

            case opcions:

                do {
                    console.println("1.- Inserir Jugador");
                    console.println("2.- Eliminar Jugador");
                    console.println("3.- Visualitzar un dels dos arbres");
                    console.println("4.- Clonar");
                    console.println("5.- Acabar");

                    console.println("Tria una opció: [1,5]");
                    val = console.readInt();
                    
                } while (val < 1 && val > 5);
                return val;
                

            case entrarPosicio:
                do {

                    console.println("Indica la posició del jugador:");
                    console.println("1.- Base");
                    console.println("2.- Escorta");
                    console.println("3.- Aler");
                    console.println("4.- AlerPivot");
                    console.println("5.- Pivot");

                    console.println("Tria una opció: [1,5]");
                    val = console.readInt();

                } while (val < 1 && val > 5);
                return val;

            case entrarPuntuacio:
                do {
                    console.println("Indica la seva punctuació [0, 1000]");
                    val = console.readInt();

                } while (val < 0 && val > 1000);
                return val;

            default:
                return -1;

        }

    }

}
