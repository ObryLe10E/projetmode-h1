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
	
	public double getMax() {
		double Max = this.pointsList.get(0).getX();
		for (Point p : this.pointsList) {
			if(p.getX() > Max) {
				Max = p.getX();
			}
			if (p.getY() > Max) {
				Max = p.getX();
			}
		}
		return Max;
	}
	public double getCentreX() {
		double MaxX;
		double MinX;
		double centreX = 0;
		Point Q = this.pointsList.get(0);
		MaxX = Q.getX();
		MinX = Q.getX();
		for (Point p : this.pointsList) {
			if(p.getX() > MaxX) {
				MaxX = p.getX();
			}else if (p.getX() < MinX) {
				MinX = p.getX();
			}
		}
		centreX = (MaxX - MinX)/2;
		return centreX;
	}

	public double getCentreY() {
		double MaxY;
		double MinY;
		double centreY = 0;
		Point Q = this.pointsList.get(0);
		MaxY = Q.getY();
		MinY = Q.getY();
		for (Point p : this.pointsList) {
			if(p.getY() > MaxY) {
				MaxY = p.getY();
			}else if (p.getY() < MinY) {
				MinY = p.getY();
			}
		}
		centreY = (MaxY - MinY)/2;
		return centreY;
	}

	public void translation(double ratioX, double ratioY) {
		for (Point p : this.pointsList) {
			p.setX(p.getX() + ratioX);
			p.setY(p.getY() + ratioY);
		}
	}

	public void rotateX(Double grad) {
		for (Point p : pointsList) {
			Double y = p.getY();
			Double z = p.getZ();
			p.setY(Math.cos(grad) * y - Math.sin(grad) * z);
			p.setZ(Math.cos(grad) * z + Math.sin(grad) * y);
		}
	}

	public void rotateY(Double grad) {
		for (Point p : pointsList) {
			Double x = p.getX();
			Double z = p.getZ();
			p.setX(Math.cos(grad) * x - Math.sin(grad) * z);
			p.setZ(Math.cos(grad) * z + Math.sin(grad) * x);
		}
	}

	public void rotateZ(Double grad) {
		for (Point p : pointsList) {
			Double x = p.getX();
			Double y = p.getY();
			p.setX(Math.cos(grad) * x - Math.sin(grad) * y);
			p.setY(Math.cos(grad) * y + Math.sin(grad) * x);
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
				facesList.set(j + 1, facesList.get(j));
				j = j - 1;
			}
			facesList.set(j + 1, key);
		}
	}

	public void printAvg() {
		for (Face f : facesList) {
			System.out.println("Moyenne de la face : " + f.average());
		}
	}
}