package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Groupe H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Face implements Comparable<Face>{
	private List<Point> points;
	private int r;
	private int g;
	private int b;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Face other = (Face) obj;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		return true;
	}
	/**
	 * Permet de r�cup�rer le nombre de points, la taille de la face.
	 * @return Le nombre d'�l�ments dans la liste points -> Taille de la face
	 */
	public int size() {
		return points.size();
	}
	/**
	 * Permet de r�cup�rer un point a un indice donn�
	 * @param idx -> indice auquel on r�cupere le point dans la liste
	 * @return un Point
	 */
	public Point get(int idx) {
		return points.get(idx);
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
	public Double averageZ() {
		double avg = 0.0;
		for (Point p : points)
			avg += p.getZ();
		return avg / points.size();
	}
	/**
	 * Permet de r�cuperer le vecteur normal
	 * @return vecteur normal de la face
	 */
	public Vecteur getVecteurNormal() {
		if (points.size() >= 3) {
			Vecteur ab = new Vecteur(points.get(0), points.get(1));
			Vecteur ac = new Vecteur(points.get(0), points.get(2));
			return ab.produitVectoriel(ac);
		}
		return new Vecteur();
	}
	/**
	 * Permet de r�cuperer le vecteur normal unitaire
	 * @return vnu de la face
	 */
	private Vecteur getVecteurNormalUnitaire() {
		Vecteur v = this.getVecteurNormal();
		return v.diviser(v.normeVectoriel());
	}
	/**
	 * Permet de r�cuperer le coefficient de lumi�re
	 * @param l -> vecteur lumi�re
	 * @return le coefficient de lumi�re de la face
	 */


	private double getEclairage(Vecteur l) {
		return this.getVecteurNormalUnitaire().produitScalaire(l.diviser(l.normeVectoriel()));
	}
	/**
	 * Permet de r�cuperer la couleur de la face en fonction de la lumi�re et de la position du vecteur lumi�re
	 * @param rgb couleur initiale
	 * @param grad angle de rotation du vecteur eclairage
	 * @return la couleur apr�s transformation
	 */
	public int getColor(double rgb, double grad) {
		Vecteur vecteurEclairage = new Vecteur(0,0,1);
		vecteurEclairage.rotationY(grad);
		int luminosite = (int) (this.getEclairage(vecteurEclairage) * (rgb*255));
		return luminosite;
	}

	@Override
	public int compareTo(Face other) {
		return this.averageZ().compareTo(other.averageZ());
	}
}