package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Repere {
	private List<Point> pointsList;
	private List<Face> facesList;
	private double xOffSet =0.0;
	private double yOffSet =0.0;

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
		System.out.println(this.xOffSet + "    "+this.yOffSet );
		this.xOffSet += ratioX;
		this.yOffSet += ratioY;
		this.translation2(ratioX, ratioY, 0);
	}
	public void translation2(double ratioX, double ratioY, double ratioZ) {
		for (Point p : this.pointsList) 
			p.translation(new Vecteur(ratioX,ratioY,ratioZ));
	}

	public double averageZ() {
		double avg = 0.0;
		for (Point p : this.pointsList) {
			avg += p.getZ();
		}
		return avg / this.pointsList.size();
	}
	public double averageX() {
		double avg = 0.0;
		for (Point p :this.pointsList) {
			avg += p.getX();
		}
		return avg / this.pointsList.size();
	}
	public double averageY() {
		double avg = 0.0;
		for (Point p : this.pointsList) {
			avg += p.getY();
		}
		return avg / this.pointsList.size();
	}
	public void center() {
		this.translation2(-this.averageX(), -this.averageY(), -this.averageZ()); 
	}
	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses X
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */	
	public void rotateX(Double grad) {
		this.translation2(-this.xOffSet, -this.yOffSet, 0.0);
		for (Point p : pointsList) {
			p.rotationX(grad);
		
//			Double y = p.getY();
//			Double z = p.getZ();
//			p.setY(Math.cos(grad) * y - Math.sin(grad) * z);
//			p.setZ(Math.cos(grad) * z + Math.sin(grad) * y);
		}
		this.translation2(this.xOffSet, this.yOffSet, 0.0);
	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses Y
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	
	public void rotateY(Double grad) {
		System.out.println(this.xOffSet + "    "+this.yOffSet );
		this.translation2(-this.xOffSet, -this.yOffSet, 0.0);

		for (Point p : pointsList) {
			p.rotationY(grad);
			
		
//			Double y = p.getY();
//			Double z = p.getZ();
//			p.setY(Math.cos(grad) * y - Math.sin(grad) * z);
//			p.setZ(Math.cos(grad) * z + Math.sin(grad) * y);
		}
		this.translation2(this.xOffSet, this.yOffSet, 0.0);

	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses Z
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	public void rotateZ(Double grad) {
		this.translation2(-this.xOffSet,-this.yOffSet , 0.0);
		for (Point p : pointsList) {
			p.rotationZ(grad);
//			Double x = p.getX();
//			Double y = p.getY();
//			p.setX(Math.cos(grad) * x - Math.sin(grad) * y);
//			p.setY(Math.cos(grad) * y + Math.sin(grad) * x);
		}
		this.translation2(this.xOffSet,this.yOffSet , 0.0);
	}

	/**
	 * Effectue un zoom sur le modèle
	 * 
	 * @param scale Ratio à partir duquel calculer la matrice d'homothétie
	 */
	public void scaling(double scale) {
		this.translation2(-this.xOffSet, -this.yOffSet,0);
		for (Point p : this.pointsList) p.homothetie(scale);
		this.translation2(this.xOffSet, this.yOffSet,0);
	}

	/**
	 * Trie les faces du repère selon leur coordonée Z moyenne
	 * voir si on peut utiliser list.sort en implémentant comparable/comparator
	 */
	public void sortFaces() {
		Collections.sort(facesList,new FaceComparator());
		/*int n = facesList.size();
		for (int i = 1; i < n; ++i) {
			Face key = facesList.get(i);
			int j = i - 1;
			while (j >= 0 && facesList.get(j).average() > key.average()) {
				facesList.set(j + 1, facesList.get(j));
				j = j - 1;
			}
			facesList.set(j + 1, key);
		}*/
	}
}