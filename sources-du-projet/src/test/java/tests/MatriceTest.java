package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DecimalFormat;

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
    	double[][] res = new double[4][4];

        res[0][0] =  1;
        res[0][1] =  0;
        res[0][2] =  0;
        res[0][3] =  0;

        res[1][0] =  0;
        res[1][1] =  -0.45;
        res[1][2] =  -0.89;
        res[1][3] =  0;

        res[2][0] =  0;
        res[2][1] =  0.89;
        res[2][2] =  -0.45;
        res[2][3] =  0;

        res[3][0] =  0;
        res[3][1] =  0;
        res[3][2] =  0;
        res[3][3] =  1;
        double [][] mat = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
        Matrice test = new Matrice(mat);
        Matrice rs = new Matrice(res);
        test.rotationX(90);
        DecimalFormat df = new DecimalFormat("0.00");
        test.getMatrice()[1][1] = Double.parseDouble(df.format(test.getMatrice()[1][1]).replace(',','.'));
        test.getMatrice()[1][2] = Double.parseDouble(df.format(test.getMatrice()[1][2]).replace(',','.'));
        test.getMatrice()[1][3] = Double.parseDouble(df.format(test.getMatrice()[1][3]).replace(',','.'));
        test.getMatrice()[2][1] = Double.parseDouble(df.format(test.getMatrice()[2][1]).replace(',','.'));
        test.getMatrice()[2][2] = Double.parseDouble(df.format(test.getMatrice()[2][2]).replace(',','.'));
        test.getMatrice()[2][3] = Double.parseDouble(df.format(test.getMatrice()[2][3]).replace(',','.'));
        
        assertEquals(rs,test);
    }

    @Test
    public void rotationYtest() {
    	double[][] res = new double[4][4];
        res[0][0] = -0.45;
        res[0][1] = 0;
        res[0][2] =  -0.89;
        res[0][3] =  0;

        res[1][0] =  0;
        res[1][1] =  1;
        res[1][2] =  0;
        res[1][3] =  0;

        res[2][0] =  0.89;
        res[2][1] =  0;
        res[2][2] =  -0.45;
        res[2][3] =  0;

        res[3][0] =  0;
        res[3][1] =  0;
        res[3][2] =  0;
        res[3][3] =  1;
        double [][] mat = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
        Matrice test = new Matrice(mat);
        Matrice rs = new Matrice(res);
        test.rotationY(90);
        DecimalFormat df = new DecimalFormat("0.00");
        test.getMatrice()[0][0] = Double.parseDouble(df.format(test.getMatrice()[0][0]).replace(',','.'));
        test.getMatrice()[0][2] = Double.parseDouble(df.format(test.getMatrice()[0][2]).replace(',','.'));
        test.getMatrice()[0][3] = Double.parseDouble(df.format(test.getMatrice()[0][3]).replace(',','.'));
        test.getMatrice()[2][0] = Double.parseDouble(df.format(test.getMatrice()[2][0]).replace(',','.'));
        test.getMatrice()[2][2] = Double.parseDouble(df.format(test.getMatrice()[2][2]).replace(',','.'));
        test.getMatrice()[2][3] = Double.parseDouble(df.format(test.getMatrice()[2][3]).replace(',','.'));
        
        assertEquals(rs,test);
    }

    @Test
    public void rotationZtest() {
     	double[][] res = new double[4][4];
        res[0][0] =  -0.45;
        res[0][1] =  -0.89;
        res[0][2] =  0;
        res[0][3] =  0;

        res[1][0] =  0.89;
        res[1][1] =  -0.45;
        res[1][2] =  0;
        res[1][3] =  0;

        res[2][0] =  0;
        res[2][1] =  0;
        res[2][2] =  1;
        res[2][3] =  0;

        res[3][0] =  0;
        res[3][1] =  0;
        res[3][2] =  0;
        res[3][3] =  1;
        double [][] mat = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
        Matrice test = new Matrice(mat);
        Matrice rs = new Matrice(res);
        test.rotationZ(90);
        DecimalFormat df = new DecimalFormat("0.00");
        test.getMatrice()[0][0] = Double.parseDouble(df.format(test.getMatrice()[0][0]).replace(',','.'));
        test.getMatrice()[0][1] = Double.parseDouble(df.format(test.getMatrice()[0][1]).replace(',','.'));
        test.getMatrice()[0][3] = Double.parseDouble(df.format(test.getMatrice()[0][3]).replace(',','.'));
        test.getMatrice()[1][0] = Double.parseDouble(df.format(test.getMatrice()[1][0]).replace(',','.'));
        test.getMatrice()[1][1] = Double.parseDouble(df.format(test.getMatrice()[1][1]).replace(',','.'));
        test.getMatrice()[1][3] = Double.parseDouble(df.format(test.getMatrice()[1][3]).replace(',','.'));
        
        assertEquals(rs,test);
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