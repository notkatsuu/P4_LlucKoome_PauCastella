package Jugador;

public class Jugador implements Comparable<Jugador> {

    public enum Posicio{ Base, Escorta, Aler, AlerPivot, Pivot};

    private Posicio pos;
    
    private int puntuacio;

    public Jugador(int pos, int punts){

        this.pos = Posicio.values()[pos-1]; // Converteixo l'enter a una Posició i l'assigno a l'Atribut pos

        puntuacio = punts; // assigno els punts

    }

    public int compareTo(Jugador j){ //Comparació entre jugadors

        int val=this.pos.compareTo(j.pos);

        if(val == 0)
            return (this.puntuacio-j.puntuacio);

        else return val;
        
    }

    public String toString(){


        return (pos.name() + " " + puntuacio);
    }
    
}
