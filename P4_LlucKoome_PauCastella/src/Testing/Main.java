package Testing;

import java.awt.Color;

import Estructura.Acb;
import Estructura.AcbEnll;
import Estructura.ArbreException;
import Jugador.Jugador;
import jconsole.JConsole;

public class Main {

    // Enum for different menu options
    public enum Menus {
        OPCIONS("1.- Inserir Jugador", "2.- Eliminar Jugador", "3.- Visualitzar un dels dos arbres", "4.- Clonar",
                "5.- Acabar"),
        ENTRAR_POSICIO("1.- Base", "2.- Escorta", "3.- Aler", "4.- AlerPivot", "5.- Pivot"),
        ENTRAR_PUNTUACIO("Indica la seva puntuació [0, 1000]");

        private final String[] options;

        Menus(String... options) {
            this.options = options;
        }

        // Returns the options associated with this menu
        String[] getOptions() {
            return options;
        }
    }

    public static JConsole console;

    public static <E extends Comparable<E>> void main(String[] args) throws Exception {
        // Initialize the trees
        Acb<E> arbre = new AcbEnll<E>();
        Acb<E> duplicate = new AcbEnll<E>();
        Acb<E> aVisualitzar = null;

        Comparable<E> c;

        // Initialize the console
        initConsole();

        boolean run = true;
        while (run) {
            console.clear();
            // Display the options and perform the selected action
            switch (sistemaPrints(Menus.OPCIONS)) {

                case 1:
    try {
        Jugador j = seleccioJugador();
        arbre.inserir((E) j);
    } catch (ArbreException e) {
        printArbreException(e);
    }
    break;

case 2:
    try {
        Jugador j = seleccioJugador();
        arbre.esborrar((E) j);
    } catch (ArbreException e) {
        printArbreException(e);
    }
    break;


                case 3:
                    // Display one of the trees
                    if (seleccioBinaria("Actual", "Clonat"))
                        aVisualitzar = arbre;
                    else if (duplicate != null) {
                        aVisualitzar = duplicate;
                    } else {
                        console.println("Arbre Buit");
                        break;
                    }

                    try {
                        ((AcbEnll<E>) aVisualitzar).iniRecorregut(seleccioBinaria("Ascendent", "Descendent"));

                        do {
                            // Display the next element in the tree
                            c = ((AcbEnll<E>) aVisualitzar).segRecorregut();
                            console.println(c.toString());

                        } while (!((AcbEnll<E>) aVisualitzar).finalRecorregut());

                    } catch (ArbreException e) {
                        // Handle exception
                        printArbreException(e);
                    }

                    console.readKey();
                    break;

                case 4:
                    // Clone the tree
                    duplicate = (Acb<E>) ((AcbEnll) arbre).clone();
                    break;

                case 5:
                    // Exit the program
                    run = !run;
                    break;
            }
        }

        System.exit(0);

    }

    // Initialize the console
    public static void initConsole() {
        console = new JConsole(80, 30);
        console.setCursorVisible(false);
        console.setTitle("P4_PauCastella_LlucKoome");
    }

    // Display the options for the given menu and return the selected option
    public static int sistemaPrints(Menus actual) {
        int val = -1;
        switch (actual) {

            case OPCIONS:
            case ENTRAR_POSICIO:
                do {
                    // Display the options
                    for (String option : actual.getOptions()) {
                        console.println(option);
                    }

                    console.println("Tria una opció: [1," + actual.getOptions().length + "]");
                    val = console.readInt();

                } while (val < 1 || val > actual.getOptions().length);
                return val;

            case ENTRAR_PUNTUACIO:
                do {
                    // Prompt

                    console.println(actual.getOptions()[0]);
                    val = console.readInt();

                } while (val < 0 || val > 1000);
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

    public static void printArbreException(ArbreException e) {
        console.setForegroundColor(Color.red);
        console.println("\n" + e.toString() + "\n");
        console.resetColor();
        console.readKey();
        console.clear();
    }

    private static Jugador seleccioJugador() {
        return new Jugador(sistemaPrints(Menus.ENTRAR_POSICIO), sistemaPrints(Menus.ENTRAR_PUNTUACIO));
    }
    

}
