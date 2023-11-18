package Testing;

import jconsole.JConsole;
public class Main {

    public enum Menus{opcions, entrarJugador, mostrarArbre}
    public static JConsole console;
    public static void main(String[] args) throws Exception {

        sistemaPrints(Menus.opcions);
        
    }

    public static int sistemaPrints(Menus actual){

        switch (actual){

            case opcions:
            
            console.println("1.- Inserir Jugador");
            console.println("2.- Eliminar Jugador");
            console.println("3.- Visualitzar un dels dos arbres");
            console.println("4.- Clonar");
            console.println("5.- Acabar");

            console.println("Tria una opció: [1,5]");
            return (console.readInt()); 

            default: return -1;

            

        }

    }
}
