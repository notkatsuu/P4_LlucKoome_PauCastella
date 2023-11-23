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
        OPCIONS("Inserir Jugador", "Eliminar Jugador", "Visualitzar un dels dos arbres", "Clonar",
                "Acabar"),
        ENTRAR_JUGADOR("Base", "Escorta", "Aler", "AlerPivot", "Pivot");

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

        Acb<E> aVisualitzar = null;

        Object duplicate = null;

        Comparable<E> c;

        // Initialize the console
        initConsole();

        //Testing
        JUnit5TestPractica4 tester = new JUnit5TestPractica4();

        tester.testArbre0Null();
        tester.testArbre1();
        tester.testArbre2QueueAscendent();
        tester.testArbre3QueueDescendent();
        tester.testArbre4inserirDuplicat();
        tester.testArbre5eliminarInexistent();
        tester.testArbre6clonat();


        boolean run = true;
        while (run) {
            console.clear();
            // Display the options and perform the selected action
            switch (printSystem(Menus.OPCIONS.getOptions())) {

                case 1:
                    try {
                        Jugador j = seleccioJugador();
                        arbre.inserir((E) j);

                    } catch (ArbreException e) {
                        printArbreException(e);
                        break;
                    }

                    confirmar("afegit el jugador");

                    break;

                case 2:
                    try {

                        console.println("Selecciona un dels teus jugadors actuals: \n");
                        ((AcbEnll<E>) arbre).iniRecorregut(true);

                        do {

                            // Display the next element in the tree
                            c = ((AcbEnll<E>) arbre).segRecorregut();
                            console.println(" - " + c.toString());

                        } while (!((AcbEnll<E>) arbre).finalRecorregut());

                        console.println();

                        Jugador j = seleccioJugador();
                        arbre.esborrar((E) j);
                    } catch (ArbreException e) {
                        printArbreException(e);
                        break;
                    }

                    confirmar("eliminat el jugador");
                    break;

                case 3:
                    
                    // Display one of the trees
                    if (printSystem("Actual", "Clonat") == 1)
                        aVisualitzar = arbre;
                    else if (duplicate != null) {
                        aVisualitzar = (Acb<E>) duplicate;
                    } else {

                        console.setForegroundColor(Color.red);
                        console.println("Arbre Buit");
                        console.resetColor();
                        console.readKey();
                        break;
                    }

                    try {
                        ((AcbEnll<E>) aVisualitzar).iniRecorregut(printSystem("Ascendent", "Descendent") == 1);
                        int nombreCua = 0;
                        do {
                            nombreCua++;
                            // Display the next element in the tree
                            c = ((AcbEnll<E>) aVisualitzar).segRecorregut();
                            console.println(nombreCua + ".-: " + c.toString());

                        } while (!((AcbEnll<E>) aVisualitzar).finalRecorregut());
                        console.println("\n Alçada actual de l'arbre: " + ((AcbEnll<E>) arbre).cardinalitat() + "\n ");
                        console.readKey();
                    } catch (ArbreException e) {
                        // Handle exception
                        printArbreException(e);
                    }

                    break;

                case 4:
                    // Clone the tree
                    duplicate = ((AcbEnll<E>) arbre).clone();

                    confirmar("clonat");
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

    public static void printArbreException(ArbreException e) {
        console.setForegroundColor(Color.red);
        console.println("\n" + e.getLocalizedMessage() + "\n" + "Prem qualsevol botó per a retornar a l'inici.");
        console.resetColor();
        console.readKey();

    }

    public static int printSystem(String... options) {
        int val = -1;
        do {
            // Display the options

            if (options.length == 2) { // Handle seleccioBinaria case
                console.print("Tria una opció -> " + options[0] + "[1], " + options[1] + "[2]: ");
                val = console.readInt();
                if (val == 1 || val == 2) {
                    return val;
                }
            } else if (options.length == 1 && options[0].startsWith("Tria la puntuació")) { // Handle setPunts case
                console.print(options[0] + ": ");
                val = console.readInt();
                if (val >= 0 && val <= 1000) {
                    return val;
                }
            } else { // Default case
                console.setForegroundColor(Color.gray);
                console.println();
                for (int i = 0; i < options.length; i++) {
                    console.println((i + 1) + ".- " + options[i]);
                }
                console.resetColor();
                console.println();
                console.print("Tria una opció: [1," + options.length + "]: ");
                val = console.readInt();
                if (val >= 1 && val <= options.length) {
                    return val;
                }
            }

        } while (true);
    }

    private static Jugador seleccioJugador() {
        //restem un a la posició ja que l'usuari entra un valor de 1 a 5 i l'array és de 0 a 4
        return new Jugador(printSystem(Menus.ENTRAR_JUGADOR.getOptions())-1, printSystem("Tria la puntuació [0,1000]"));
    }

    private static void confirmar(String missatge) {
        console.println();
        console.setForegroundColor(Color.GREEN);
        console.println("S'ha " + missatge + " correctament !");
        console.resetColor();
        console.readKey();

    }

}
