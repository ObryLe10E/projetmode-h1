package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modele.Matrice;
public class MatriceTest {
	double [][] tabA = {{1,2},{2,1}};
	double [][] tabB = {{1,2},{3,1}};
	double [][] result = {{7,4},{5,5}};
	@Test
	public void multiplyTest() {
		assertArrayEquals(new Matrice(result).getMatrice(),new Matrice(tabA).multiply(new Matrice(tabB)).getMatrice() );
	}
}
