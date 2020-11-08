package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Face {
	private List<Point> points;
	// public static final int TAILLE = 4;

	/**
	 * Constructeur d'une Face à partir d'une liste de points définis
	 * 
	 * @param points Liste des points à partir desquels on crée la Face
	 */
	public Face(List<Point> points) {
		this.points = points;
	}

	/**
	 * Constructeur d'une Face vide sans points définis
	 */
	public Face() {
		this(new ArrayList<Point>());
	}

	/**
	 * @return Liste des points de la Face
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * Ajoute un Point à ceux de la Face
	 * 
	 * @param p Point à ajouter à la Face
	 * @return Vrai si le Point a été ajouté, faux sinon
	 */
	public boolean addPoint(Point p) {
		if (this.points.contains(p) /* && this.points.size() > TAILLE */)
			return false;
		else {
			this.points.add(p);
			return true;
		}
	}

	/**
	 * Renvoie la Face sous forme de chaîne de caractères : Face[x,y,z]
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (Point p : this.points) {
			if (p != null)
				sb.append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1) + "]";
	}

	/**
	 * Calcule la moyenne des coordonnées Z de la Face
	 * 
	 * @return Z moyen de la Face
	 */
	public double average() {
		double avg = 0.0;
		for (Point p : points) {
			avg += p.getZ();
		}
		return avg / points.size();
	}
}