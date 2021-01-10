package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modele.Matrice;
import modele.Vecteur;
public class MatriceTest {
	double [][] tabA = {{1,2},{2,1}};
	double [][] tabB = {{1,2},{3,1}};
	double [][] result = {{7,4},{5,5}};
	
	
	@Test
	public void testEquals() {
		assertFalse(new Matrice(tabA).equals(new Matrice(tabB)));
		assertTrue(new Matrice(tabA).equals(new Matrice(tabA)));
		assertFalse(new Matrice(tabB).equals(new Matrice(tabA)));
		assertTrue(new Matrice(tabA).equals(new Matrice(tabA)));
	}
	@Test
	public void multiplyTest() {
		assertArrayEquals(new Matrice(result).getTableau(),new Matrice(tabA).multiply(new Matrice(tabB)).getTableau() );
	}
	@Test
	public void getLineTest() {
		double [][] t1 = {{1,2},{2,1},{2,3}};
		double [][] t2 = {{1,2},{2,1},{2,3},{2,3},{2,3}};
		Matrice m1 = new Matrice(t1);
		Matrice m2 = new Matrice(t2);
		assertEquals(3, m1.getLineSize());
		assertEquals(5, m2.getLineSize());
	}

	@Test
	public void getColumnTest() {
		double [][] t1 = {{1,2},{2,1},{2,3}};
		double [][] t2 = {{1,2,2},{2,1,1},{2,3,3}};
		Matrice m1 = new Matrice(t1);
		Matrice m2 = new Matrice(t2);
		assertEquals(2, m1.getColumnSize());
		assertEquals(3, m2.getColumnSize());
	}
	@Test
	public void translation() {
		Vecteur v = new Vecteur(1, 2, 3);
		double [][] res = {{2,2,2},{3,3,3},{4,4,4},{1,1,1}};
		double [][] mat = {{1,1,1},{1,1,1},{1,1,1},{1,1,1}};
		Matrice rs = new Matrice(res);
		Matrice ma = new Matrice(mat);
		ma.translation(v);
		assertTrue(rs.equals(ma));
	}
	@Test
	public void rotationX_test() {
		/*double grad = (Math.PI/2);
		double [][] res = {{0,3},{1,4},{5,2},{1,1}};
		double [][] mat = {{0,3},{1,4},{2,5},{1,1}};
		Matrice rs = new Matrice(res);
		Matrice ma = new Matrice(mat);
		System.out.println(ma);
		ma.rotationX(grad);
		System.out.println(ma);
		assertTrue(rs.equals(ma));*/
	}

	@Test
	public void rotationY_test() {
	}
	
	@Test
	public void rotationZ_test() {
	}
	
	@Test
	public void homotetieTest() {
		double ratio =0.5;
		double [][] res = {{0.5,0.5,0.5},{0.5,0.5,0.5},{0.5,0.5,0.5},{1,1,1}};
		double [][] mat = {{1,1,1},{1,1,1},{1,1,1},{1,1,1}};
		Matrice rs = new Matrice(res);
		Matrice ma = new Matrice(mat);
		ma.homothetie(ratio);
		assertTrue(rs.equals(ma));
	}
}
