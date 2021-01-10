package modele;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Vecteur extends Point {
	/**
	 * Constructeur d'un vecteur nul
	 */
	public Vecteur() {
		this.matrice = new Matrice(new double[][] { { 0 }, { 0 }, { 0 }, { 0.0 } });
	}
	/**
	 * Constructeur d'un vecteur a partir de trois coordon�es
	 * @param pointX
	 * @param pointY
	 * @param pointZ
	 */
	public Vecteur(double pointX, double pointY, double pointZ) {
		this.matrice = new Matrice(new double[][] { { pointX }, { pointY }, { pointZ }, { 0.0 } });
	}
	/**
	 * Construteur d'un vecteur a partir de deux points
	 * @param pointA
	 * @param pointB
	 */
	public Vecteur(Point pointA, Point pointB) {
		this.matrice = new Matrice(new double[][] { { pointB.getX() - pointA.getX() }, { pointB.getY() - pointA.getY() }, { pointB.getZ() - pointA.getZ() }, { 0.0 } });
	}
	/**
	 * Permet de faire un produit vectoriel
	 * @param point
	 * @return
	 */
	public Vecteur produitVectoriel(Point point) {
		double pointX = this.getY() * point.getZ() - this.getZ() * point.getY();
		double pointY = this.getZ() * point.getX() - this.getX() * point.getZ();
		double pointZ = this.getX() * point.getY() - this.getY() * point.getX();
		return new Vecteur(pointX,pointY,pointZ);
	}
	/**
	 * Permet de faire un produit scalaire
	 * @param point
	 * @return
	 */
	public double produitScalaire(Point point) {
		return this.getX() * point.getX() + this.getY() * point.getY() + this.getZ() * point.getZ();
	}
	/**
	 * 
	 * @return la norme du vecteur this
	 */
	public double normeVectorielle() {
		double ans = Math.pow(this.getX(),2) +  Math.pow(this.getY(),2) +  Math.pow(this.getZ(),2);
		return Math.sqrt(ans);
	}
	/**
	 * La matrice du vecteur
	 * @return la matrice du vecteur sous forme de tableau
	 */
	public double[][] getTableau(){
		return matrice.getTableau();
	}
	/**
	 * @return le vecteur sous forme de matrice
	 */
	public Matrice getMatrice() {
		return new Matrice(new double[][] { { getX() }, { getY() }, { getZ() }, { 0.0 } });
	}
	/**
	 * Permet de diviser un vecteur
	 * @param diviseur
	 * @return
	 */
	public Vecteur diviser(double diviseur) {
		double pointX = this.getX() / diviseur;
		double pointY = this.getY() / diviseur;
		double pointZ = this.getZ() / diviseur;
		return new Vecteur(pointX, pointY, pointZ);
	}
}