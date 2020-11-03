package modele;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.transform.MatrixType;

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

	public void rotateX() {
		for(Point p : pointsList ) {
			Double y = p.getY();
			Double z = p.getZ();
			p.setY(Math.cos(Math.PI/4)*y-Math.sin(Math.PI/4)*z);
			p.setZ(Math.cos(Math.PI/4)*z+Math.sin(Math.PI/4)*y);
		}
	}
	public void rotateY() {
		for(Point p : pointsList ) {
			Double x = p.getX();
			Double z = p.getZ();
			p.setX(Math.cos(Math.PI/4)*x-Math.sin(Math.PI/4)*z);
			p.setZ(Math.cos(Math.PI/4)*z+Math.sin(Math.PI/4)*x);
		}
	}
	public void rotateZ() {
		for(Point p : pointsList ) {
			Double x = p.getX();
			Double y = p.getY();
			p.setX(Math.cos(Math.PI/4)*x-Math.sin(Math.PI/4)*y);
			p.setY(Math.cos(Math.PI/4)*y+Math.sin(Math.PI/4)*x);
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
			while (j >= 0 && facesList.get(j).average() > key.average()) {
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