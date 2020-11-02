package modele;

import java.util.ArrayList;
import java.util.List;

public class Face {
	private List<Point> points;
	public static final int TAILLE = 4;

	public Face(List<Point> points) {
		if (points.size() <= TAILLE)
			this.points = points;
	}

	public Face() {
		this(new ArrayList<Point>());
	}

	public List<Point> getPoints() {
		return points;
	}

	public boolean addPoint(Point p) {
		if (this.points.contains(p) && this.points.size() > TAILLE)
			return false;
		else {
			this.points.add(p);
			return true;
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (Point p : this.points) {
			if (p != null)
				sb.append(",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1) + "]";
	}
	
	public double average() {
		double avg = 0.0;
		for(Point p : points) {
			avg += p.getZ();
		}
		return avg/points.size();
	}
}