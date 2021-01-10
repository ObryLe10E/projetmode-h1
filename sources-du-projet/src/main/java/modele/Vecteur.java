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
	 * Constructeur d'un vecteur a partir de trois coordonées
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vecteur(double x, double y, double z) {
		this.matrice = new Matrice(new double[][] { { x }, { y }, { z }, { 0.0 } });
	}
	/**
	 * Construteur d'un vecteur a partir de deux points
	 * @param a
	 * @param b
	 */
	public Vecteur(Point a, Point b) {
		this.matrice = new Matrice(new double[][] { { b.getX() - a.getX() }, { b.getY() - a.getY() }, { b.getZ() - a.getZ() }, { 0.0 } });
	}
	/**
	 * Permet de faire un produit vectoriel
	 * @param p
	 * @return
	 */
	public Vecteur produitVectoriel(Point p) {
		double x = this.getY() * p.getZ() - this.getZ() * p.getY();
		double y = this.getZ() * p.getX() - this.getX() * p.getZ();
		double z = this.getX() * p.getY() - this.getY() * p.getX();
		return new Vecteur(x,y,z);
	}
	/**
	 * Permet de faire un produit scalaire
	 * @param p
	 * @return
	 */
	public double produitScalaire(Point p) {
		return this.getX() * p.getX() + this.getY() * p.getY() + this.getZ() * p.getZ();
	}
	/**
	 * 
	 * @return la norme du vecteur this
	 */
	public double normeVectoriel() {
		double ans = Math.pow(this.getX(),2) +  Math.pow(this.getY(),2) +  Math.pow(this.getZ(),2);
		return Math.sqrt(ans);
		
	}
	/**
	 * @return le vecteur sous forme de matrice
	 */
	public Matrice getMatrice() {
		return new Matrice(new double[][] { { getX() }, { getY() }, { getZ() }, { 0.0 } });
	}
	/**
	 * Permet de diviser un vecteur
	 * @param d
	 * @return
	 */
	public Vecteur diviser(double d) {
		double x = this.getX() / d;
		double y = this.getY() / d;
		double z = this.getZ() / d;
		return new Vecteur(x, y, z);
	}
}