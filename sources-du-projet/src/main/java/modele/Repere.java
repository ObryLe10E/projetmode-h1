package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

	public void scaling(double scale) {
		for (Point p : this.pointsList) {
			p.setX(p.getX() * scale);
			p.setY(p.getY() * scale);
			p.setZ(p.getZ() * scale);
		}
	}

	public void sortFaces() {
		int n = facesList.size(); 
		for (int i = 1; i < n; ++i) { 
			Face key = facesList.get(i); 
			int j = i - 1; 
			while (j >= 0 && facesList.get(j).average() < key.average()) {
				facesList.set(j+1, facesList.get(j));
				j = j - 1; 
			} 
			facesList.set(j+1, key);
		}
	}
	public void printAvg() {
		for(Face f : facesList) {
			System.out.println("Moyenne de la face : "+ f.average());
		}
	}
}