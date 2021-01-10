package modele;

import java.util.Arrays;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Matrice {
	private double[][] matrice;
	
	/**
	 * Constructeur de matrice a partir d'un tableau de double 2D
	 * @param matrice -> tableau de double 2D
	 */
	public Matrice(double [][] matrice) {
		this.matrice = matrice;
	}
	
	/**
	 * Permet de rï¿½cuperer la matrice sous forme de tableau 2D
	 * @return matrice en tableau de double 2D
	 */
	public double[][] getTableau() {
		return this.matrice;
	}
	
	/**
	 * Permet d'affecter une matrice a partir d'un tableau double 2D
	 * @param matrice -> tableau de double 2D
	 */
	public void setMatrice(double[][] matrice) {
		this.matrice = matrice;
	}
	/**
	 * Permet de récuprer une matrice
	 * @return double[][] 
	 */
	public double[][] getMatrice() {
		return matrice;
	}

	/**
	 * Recuperer la taille des lignes de la matrice
	 * @return la taille des lignes
	 */
	public int getLineSize() {
		return this.matrice.length;
	}
	
	/**
	 * Recuperer la taille des colonnes de la matrice
	 * @return la taille des colonnes
	 */
	public int getColumnSize() {
		return this.matrice[0].length;
	}
	
	/**
	 * Permet de multiplier 2 matrices
	 * @param matrice -> matrice avec laquelle on multiplie la matrice qui appelle cette methode
	 * @return le resultat de la multiplication des deux matrices
	 */
	public Matrice multiply(Matrice matrice) {
		double [][] matA = this.getTableau();
		double [][] matB = matrice.getTableau();
		int linesInA = this.getLineSize();
		int columnsInA = this.getColumnSize();
		int columnsInB = matrice.getColumnSize();
		double[][] result = new double[linesInA][columnsInB];
		for (int i = 0; i < linesInA; i++) {
			for(int j = 0; j < columnsInB; j++) {
				for(int k = 0; k < columnsInA; k++) { // Itere sur les colonnes de chaque ligne de A
					result[i][j] = result[i][j] + matA[i][k] * matB[k][j];
				}
			}
		}
		return new Matrice(result);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(matrice);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		Matrice mat = (Matrice) obj;
		int ligne = 0;
		int colone =0;
		for(double l[] : this.getTableau()) {
			for(double c : l) {
				if(c !=  mat.getTableau()[ligne][colone]) {
					return false;
				}
				colone ++;
			}
			ligne ++;
			colone = 0;
		}
		return true;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(double l[] :this.getTableau()) {
			builder.append("[ ");
			for(double c : l) {
				builder.append(c+" ");
			}
			builder.append("]\n");
		}
		return builder.toString();
	}
	
	/**
	 * Permet la translation d'une matrice
	 * @param vecteur le vecteur translation
	 */
	public void translation(Vecteur vecteur) {
		Matrice translation = new Matrice(new double[][] { { 1, 0, 0, vecteur.getX() }, 
			{ 0, 1, 0, vecteur.getY() },
			{ 0, 0, 1, vecteur.getZ() }, 
			{ 0, 0, 0, 1 },});
		translation = translation.multiply(this);
		this.matrice = translation.getTableau();
	}
	
	/**
	 * Permet de faire l'homothetie
	 * @param ratio de l'homothetie
	 */
	public void homothetie(double ratio) {
		Matrice homothetie = new Matrice(new double[][] { { ratio, 0, 0, 0 }, 
			{ 0, ratio, 0, 0 }, 
			{ 0, 0, ratio, 0 },
			{ 0, 0, 0, 1 },});
		homothetie = homothetie.multiply(this);
		this.matrice = homothetie.getTableau();
	}
	
	/**
	 * Permet de faire la rotation sur l'axe X de la matrice
	 * @param angle en gradian
	 */
	public void rotationX(double angle) {
		Matrice rotation = new Matrice(new double[][] { { 1, 0, 0, 0 }, 
			{ 0, Math.cos(angle), -Math.sin(angle), 0 }, 
			{ 0, Math.sin(angle), Math.cos(angle), 0 },
			{ 0, 0, 0, 1 },});
		rotation = rotation.multiply(this);
		this.matrice = rotation.getTableau();
	}

	/**
	 * Permet de faire la rotation sur l'axe Y de la matrice
	 * @param angle en gradian
	 */
	public void rotationY(double angle) {
		Matrice rotation = new Matrice(new double[][] { {  Math.cos(angle), 0, -Math.sin(angle), 0 }, 
			{ 0, 1, 0, 0 }, 
			{ Math.sin(angle), 0, Math.cos(angle), 0 },
			{ 0, 0, 0, 1 },});
		rotation = rotation.multiply(this);
		this.matrice = rotation.getTableau();
	}

	/**
	 * Permet de faire la rotation sur l'axe Z de la matrice
	 * @param angle en gradian
	 */
	public void rotationZ(double angle) {
		Matrice rotation = new Matrice(new double[][] { { Math.cos(angle), -Math.sin(angle), 0, 0 }, 
			{ Math.sin(angle), Math.cos(angle), 0, 0 }, 
			{ 0, 0, 1, 0 },
			{ 0, 0, 0, 1 },});
		rotation = rotation.multiply(this);
		this.matrice = rotation.getTableau();
	}
}