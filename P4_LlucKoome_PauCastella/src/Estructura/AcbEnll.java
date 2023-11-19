package Estructura;

import jconsole.JConsole;

import java.util.LinkedList;
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

        public Object clone() {
            NodeA aux = null;

            try {

                aux = (NodeA) (super.clone());

                if (left != null)
                    aux.left = (NodeA) (left.clone());
                if (right != null)
                    aux.right = (NodeA) (right.clone());

            } catch (CloneNotSupportedException e) {
                return null;
            }

            return aux;

        }

        // METODES ADICIONALS RECURSIUS

        private void recorrerDireccio(NodeA node, boolean sentit) {
            if (node == null) {
                return;
            }

            if (sentit) {
                recorrerDireccio(node.left, sentit);
                cua.add(node.cont);
                recorrerDireccio(node.right, sentit);
            } else {
                recorrerDireccio(node.right, sentit);
                cua.add(node.cont);
                recorrerDireccio(node.left, sentit);
            }
        }

        private NodeA inserirR(NodeA a, E e) throws ArbreException {

            if (a == null) {
                a = new NodeA(null, e, null);
                return a;
            } else {

                int dif = e.compareTo(a.cont);

                if (dif == 0)
                    throw new ArbreException("Ja existeix aquest element");

                if (dif > 0)
                    a.right = inserirR(a.right, e);
                else
                    a.left = inserirR(a.left, e);
            }

            return a;
        }

        private NodeA esborrarR(NodeA a, E e) throws ArbreException {

            if (a == null) {
                return null;
            }

            int dif = e.compareTo((E) (a.cont));

            if (dif < 0) {
                a.left = esborrarR(a.left, e);

            } else if (dif > 0) {
                a.right = esborrarR(a.right, e);

            } else { // l'hem trobat

                if (a.left == null) {

                    return a.right;

                } else if (a.right == null) {

                    return a.left;

                } else {
                    a.cont = buscarMax(a.left);
                    a.left = esborrarMax(a.left);
                }
            }

            return a;
        }

        private boolean membreR(NodeA a, E e) {

            if (a == null) {

                return false;

            }

            int dif = e.compareTo(a.cont);

            System.out.print(dif);

            if (dif == 0) {

                System.out.print("Trobat");
                return true;
            }

            if (dif > 0)
                return membreR(a.right, e);

            return membreR(a.left, e);
        }

        private E buscarMax(NodeA a) { // Katsu toca això que has tingut una idea millor
            while (a.right != null)
                a = a.right;
            return (E) (a.cont);

        }

        private NodeA esborrarMax(NodeA a) { // Katsu toca això que has tingut una idea millor

            if (a.right == null)
                return a.left;

            a.right = esborrarMax(a.right);

            return a;

        }

    }

    protected NodeA arrel;
    Queue<E> cua = new LinkedList<>();

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

        if (e == null)
            throw new ArbreException("Element Buit");

        if (arrel == null)
            arrel = new NodeA(null, e, null);

        else if (membre(e)) {
            throw new ArbreException("Ja hi es");
        }

        else

            arrel = arrel.inserirR(this.arrel, e);
    }

    @Override
    public void esborrar(E e) throws ArbreException {

        if (e == null)
            throw new ArbreException("Element Buit");
        if (arrel == null)
            throw new ArbreException("Arbre Buit");

        if (!membre(e))
            throw new ArbreException("L'element no es troba a l'arbre");

        this.arrel = arrel.esborrarR(this.arrel, e);
    }

    @Override
    public boolean membre(E e) {

        return arrel.membreR(this.arrel, e);

    }

    // ELS METODES PRIVATS ELS HAURE DE POSAR A DINS LA CLASSE NODE AL FINAL

    public void iniRecorregut(boolean sentit) { // He afegit de paràmetre el node

        if (arrel == null)
            return;

        arrel.recorrerDireccio(arrel, sentit);

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

        return (cua.isEmpty() || arrel == null);
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
        if (cua == null || cua.isEmpty())
            throw new ArbreException("Cua Buida");

        // Mirar si es modifica l'arbre, copiant la cua a un aux i fent una nova cua per
        // tal de veure si coincideixen

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

    @Override
    public Object clone() {

        if (arrel != null) {
            

            System.out.println("cagumdeu");


            return (AcbEnll<E>.NodeA) arrel.clone();
        }

        else return null;

        
    }

}
