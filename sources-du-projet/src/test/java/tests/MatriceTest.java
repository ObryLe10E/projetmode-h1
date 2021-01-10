package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        double [][] tab1 = {{1,2},{2,1},{2,3}};
        double [][] tab2 = {{1,2},{2,1},{2,3},{2,3},{2,3}};
        Matrice matrice1 = new Matrice(tab1);
        Matrice matrice2 = new Matrice(tab2);
        assertEquals(3, matrice1.getLineSize());
        assertEquals(5, matrice2.getLineSize());
    }

    @Test
    public void getColumnTest() {
        double [][] tab1 = {{1,2},{2,1},{2,3}};
        double [][] tab2 = {{1,2,2},{2,1,1},{2,3,3}};
        Matrice matrice1 = new Matrice(tab1);
        Matrice matrice2 = new Matrice(tab2);
        assertEquals(2, matrice1.getColumnSize());
        assertEquals(3, matrice2.getColumnSize());
    }
    @Test
    public void translation() {
        Vecteur vecteur = new Vecteur(1, 2, 3);
        double [][] res = {{2,2,2},{3,3,3},{4,4,4},{1,1,1}};
        double [][] mat = {{1,1,1},{1,1,1},{1,1,1},{1,1,1}};
        Matrice resultat = new Matrice(res);
        Matrice matrice = new Matrice(mat);
        matrice.translation(vecteur);
        assertTrue(resultat.equals(matrice));
    }
    @Test
    public void rotationXtest() {
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
    public void rotationYtest() {
    }

    @Test
    public void rotationZtest() {
    }

    @Test
    public void homotetieTest() {
        double ratio = 0.5;
        double [][] res = {{0.5,0.5,0.5},{0.5,0.5,0.5},{0.5,0.5,0.5},{1,1,1}};
        double [][] mat = {{1,1,1},{1,1,1},{1,1,1},{1,1,1}};
        Matrice resultat = new Matrice(res);
        Matrice matrice = new Matrice(mat);
        matrice.homothetie(ratio);
        assertTrue(resultat.equals(matrice));
    }
}