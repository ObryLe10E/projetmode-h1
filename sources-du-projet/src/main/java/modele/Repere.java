package modele;

import java.util.ArrayList;
import java.util.List;

public class Repere {
	private List<Point> pointsList;
	private List<Face> facesList;

	public Repere(List<Point> pointsList, List<Face> facesList) {
		this.pointsList = pointsList;
		this.facesList = facesList;
	}

	public Repere() {
		this(new ArrayList<Point>(), new ArrayList<Face>());
	}

	public List<Point> getPointsList() {
		return pointsList;
	}

	public List<Face> getFacesList() {
		return facesList;
	}

	@Override
	public String toString() {
		return "Repere [pointsList=" + this.pointsList + ", facesList=" + this.facesList + "]";
	}

	public void translation(double ratioX, double ratioY) {
		for (Point p : this.pointsList) {
			p.setX(p.getX() + ratioX);
			p.setY(p.getY() + ratioY);
		}
	}
}