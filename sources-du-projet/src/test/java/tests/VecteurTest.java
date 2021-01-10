package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modele.Matrice;
import modele.Point;
import modele.Vecteur;
public class VecteurTest {
	
	@Test 
	void testInitialisationVecteurNull() {
		Vecteur vecteur = new Vecteur();
		assertTrue(vecteur.getX() == 0);
		assertTrue(vecteur.getY() == 0);
		assertTrue(vecteur.getZ() == 0);
	}
	
	@Test
	void testnitialisationVecteurCoordo() {
		Vecteur vecteur = new Vecteur(5,4,3);
		assertEquals(5.0,vecteur.getX());
		assertEquals(4.0,vecteur.getY());
		assertEquals(3.0,vecteur.getZ());
	}
	
	@Test
	void testInitialisationVecteurAvecPoints() {
		Point pointA = new Point(0, 0, 0);
		Point pointB = new Point(1,1,1);
		Point pointC = new Point(3,3,3);
		Vecteur vecteurAB = new Vecteur(pointA, pointB);
		assertEquals(1.0,vecteurAB.getX());
		assertEquals(1.0,vecteurAB.getY());
		assertEquals(1.0,vecteurAB.getZ());
		Vecteur vecteurBC = new Vecteur(pointB,pointC);
		assertEquals(2.0,vecteurBC.getX());
		assertEquals(2.0,vecteurBC.getY());
		assertEquals(2.0,vecteurBC.getZ());
	}
	
	
	@Test
	public void testProduitVectoriel() {
		Vecteur vecteur = new Vecteur(1, 1, 1);
		Vecteur vecteur2 = new Vecteur(1,2,1);
		Vecteur produit = vecteur.produitVectoriel(vecteur2);
		assertArrayEquals(new Vecteur(-1,0,1).getMatrice().getTableau(),produit.getTableau());
	}
	
	@Test
	public void testProduitScalaire() {
		Vecteur vecteur = new Vecteur(1, 1, 1);
		Vecteur vecteur2 = new Vecteur(1,2,1);
		assertEquals(4.0,vecteur.produitScalaire(vecteur2));
		assertEquals(4.0,vecteur2.produitScalaire(vecteur));
	}
	
	@Test
	public void testNormeVectorielVecteurNull() {
		Vecteur vecteur = new Vecteur();
		assertTrue(0.0 == vecteur.normeVectorielle());
	}
	@Test
	public void testNormeVectoriel() {
		Vecteur vecteur = new Vecteur(2, 2, 1);
		Vecteur vecteur2 = new Vecteur(-0.5,-1,-0.5);
		assertEquals(3,vecteur.normeVectorielle());
		assertEquals(Math.sqrt(1.5),vecteur2.normeVectorielle());
	}
	
	@Test
	public void testGetMatrice() {
		Matrice matrice = new Matrice(new double [][]{{2},{2},{1},{0}});
		Vecteur vecteur = new Vecteur(2,2,1);
		assertArrayEquals(matrice.getTableau(), vecteur.getTableau());
	}
	
	@Test void testdiviser() {
		Vecteur vecteur = new Vecteur(2,2,1);
		vecteur = vecteur.diviser(2);
		assertEquals(1.0, vecteur.getX());
		assertEquals(1.0, vecteur.getY());
		assertEquals(0.5, vecteur.getZ());
	}
	
	@Test void testdivisernegatif() {
		Vecteur vecteur = new Vecteur(-2,2,-6);
		vecteur = vecteur.diviser(2);
		assertEquals(-1.0, vecteur.getX());
		assertEquals(1.0, vecteur.getY());
		assertEquals(-3.0, vecteur.getZ());
	}
}