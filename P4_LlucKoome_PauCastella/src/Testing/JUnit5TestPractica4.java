package Testing;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import Estructura.AcbEnll;
import Estructura.ArbreException;
import Jugador.Jugador;
import Jugador.Jugador.Posicio;

@TestMethodOrder(MethodOrderer.MethodName.class)
class JUnit5TestPractica4 {
	private static AcbEnll<Jugador> arbre;

	@Test
	void testArbre0Null() {
		arbre = new AcbEnll<>();
		assertEquals(0,arbre.cardinalitat());
		assertThrows(ArbreException.class, () -> {
	           arbre.arrel();
		  });
	}
	
	@Test
	void testArbre1() {
		ArrayList<Jugador> unaLista = new ArrayList<Jugador> ();
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 30));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 20));
		unaLista.add( new Jugador(Posicio.Pivot.ordinal(), 33));
		unaLista.add( new Jugador(Posicio.Base.ordinal(), 40));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 35));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 25));
		
		arbre = new AcbEnll<>();
		for (int i = 0; i < unaLista.size(); i++) {
			try {
				arbre.inserir(unaLista.get(i));
			} catch (ArbreException e) {
				fail(e.getMessage());
			}
			assertEquals(i+1,arbre.cardinalitat());
		}
		assertEquals(unaLista.size(), arbre.cardinalitat());
	}
	
	@Test
	void testArbre2QueueAscendent() {
		ArrayList<Jugador> unaLista = new ArrayList<Jugador> ();
		unaLista.add( new Jugador(Posicio.Base.ordinal(), 40));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 20));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 25));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 30));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 35));
		unaLista.add( new Jugador(Posicio.Pivot.ordinal(), 33));
		
		// comprovem la queue
		assertTrue(arbre.finalRecorregut());
		arbre.iniRecorregut(true); //ascendent
		assertFalse(arbre.finalRecorregut());
		
		// comprovem els elements de la queue
		for (int i = 0; i < unaLista.size(); i++) {
			try {
				assertEquals(0,arbre.segRecorregut().compareTo(unaLista.get(i)));
			} catch (ArbreException e) {
				fail(e.getMessage());
			}
		}
		assertTrue(arbre.finalRecorregut());
	}

	@Test
	void testArbre3QueueDescendent() {
		ArrayList<Jugador> unaLista = new ArrayList<Jugador> ();
		unaLista.add( new Jugador(Posicio.Base.ordinal(), 40));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 20));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 25));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 30));
		unaLista.add( new Jugador(Posicio.Aler.ordinal(), 35));
		unaLista.add( new Jugador(Posicio.Pivot.ordinal(), 33));
		
		assertTrue(arbre.finalRecorregut());
		arbre.iniRecorregut(false); //descendent
		assertFalse(arbre.finalRecorregut());
		for (int i = unaLista.size()-1 ; i>=0; i--) {
			try {
				assertEquals(0,arbre.segRecorregut().compareTo(unaLista.get(i)));
			} catch (ArbreException e) {
				fail(e.getMessage());
			}
		}
		assertTrue(arbre.finalRecorregut());
	}
	
	@Test
	void testArbre4inserirDuplicat() {
		assertThrows(ArbreException.class, () -> {
			arbre.inserir(new Jugador(Posicio.Aler.ordinal(), 20));
		 });
	}
	
	@Test
	void testArbre5eliminarInexistent() {
		assertThrows(ArbreException.class, () -> {
			arbre.esborrar(new Jugador(Posicio.Escorta.ordinal(), 20));
		 });
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testArbre6clonat() {
		int cardinalitat = arbre.cardinalitat();
		AcbEnll<Jugador> arbreClonat =  (AcbEnll<Jugador>) arbre.clone();
		assertEquals(cardinalitat, arbreClonat.cardinalitat());
		try {
			arbre.inserir(new Jugador(Posicio.AlerPivot.ordinal(), 10));
		} catch (ArbreException e) {
			fail(e.getMessage());
		}
		assertEquals(cardinalitat+1, arbre.cardinalitat());
		assertEquals(cardinalitat, arbreClonat.cardinalitat());
		try {
			arbre.esborrar(new Jugador(Posicio.Aler.ordinal(), 20));
		} catch (ArbreException e) {
			fail(e.getMessage());
		}
		assertEquals(cardinalitat, arbre.cardinalitat());
		assertEquals(cardinalitat, arbreClonat.cardinalitat());
	}
	

}
