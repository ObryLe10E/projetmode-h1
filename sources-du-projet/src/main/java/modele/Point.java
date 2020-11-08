package modele;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Point {
	private double x;
	private double y;
	private double z;

	/**
	 * Constructeur d'un point 3D à partir de coordonnées définies
	 * 
	 * @param x Coordonnée homogène X du point à construire
	 * @param y Coordonnée homogène Y du point à construire
	 * @param z Coordonnée homogène Z du point à construire
	 */
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructeur d'un point aux coordonnées {0,0,0}
	 */
	public Point() {
		this(0, 0, 0);
	}

	/**
	 * @return Coordonnée x du point
	 */
	public double getX() {
		return x;
	}

	/**
	 * Modifie la coordonnée x du point
	 * 
	 * @param x Nouvelle coordonnée x à appliquer au point
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return Coordonnée y du point
	 */
	public double getY() {
		return y;
	}

	/**
	 * Modifie la coordonnée y du point
	 * 
	 * @param x Nouvelle coordonnée y à appliquer au point
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return Coordonnée z du point
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Modifie la coordonnée z du point
	 * 
	 * @param x Nouvelle coordonnée z à appliquer au point
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Renvoie le point sous forme de chaînes de caractère : Point(x,y,z)
	 */
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
}
