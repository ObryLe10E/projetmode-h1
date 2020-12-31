package modele;

public class Matrice {
	private double[][] matrice;
	
	public Matrice(double [][] m) {
		this.matrice = m;
	}
	public double[][] getMatrice() {
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
		double [][] matA = this.getMatrice();
		double [][] matB = m.getMatrice();
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
	
	
}
