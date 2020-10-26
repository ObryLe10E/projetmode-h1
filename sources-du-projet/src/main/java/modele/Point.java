package modele;

public class Point {
	private static int pointTag = 0;
	private final int ID;

	private double x;
	private double y;
	private double z;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.ID = pointTag;
		pointTag++;
	}

	public Point() {
		this(0, 0, 0);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public int getID() {
		return this.ID;
	}

	public String toString() {
		return this.getID() + "(" + x + "," + y + "," + z + ")JE CO MPILE C BVOB";
	}

}
