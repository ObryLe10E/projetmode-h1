package modele;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Matrice {
	private double[][] matrice;
	/**
	 * Constructeur de matrice a partir d'un tableau de double 2D
	 * @param m -> tableau de double 2D
	 */
	public Matrice(double [][] m) {
		this.matrice = m;
	}
	/**
	 * Permet de récuperer la matrice sous forme de tableau 2D
	 * @return matrice en tableau de double 2D
	 */
	public double[][] getTableau() {
		return this.matrice;
	}
	/**
	 * Permet d'affecter une matrice a partir d'un tableau double 2D
	 * @param m -> tableau de double 2D
	 */
	public void setMatrice(double[][] m) {
		this.matrice = m;
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
	 * @param m -> matrice avec laquelle on multiplie la matrice qui appelle cette methode
	 * @return le resultat de la multiplication des deux matrices
	 */
	public Matrice multiply(Matrice m) {
		double [][] matA = this.getTableau();
		double [][] matB = m.getTableau();
		int linesInA = this.getLineSize();
		int columnsInA = this.getColumnSize();
		int columnsInB = m.getColumnSize();
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
	/**
	 * Permet la translation d'une matrice
	 * @param v le vecteur translation
	 */
	public void translation(Vecteur v) {
		Matrice matriceTranslation = new Matrice(new double[][] { { 1, 0, 0, v.getX() }, 
			{ 0, 1, 0, v.getY() },
			{ 0, 0, 1, v.getZ() }, 
			{ 0, 0, 0, 1 },});
		this.matrice = matriceTranslation.multiply(this).getTableau();
	}
	/**
	 * Permet de faire l'homothetie
	 * @param ratio de l'homothetie
	 */
	public void homothetie(double ratio) {
		Matrice matriceHomothetie = new Matrice(new double[][] { { ratio, 0, 0, 0 }, 
			{ 0, ratio, 0, 0 }, 
			{ 0, 0, ratio, 0 },
			{ 0, 0, 0, 1 },});
		this.matrice = matriceHomothetie.multiply(this).getTableau();
	}
	/**
	 * Permet de faire la rotation sur l'axe X de la matrice
	 * @param angle en gradian
	 */
	public void rotationX(double angle) {
		Matrice matriceRotation = new Matrice(new double[][] { { 1, 0, 0, 0 }, 
			{ 0, Math.cos(angle), -Math.sin(angle), 0 }, 
			{ 0, Math.sin(angle), Math.cos(angle), 0 },
			{ 0, 0, 0, 1 },});
		this.matrice = matriceRotation.multiply(this).getTableau();
	}

	/**
	 * Permet de faire la rotation sur l'axe Y de la matrice
	 * @param angle en gradian
	 */
	public void rotationY(double angle) {
		Matrice matriceRotation = new Matrice(new double[][] { {  Math.cos(angle), 0, -Math.sin(angle), 0 }, 
			{ 0, 1, 0, 0 }, 
			{ Math.sin(angle), 0, Math.cos(angle), 0 },
			{ 0, 0, 0, 1 },});
		this.matrice = matriceRotation.multiply(this).getTableau();
	}

	/**
	 * Permet de faire la rotation sur l'axe Z de la matrice
	 * @param angle en gradian
	 */
	public void rotationZ(double angle) {
		Matrice matriceRotation = new Matrice(new double[][] { { Math.cos(angle), -Math.sin(angle), 0, 0 }, 
			{ Math.sin(angle), Math.cos(angle), 0, 0 }, 
			{ 0, 0, 1, 0 },
			{ 0, 0, 0, 1 },});
		this.matrice = matriceRotation.multiply(this).getTableau();
	}
}