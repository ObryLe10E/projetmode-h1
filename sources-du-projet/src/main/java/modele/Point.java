package modele;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Point {
	protected Matrice matrice;

	/**
	 * Constructeur d'un point 3D à partir de coordonnées définies
	 * 
	 * @param x Coordonnée homogène X du point à construire
	 * @param y Coordonnée homogène Y du point à construire
	 * @param z Coordonnée homogène Z du point à construire
	 */
	public Point(double x, double y, double z) {
		this.matrice = new Matrice(new double[][] { { x }, { y }, { z }, { 1.0 } });
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
		return matrice.getTableau()[0][0];
	}

	/**
	 * Modifie la coordonnée x du point
	 * 
	 * @param x Nouvelle coordonnée x à appliquer au point
	 */
	public void setX(double x) {
		matrice.getTableau()[0][0] = x;
	}

	/**
	 * @return Coordonnée y du point
	 */
	public double getY() {
		return matrice.getTableau()[1][0];
	}

	/**
	 * Modifie la coordonnée y du point
	 * 
	 * @param x Nouvelle coordonnée y à appliquer au point
	 */
	public void setY(double y) {
		matrice.getTableau()[1][0] = y;
	}

	/**
	 * @return Coordonnée z du point
	 */
	public double getZ() {
		return matrice.getTableau()[2][0];
	}

	/**
	 * Modifie la coordonnée z du point
	 * 
	 * @param x Nouvelle coordonnée z à appliquer au point
	 */
	public void setZ(double z) {
		matrice.getTableau()[2][0] = z;
	}

	/**
	 * Renvoie le point sous forme de chaînes de caractère : Point(x,y,z)
	 */
	public String toString() {
		return matrice.toString();
	}
	/**
	 * Recup�rer le point sous forme de matrice
	 * @return Une matrice correspondant au point
	 */
	public Matrice getMatrice() {
		return new Matrice(new double[][] { { getX() }, { getY() }, { getZ() }, { 1.0 } });
	}
	/**
	 * Translate un point a partir d'un vecteur v
	 * @param v -> vecteur de translation
	 */
	public void translation(Vecteur v) {
		matrice.translation(v);
	}
	/**
	 * Permet l'homothetie d'un point a partir d'un ratio
	 * @param ratio
	 */
	public void homothetie(double ratio) {
		matrice.homothetie(ratio);
	}
	/**
	 * Permet la rotation du point sur l'axe X
	 * @param angle en gradian
	 */
	public void rotationX(double angle) {
		matrice.rotationX(angle);
	}

	/**
	 * Permet la rotation du point sur l'axe Y
	 * @param angle en gradian
	 */
	public void rotationY(double angle) {
		matrice.rotationY(angle);
	}

	/**
	 * Permet la rotation du point sur l'axe Z
	 * @param angle en gradian
	 */

	public void rotationZ(double angle) {
		matrice.rotationZ(angle);
	}
}