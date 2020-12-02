package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Repere {
	private List<Point> pointsList;
	private List<Face> facesList;

	/**
	 * Constructeur d'un repère à partir de points et de faces définis
	 * 
	 * @param pointsList Liste de points à ajouter au repère
	 * @param facesList  Liste de faces à ajouter au repère
	 */
	public Repere(List<Point> pointsList, List<Face> facesList) {
		this.pointsList = pointsList;
		this.facesList = facesList;
	}

	/**
	 * Constructeur d'un repère vide sans points ni faces
	 */
	public Repere() {
		this(new ArrayList<Point>(), new ArrayList<Face>());
	}

	/**
	 * @return Liste des points du repère
	 */
	public List<Point> getPointsList() {
		return pointsList;
	}

	/**
	 * @return Liste des faces du repère
	 */
	public List<Face> getFacesList() {
		return facesList;
	}

	/**
	 * Renvoie le repère sous forme de chaîne de caractères
	 */
	@Override
	public String toString() {
		return "Repere [pointsList=" + this.pointsList + ", facesList=" + this.facesList + "]";
	}
	
	public boolean addPoint(Point p) {
		if(p != null && !pointsList.contains(p)) {
			this.pointsList.add(p);
			return true;
		}
		return false;
	}
	
	public Point getPoint(int idx) {
		Point p = this.pointsList.get(idx);
		if(p != null) 
			return p;
		return null;
	}
	
	public boolean addFace(Face f) {
		if(f != null && !facesList.contains(f)) {
			this.facesList.add(f);
			return true;
		}
		return false;
	}
	
	public Face getFace(int idx) {
		Face f = this.facesList.get(idx);
		if(f != null) 
			return f;
		return null;
	}

	/**
	 * Trouve le point le plus élevé sur l'axe des abscisses X
	 * 
	 * @return Point le plus élevé sur X
	 */
	public double getMax() {
		double max = this.pointsList.get(0).getX();
		for (Point p : this.pointsList) {
			if (p.getX() > max) {
				max = p.getX();
			}
			if (p.getY() > max) {
				max = p.getX();
			}
		}
		return max;
	}

	/**
	 * Calcule le centre du modèle sur l'axe des abscisses X
	 * 
	 * @return Centre du modèle sur X
	 */
	public double getCentreX() {
		double maxX;
		double minX;
		double centreX = 0;
		Point Q = this.pointsList.get(0);
		maxX = Q.getX();
		minX = Q.getX();
		for (Point p : this.pointsList) {
			if (p.getX() > maxX) {
				maxX = p.getX();
			} else if (p.getX() < minX) {
				minX = p.getX();
			}
		}
		centreX = (maxX - minX) / 2;
		return centreX;
	}

	/**
	 * Calcule le centre du modèle sur l'axe des abscisses Y
	 * 
	 * @return Centre du modèle sur Y
	 */
	public double getCentreY() {
		double MaxY;
		double MinY;
		double centreY = 0;
		Point Q = this.pointsList.get(0);
		MaxY = Q.getY();
		MinY = Q.getY();
		for (Point p : this.pointsList) {
			if (p.getY() > MaxY) {
				MaxY = p.getY();
			} else if (p.getY() < MinY) {
				MinY = p.getY();
			}
		}
		centreY = (MaxY - MinY) / 2;
		return centreY;
	}

	/**
	 * Effectue une translation du modèle
	 * 
	 * @param ratioX Ratio à ajouter à la coordonée homogène X de chaque point du
	 *               repère
	 * @param ratioY Ratio à ajouter à la coordonée homogène Y de chaque point du
	 *               repère
	 */
	public void translation(double ratioX, double ratioY) {
		for (Point p : this.pointsList) {
			p.setX(p.getX() + ratioX);
			p.setY(p.getY() + ratioY);
		}
	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses X
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	public void rotateX(Double grad) {
		for (Point p : pointsList) {
			Double y = p.getY();
			Double z = p.getZ();
			p.setY(Math.cos(grad) * y - Math.sin(grad) * z);
			p.setZ(Math.cos(grad) * z + Math.sin(grad) * y);
		}
	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses Y
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	public void rotateY(Double grad) {
		for (Point p : pointsList) {
			Double x = p.getX();
			Double z = p.getZ();
			p.setX(Math.cos(grad) * x - Math.sin(grad) * z);
			p.setZ(Math.cos(grad) * z + Math.sin(grad) * x);
		}
	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses Z
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	public void rotateZ(Double grad) {
		for (Point p : pointsList) {
			Double x = p.getX();
			Double y = p.getY();
			p.setX(Math.cos(grad) * x - Math.sin(grad) * y);
			p.setY(Math.cos(grad) * y + Math.sin(grad) * x);
		}
	}

	/**
	 * Effectue un zoom sur le modèle
	 * 
	 * @param scale Ratio à partir duquel calculer la matrice d'homothétie
	 */
	public void scaling(double scale) {
		for (Point p : this.pointsList) {
			p.setX(p.getX() * scale);
			p.setY(p.getY() * scale);
			p.setZ(p.getZ() * scale);
		}
	}

	/**
	 * Trie les faces du repère selon leur coordonée Z moyenne
	 */
	public void sortFaces() {
		int n = facesList.size();
		for (int i = 1; i < n; ++i) {
			Face key = facesList.get(i);
			int j = i - 1;
			while (j >= 0 && facesList.get(j).average() > key.average()) {
				facesList.set(j + 1, facesList.get(j));
				j = j - 1;
			}
			facesList.set(j + 1, key);
		}
	}
}