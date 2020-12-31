package modele;

public class Vecteur extends Point {
	
	public Vecteur() {
		this.matrice = new Matrice(new double[][] { { 0 }, { 0 }, { 0 }, { 0.0 } });
	}
	
	public Vecteur(double x, double y, double z) {
		this.matrice = new Matrice(new double[][] { { x }, { y }, { z }, { 0.0 } });
	}
	
	public Vecteur(Point a, Point b) {
		this.matrice = new Matrice(new double[][] { { b.getX() - a.getX() }, { b.getY() - a.getY() }, { b.getZ() - a.getZ() }, { 0.0 } });
//		super(b.getX() - a.getX(), b.getY() - a.getY(), b.getZ() - a.getZ());
	}
	
	public Vecteur produitVectoriel(Point p) {
		double x = this.getY() * p.getZ() - this.getZ() * p.getY();
		double y = this.getZ() * p.getX() - this.getX() * p.getZ();
		double z = this.getX() * p.getY() - this.getY() * p.getX();
		
		return new Vecteur(x,y,z);
	}
	
	public double produitScalaire(Point p) {
		return this.getX() * p.getX() + this.getY() * p.getY() + this.getZ() + p.getZ();
	}
	
	public double normeVectoriel() {
		double ans = Math.pow(this.getX(),2) +  Math.pow(this.getY(),2) +  Math.pow(this.getZ(),2);
		return Math.sqrt(ans);
		
	}
	
	public Matrice getMatrice() {
		return new Matrice(new double[][] { { getX() }, { getY() }, { getZ() }, { 0.0 } });
	}
}