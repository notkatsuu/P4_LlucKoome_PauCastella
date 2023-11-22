package Estructura;

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

        // METODES ADDICIONALS RECURSIUS

        private Queue<E> recorrerDireccio(boolean sentit) {
            Queue<E> aux = new LinkedList<>();
            NodeA primer = sentit ? this.left : this.right;
            NodeA segon = sentit ? this.right : this.left;

            if (primer != null) {
                aux.addAll(primer.recorrerDireccio(sentit));
            }

            aux.add(this.cont);

            if (segon != null) {
                aux.addAll(segon.recorrerDireccio(sentit));
            }

            return aux;
        }

        private NodeA inserirR(NodeA a, E e) throws ArbreException {

            if (a == null) {
                a = new NodeA(e);
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

            if (dif == 0) {

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

        @Override
        public NodeA clone() {
            NodeA cloned = new NodeA();
            cloned.cont = this.cont;
            if (this.left != null) {
                cloned.left = this.left.clone();
            }
            if (this.right != null) {
                cloned.right = this.right.clone();
            }
            return cloned;
        }

        public int cardinalitat(int max, int actual){

         

            actual++;

            max = Math.max(max, actual);

            

            if (left != null) max = left.cardinalitat(max, actual);

            
            if (right != null) max = right.cardinalitat(max, actual);
        
         
            

            return max;


           
            
            
        }

    }

    protected NodeA arrel = null;
    public Queue<E> cua = new LinkedList<>();

    public AcbEnll() { // sense parametre, constructor arbre null
        this.arrel = null;
    }

    public AcbEnll(AcbEnll<E> left, E e, AcbEnll<E> right) {

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
            throw new ArbreException("Element Buit"); // Per a debugging

        if (arrel == null)
            arrel = new NodeA(e);

        else if (membre(e)) {
            throw new ArbreException("Ja hi és aquest jugador!");
        }

        else

            arrel = arrel.inserirR(this.arrel, e);
    }

    @Override
    public void esborrar(E e) throws ArbreException {

        if (e == null)
            throw new ArbreException("Element Buit"); // Per a debugging
        if (arrel == null)
            throw new ArbreException("Arbre Buit"); // Per a debugging

        if (!membre(e))
            throw new ArbreException("El jugador no es troba a la llista");

        this.arrel = arrel.esborrarR(this.arrel, e);
    }

    @Override
    public boolean membre(E e) {

        return arrel.membreR(this.arrel, e);

    }

    public void iniRecorregut(boolean sentit) {

        if (arrel == null)
            return;
        cua = this.arrel.recorrerDireccio(sentit); // Assign the returned queue to cua

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
        if (cua == null)
            throw new ArbreException("Cua Inexistent"); // Per a debugging
        if (cua.isEmpty())
            throw new ArbreException("No hi ha jugadors");
        Queue<E> cuaUpdated = null;

        if (cua.toArray().length > 1)
            cuaUpdated = arrel.recorrerDireccio(((Comparable<E>) cua.toArray()[0]).compareTo((E) cua.toArray()[1]) < 0);

        else
            cuaUpdated = arrel.recorrerDireccio(true);

        if (cuaUpdated == cua)
            throw new ArbreException("Cua modificada en interació");

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
        if (this.arrel == null)
            return null;
        AcbEnll<E> copia = new AcbEnll<E>();
        copia.arrel = this.arrel.clone();

        return copia;
    }

    public int cardinalitat() {

        return arrel.cardinalitat(-1,0);
    }

}
