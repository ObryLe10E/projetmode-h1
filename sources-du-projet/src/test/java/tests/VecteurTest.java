package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modele.Matrice;
import modele.Point;
import modele.Vecteur;
public class VecteurTest {
	
	@Test 
	void testInitialisationVecteurNull() {
		Vecteur v = new Vecteur();
		assertTrue(v.getX() == 0);
		assertTrue(v.getY() == 0);
		assertTrue(v.getZ() == 0);
	}
	
	@Test
	void testnitialisationVecteurCoordo() {
		Vecteur v = new Vecteur(5,4,3);
		assertEquals(5.0,v.getX());
		assertEquals(4.0,v.getY());
		assertEquals(3.0,v.getZ());
	}
	
	@Test
	void testInitialisationVecteurAvecPoints() {
		Point a = new Point(0, 0, 0);
		Point b = new Point(1,1,1);
		Point c = new Point(3,3,3);
		Vecteur ab = new Vecteur(a, b);
		assertEquals(1.0,ab.getX());
		assertEquals(1.0,ab.getY());
		assertEquals(1.0,ab.getZ());
		Vecteur bc = new Vecteur(b,c);
		assertEquals(2.0,bc.getX());
		assertEquals(2.0,bc.getY());
		assertEquals(2.0,bc.getZ());
	}
	
	
	@Test
	public void testProduitVectoriel() {
		Vecteur v = new Vecteur(1, 1, 1);
		Vecteur v2 = new Vecteur(1,2,1);
		assertArrayEquals(new Vecteur(-1,0,1).getMatrice().getTableau(),v.produitVectoriel(v2).getMatrice().getTableau());
	}
	
	@Test
	public void testProduitScalaire() {
		Vecteur v = new Vecteur(1, 1, 1);
		Vecteur v2 = new Vecteur(1,2,1);
		assertEquals(4.0,v.produitScalaire(v2));
		assertEquals(4.0,v2.produitScalaire(v)); //On check si c'est commutatif
	}
	
	@Test
	public void testNormeVectorielVecteurNull() {
		Vecteur v = new Vecteur();
		assertTrue(0.0 == v.normeVectoriel());
	}
	@Test
	public void testNormeVectoriel() {
		Vecteur v = new Vecteur(2, 2, 1);
		Vecteur v2 = new Vecteur(-0.5,-1,-0.5);
		assertEquals(3,v.normeVectoriel());
		assertEquals(Math.sqrt(1.5),v2.normeVectoriel());
	}
	
	@Test
	public void testGetMatrice() {
		Matrice m = new Matrice(new double [][]{{2},{2},{1},{0}});
		Vecteur v = new Vecteur(2,2,1);
		assertArrayEquals(m.getTableau(), v.getMatrice().getTableau());
	}
	
	@Test void testdiviser() {
		Vecteur v = new Vecteur(2,2,1);
		v = v.diviser(2);
		assertEquals(1.0, v.getX());
		assertEquals(1.0, v.getY());
		assertEquals(0.5, v.getZ());
	}
	
	@Test void testdivisernegatif() {
		Vecteur v = new Vecteur(-2,2,-6);
		v = v.diviser(2);
		assertEquals(-1.0, v.getX());
		assertEquals(1.0, v.getY());
		assertEquals(-3.0, v.getZ());
	}

	
}
