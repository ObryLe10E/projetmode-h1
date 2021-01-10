package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.paint.Color;
import utils.Subject;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Repere extends Subject{
	private List<Point> pointsList;
	private List<Face> facesList;
	private double xOffSet =0.0;
	private double yOffSet =0.0;
	public double lightAngle;
	public Color faceColor;
	public Color strokeColor;

	/**
	 * Constructeur d'un repère à partir de points et de faces définis
	 * 
	 * @param pointsList Liste de points à ajouter au repère
	 * @param facesList  Liste de faces à ajouter au repère
	 */
	public Repere(List<Point> pointsList, List<Face> facesList) {
		this.pointsList = pointsList;
		this.facesList = facesList;
		faceColor = Color.GRAY; strokeColor = Color.BLACK;
		lightAngle = 0.0;
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
	 * Permet de fixer un angle de lumi�re
	 * @param angle
	 */
	public void setLightAngle(double angle) {
		lightAngle = angle;
		notifyObservers();
	}
/**
 * Permet de fixer une coleur aux faces
 * @param color
 */
	public void setFaceColor(Color color) {
		this.faceColor = color;
		notifyObservers();
	}
/**
 * Permet de fixer une couleur des fils a un model
 * @param color
 */
	public void setStrokeColor(Color color) {
		this.strokeColor = color;
		notifyObservers();
	}
/**
 * Permet de centrer la figure en fonction de la taille de la fenetre
 * @param length
 * @param height
 */
	public void frame(double length, double height) {
		double scl = length / (this.getMaxX() - this.getMinX());
		double sch = height / (this.getMaxY() - this.getMinY());
		this.scaling(((sch < scl) ? sch : scl));
	}
/**
 * 
 * @return le Y minimum 
 */
	private double getMinY() {
		double min = this.getPointsList().get(0).getY();
		double tmp;
		for (int i = 1; i < this.getPointsList().size(); i++) {
			tmp = this.getPointsList().get(i).getY();
			if (tmp < min) min = tmp;
		}
		return min;
	}
	/**
	 * 
	 * @return le X minimum
	 */
	private double getMinX() {
		double min = this.getPointsList().get(0).getX();
		double tmp;
		for (int i = 1; i < this.getPointsList().size(); i++) {
			tmp = this.getPointsList().get(i).getX();
			if (tmp < min) min = tmp;
		}
		return min;
	}
	/**
	 * 
	 * @return le X maximum
	 */
	private double getMaxX() {
		double min = this.getPointsList().get(0).getX();
		double tmp;
		for (int i = 1; i < this.getPointsList().size(); i++) {
			tmp = this.getPointsList().get(i).getX();
			if (tmp > min) min = tmp;
		}
		return min;
	}
	/**
	 * 
	 * @return le Y maximum
	 */
	private double getMaxY() {
		double min = this.getPointsList().get(0).getY();
		double tmp;
		for (int i = 1; i < this.getPointsList().size(); i++) {
			tmp = this.getPointsList().get(i).getY();
			if (tmp > min) min = tmp;
		}
		return min;
	}

	/**
	 * Trouve le point le plus élevé sur l'axe des abscisses X
	 * 
	 * @return Point le plus élevé sur X
	 */
	public double getMax() {
		double max = pointsList.get(0).getX();
		for (Point p : this.pointsList) {
			if (p.getX() > max) max = p.getX();
			if (p.getY() > max) max = p.getY();
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
		Point Q = pointsList.get(0);
		maxX = Q.getX();
		minX = Q.getX();
		for (Point p : pointsList) {
			if (p.getX() > maxX)maxX = p.getX();
			else if (p.getX() < minX) minX = p.getX();
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
		Point Q = pointsList.get(0);
		MaxY = Q.getY();
		MinY = Q.getY();
		for (Point p : pointsList) {
			if (p.getY() > MaxY)
				MaxY = p.getY();
			else if (p.getY() < MinY) 
				MinY = p.getY();
		}
		centreY = (MaxY - MinY) / 2;
		return centreY;
	}

	/**
	 * Effectue une translation du modèle en stockant l'offSet de x et y
	 * 
	 * @param ratioX Ratio à ajouter à la coordonée homogène X de chaque point du
	 *               repère
	 * @param ratioY Ratio à ajouter à la coordonée homogène Y de chaque point du
	 *               repère
	 */
	public void translation(double ratioX, double ratioY) {
		this.xOffSet += ratioX;
		this.yOffSet += ratioY;
		this.translation2(ratioX, ratioY, 0);
		notifyObservers();
	}
	/**
	 *  Effectue une translation basique
	 * @param ratioX 
	 * @param ratioY
	 * @param ratioZ
	 */
	public void translation2(double ratioX, double ratioY, double ratioZ) {
		for (Point p : pointsList) 
			p.translation(new Vecteur(ratioX,ratioY,ratioZ));
	}
	/**
	 * 
	 * @return la moyenne des Z
	 */
	public double averageZ() {
		double avg = 0.0;
		for (Point p : pointsList)
			avg += p.getZ();
		return avg / pointsList.size();
	}
	/**
	 * 
	 * @return la moyenne des X
	 */
	public double averageX() {
		double avg = 0.0;
		for (Point p : pointsList)
			avg += p.getX();
		return avg / pointsList.size();
	}
	/**
	 * 
	 * @return la moyenne des Y
	 */
	public double averageY() {
		double avg = 0.0;
		for (Point p : pointsList)
			avg += p.getY();
		return avg / pointsList.size();
	}
	/**
	 * Permet de centrer la figure
	 */
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
		for (Point p : pointsList)
			p.rotationX(grad);
		this.translation2(this.xOffSet, this.yOffSet, 0.0);
		notifyObservers();
	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses Y
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	public void rotateY(Double grad) {
		this.translation2(-this.xOffSet, -this.yOffSet, 0.0);
		for (Point p : pointsList)
			p.rotationY(grad);
		this.translation2(this.xOffSet, this.yOffSet, 0.0);
		notifyObservers();
	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses Z
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	public void rotateZ(Double grad) {
		this.translation2(-this.xOffSet,-this.yOffSet , 0.0);
		for (Point p : pointsList)
			p.rotationZ(grad);
		this.translation2(this.xOffSet,this.yOffSet , 0.0);
		notifyObservers();
	}

	/**
	 * Effectue un zoom sur le modèle
	 * 
	 * @param scale Ratio à partir duquel calculer la matrice d'homothétie
	 */
	public void scaling(double scale) {
		this.translation2(-this.xOffSet, -this.yOffSet,0);
		for (Point p : pointsList) 
			p.homothetie(scale);
		this.translation2(this.xOffSet, this.yOffSet,0);
		notifyObservers();
	}

	/**
	 * Trie les faces du repère selon leur coordonée Z moyenne
	 * voir si on peut utiliser list.sort en implémentant comparable/comparator
	 */
	public void sortFaces() {
		Collections.sort(facesList);
	}
}