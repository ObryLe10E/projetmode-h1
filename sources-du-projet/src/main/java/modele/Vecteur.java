package modele;

public class Vecteur extends Point {
	
	public Vecteur() {
		super();
	}
	
	public Vecteur(double x, double y, double z) {
		super(x,y,z);
	}
	
	public Vecteur(Point a, Point b) { // Bien joué 
		super(b.getX() - a.getX(), b.getY() - a.getY(), b.getZ() - a.getZ());
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
	
	public double [] getMatrice() {
		return new double [] {this.getX(),this.getY(),this.getZ(),1};
	}
}
