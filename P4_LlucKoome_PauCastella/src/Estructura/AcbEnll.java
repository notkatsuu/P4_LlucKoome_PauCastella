package Estructura;

import java.util.Queue;

public class AcbEnll<E extends Comparable<E>> implements Acb<E>, Cloneable {

    private class NodeA { // Creem classe privada NodeA
        E cont;
        NodeA left, right;

        NodeA() {
            this(null);
        }

        NodeA(E o) {
            this(null, o, null);

        }

        NodeA(NodeA e, E o, NodeA d) {
            cont = o;
            left = e;
            right = d;

        }
    }

    protected NodeA arrel;
    Queue<E> cua;

    public AcbEnll() { // sense parametre, constructor arbre null
        this.arrel = null;
    }

    public AcbEnll(AcbEnll left, E e, AcbEnll right) {

        this.arrel = new NodeA(left.arrel, e, right.arrel);
    }

    @Override
    public E arrel() throws ArbreException {

        if (this.arrel == null)
            throw new ArbreException("Arbre buit!");

        else
            return this.arrel.cont;
    }

    @Override
    public Acb<E> fillEsquerre() {
        if (arrel != null) {
            AcbEnll<E> a = new AcbEnll<>();
            a.arrel = arrel.left;
            return a;
        }
        return null;
    }

    @Override
    public Acb<E> fillDret() {
        if (arrel != null) {
            AcbEnll<E> a = new AcbEnll<>();
            a.arrel = arrel.right;
            return a;
        }
        return null;

    }

    @Override
    public boolean abBuit() {

        return (arrel == null);
    }

    @Override
    public void buidar() {

        arrel = null;
    }

    @Override
    public void inserir(E e) throws ArbreException {

        if (arrel == null)
            arrel = new NodeA(null, e, null);

        else
            inserirR(this.arrel, e);
    }

    @Override
    public void esborrar(E e) throws ArbreException {
        if (arrel == null)
            throw new ArbreException("L'element no es troba");

        this.arrel = esborrarR(this.arrel, e);
    }

    @Override
    public boolean membre(E e) {
        if (arrel == null)
            return false;

        return membreR(this.arrel, e);

    }

    private NodeA inserirR(AcbEnll.NodeA a, E e) throws ArbreException {

        int dif = e.compareTo((E) (a.cont));

        if (dif == 0)
            throw new ArbreException("Ja existeix aquest element");

        if (dif > 0)
            return inserirR(a.right, e);

        return inserirR(a.left, e);
    }



    //ELS METODES PRIVATS ELS HAURE DE POSAR A DINS LA CLASSE NODE AL FINAL

    
    private NodeA esborrarR(AcbEnll.NodeA a, E e) throws ArbreException {

        int dif = e.compareTo((E) (a.cont));

        if (dif > 0)
            a.left = esborrarR(a.right, e);

        if (dif < 0)
            a.right = esborrarR(a.left, e);

        else { // l'hem trobat

            if (a.left == null && a.right == null) { // no té fills
                a = null;
            } else if (a.left != null && a.right != null) { // té dos fills

                a.cont = buscarMax(a.left); // busquem el màxim per la esquerra (per a tenir el valor més proper per
                                            // abaix)
                a.left = esborrarMax(a.left);

            } else if (a.left == null)
                a = a.right;
            else
                a = a.left;

        }
        return a;

    }

    private E buscarMax(AcbEnll.NodeA a) { // Katsu toca això que has tingut una idea millor
        while (a.right != null)
            a = a.right;
        return (E) (a.cont);

    }

    private NodeA esborrarMax(AcbEnll.NodeA a) { // Katsu toca això que has tingut una idea millor

        if (a.right == null)
            return a.left;

        a.right = esborrarMax(a.right);

        return a;

    }

    private boolean membreR(AcbEnll<E>.NodeA a, E e) {

        int dif = e.compareTo((E) (a.cont));

        if (dif == 0)
            return true;

        if (dif > 0)
            return membreR(a.right, e);

        return membreR(a.left, e);
    }

    public void iniRecorregut(NodeA arrel, boolean sentit) { // He affegit de paràmetre el node

        if (arrel == null)
            return;

        if (sentit) {

            iniRecorregut(arrel.left, sentit);

            cua.add(arrel.cont);

            iniRecorregut(arrel.right, sentit);

        } else {

            iniRecorregut(arrel.right, sentit);

            cua.add(arrel.cont);

            iniRecorregut(arrel.left, sentit);
        }

    }

    /*
     * Aquest mètode fa la preparació, caldrà omplir l’atribut cua amb els elements
     * de l’arbre, l’ordre
     * dependrà del paràmetre d’entrada sentit:
     * ‐ true: el recorregut serà l’inOrdre treballat a classe de teoria obtenint
     * una ordenació
     * ascendent.
     * ‐ false: el recorregut serà un inOrdre intercanviant el tractament dels fills
     * esquerra i dreta,
     * obtenint una ordenació descendent.
     */
    public boolean finalRecorregut() {
        
        return (cua.isEmpty() || arrel==null);
    }

    /*
     * retorna true si ja s’ha arribat al final del recorregut en inOrdre de
     * l’arbre. Això és si:
     * ‐ l’arbre és buit
     * ‐ la darrera vegada que es va invocar segRecorregut aquest mètode ja va
     * retornar el darrer
     * element en inOrdre de l’arbre.
     * Tot això és el mateix que dir que retorna true quan no té sentit invocar el
     * mètode segRecorregut
     */
    public E segRecorregut() throws ArbreException {
        if (cua==null || cua.isEmpty()) throw new ArbreException("Cua Buida");
        else{
            Queue<E> aux = cua;
            iniRecorregut(arrel, (aux.poll().compareTo(aux.poll()) > 0)); 

            if(aux!=cua) throw new ArbreException("Modificació d'Arbre ha interrumput una iteració!");
        }
        return cua.poll();
        
    }
    /*
     * retorna el següent element en inordre, si n’hi ha.
     * Llença una excepció si:
     * ‐ abans d’invocar‐lo no s’ha invocat el mètode iniRecorregut
     * ‐ la darrera vegada que es va invocar ja va retornar el darrer element del
     * recorregut
     * (finalRecorregut retornaria true)
     * ‐ s’invoca quan entre la invocació de iniRecorregut i la del mètode s’ha
     * produït una
     * modificació de l’arbre, això és, s’ha fet ús del mètode inserir, esborrar,
     * buidar
     */

}
