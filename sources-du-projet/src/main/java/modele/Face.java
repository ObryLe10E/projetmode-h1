package modele;

import java.util.ArrayList;
import java.util.List;

public class Face {
	// private static int faceTag = 0;
	// private final int ID;

	private List<Point> points;
	public static final int TAILLE = 4;

	public Face(List<Point> points) {
		if (points.size() <= TAILLE)
			this.points = points;
		// this.ID = faceTag;
		// faceTag++;
		// } else
		// this.ID = 0;
	}

	public Face() {
		this(new ArrayList<Point>());
	}

	public List<Point> getPoints() {
		return points;
	}

	// public int getID() {
	// return ID;
	// }

	public boolean addPoint(Point p) {
		if (this.points.contains(p) && this.points.size() > TAILLE)
			return false;
		else {
			this.points.add(p);
			return true;
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(/* "" + this.getID() + */"[");
		for (Point p : this.points) {
			if (p != null)
				sb.append(/* p.getID() + */",");
		}
		return sb.toString().substring(0, sb.toString().length() - 1) + "]";
	}

	// public static void resetID() {
	// Face.faceTag = 0;
	// }
}