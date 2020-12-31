package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modele.Vecteur;
public class VecteurTest {
	@Test
	public void normeTest() {
		Vecteur v = new Vecteur(2, 2, 1);
		Vecteur v2 = new Vecteur(-0.5,-1,-0.5);
		assertEquals(3,v.normeVectoriel());
		assertEquals(Math.sqrt(1.5),v2.normeVectoriel());
	}
	@Test
	public void produitVectorielTest() {
		Vecteur v = new Vecteur(1, 1, 1);
		Vecteur v2 = new Vecteur(1,2,1);
		assertArrayEquals(new Vecteur(-1,0,1).getMatrice().getTableau(),v.produitVectoriel(v2).getMatrice().getTableau());
	}
}
