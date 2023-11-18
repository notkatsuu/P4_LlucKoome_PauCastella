package Jugador;

public class Jugador {

    public enum Posicio{ Base, Escorta, Aler, AlerPivot, Pivot};

    private Posicio pos;

    private int puntuacio;

    public Jugador(int pos, int punts){

        this.pos = Posicio.values()[pos]; // Converteixo l'enter a una Posició i l'assigno a l'Atribut pos

        puntuacio = punts; // assigno els punts

    }

    public boolean compareTo(Jugador j){ //Comparació entre jugadors

        if (j.pos==this.pos){ //Si les posicions coincideixen
            return (j.puntuacio == this.puntuacio); //Retorna si tenen la mateixa posició
        }
        return false; //Si no, !=
    }

    public String toString(){


        return (pos.name() + " " + puntuacio);
    }
    
}
