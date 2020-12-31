package modele;

public class Matrice {
	private double[][] matrice;
	
	public Matrice(double [][] m) {
		this.matrice = m;
	}
	
	public double[][] getTableau() {
		return this.matrice;
	}
	
	public void setMatrice(double[][] m) {
		this.matrice = m;
	}
	
	public int getLineSize() {
		return this.matrice.length;
	}
	
	public int getColumnSize() {
		return this.matrice[0].length;
	}
	
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
	
	public void translation(Vecteur v) {
		Matrice matriceTranslation = new Matrice(new double[][] { { 1, 0, 0, v.getX() }, 
																  { 0, 1, 0, v.getY() },
																  { 0, 0, 1, v.getZ() }, 
																  { 0, 0, 0, 1 },});
		this.matrice = matriceTranslation.multiply(this).getTableau();
	}
	
	public void homothetie(double ratio) {
		Matrice matriceHomothetie = new Matrice(new double[][] { { ratio, 0, 0, 0 }, 
														   		 { 0, ratio, 0, 0 }, 
														   		 { 0, 0, ratio, 0 },
														   		 { 0, 0, 0, 1 },});
		this.matrice = matriceHomothetie.multiply(this).getTableau();
	}
	
	public void rotationX(double angle) {
		Matrice matriceRotation = new Matrice(new double[][] { { 1, 0, 0, 0 }, 
														   	   { 0, Math.cos(angle), -Math.sin(angle), 0 }, 
														       { 0, Math.sin(angle), Math.cos(angle), 0 },
														       { 0, 0, 0, 1 },});
		this.matrice = matriceRotation.multiply(this).getTableau();
	}
	
	public void rotationY(double angle) {
		Matrice matriceRotation = new Matrice(new double[][] { {  Math.cos(angle), 0, -Math.sin(angle), 0 }, 
														   	   { 0, 1, 0, 0 }, 
														       { Math.sin(angle), 0, Math.cos(angle), 0 },
														       { 0, 0, 0, 1 },});
		this.matrice = matriceRotation.multiply(this).getTableau();
	}
	
	public void rotationZ(double angle) {
		Matrice matriceRotation = new Matrice(new double[][] { { Math.cos(angle), -Math.sin(angle), 0, 0 }, 
														   	   { Math.sin(angle), Math.cos(angle), 0, 0 }, 
														       { 0, Math.sin(angle), 1, 0 },
														       { 0, 0, 0, 1 },});
		this.matrice = matriceRotation.multiply(this).getTableau();
	}
}