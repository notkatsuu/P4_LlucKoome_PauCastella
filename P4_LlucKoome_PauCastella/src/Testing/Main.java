package Testing;

import java.awt.Color;

import Estructura.Acb;
import Estructura.AcbEnll;
import Estructura.ArbreException;
import Jugador.Jugador;
import jconsole.JConsole;

public class Main {

    public enum Menus {
        opcions, entrarPosicio, entrarPuntuacio,
    }

    public static JConsole console;

    public static <E extends Comparable<E>> void main(String[] args) throws Exception {
        Acb<E> arbre = new AcbEnll();
        Acb<E> duplicate = null; // no entenc lo de la E
        Acb<E> aVisualitzar = null;

        Comparable<E> c;

        initConsole();
        boolean run = true;
        while (run) {
            console.clear();
            switch (sistemaPrints(Menus.opcions)) {

                case 1:

                    try {
                        E j = (E) new Jugador(sistemaPrints(Menus.entrarPosicio), sistemaPrints(Menus.entrarPuntuacio));
                        arbre.inserir(j);
                    } catch (ArbreException e) {

                        console.setForegroundColor(Color.red);
                        console.println("\n" + e.toString() + "\n");
                        console.resetColor();
                        console.readKey();
                        console.clear();
                    }

                    break;

                case 2:

                    try {
                        E j = (E) new Jugador(sistemaPrints(Menus.entrarPosicio), sistemaPrints(Menus.entrarPuntuacio));
                        arbre.esborrar(j);
                    } catch (ArbreException e) {
                        console.setForegroundColor(Color.red);
                        console.println("\n" + e.toString() + "\n");
                        console.resetColor();
                        console.readKey();
                        console.clear();
                    }
                    break;

                case 3:
                    if (seleccioBinaria("Actual", "Clonat"))
                        aVisualitzar = arbre;
                    else if (duplicate != null) {
                        aVisualitzar = duplicate;
                        System.out.print(duplicate);
                    } else {
                        console.println("Arbre Buit");
                        break;
                    }

                    try {
                        ((AcbEnll) aVisualitzar).iniRecorregut(seleccioBinaria("Ascendent", "Descendent"));

                        do {

                            c = ((AcbEnll) aVisualitzar).segRecorregut();

                            console.println(c.toString());

                        } while (!((AcbEnll) aVisualitzar).finalRecorregut());

                    } catch (ArbreException e) {

                        console.setForegroundColor(Color.red);
                        console.println("\n" + e.toString() + "\n");
                        console.resetColor();
                        console.readKey();
                        console.clear();
                    }

                    console.readKey();

                    break;

                case 4:

                   
                    break;

                case 5:
                    run = !run;
                    break;
            }
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

    public static boolean seleccioBinaria(String a, String b) {
        int result;
        do {
            console.println("Indica en quin ordre vols mostrar els jugadors");
            console.println("1.- " + a);
            console.println("2.- " + b);
            console.println("Tria una opció [1,2]");

            result = console.readInt();
        } while (result != 2 && result != 1);

        return (result == 1);
    }

}
