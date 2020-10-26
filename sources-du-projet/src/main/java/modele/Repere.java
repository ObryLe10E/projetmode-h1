package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repere {
	private Map<Integer, Point> pointsMap;
	private List<Face> facesList;

	public Repere(Map<Integer, Point> pointsMap, List<Face> facesList) {
		this.pointsMap = pointsMap;
		this.facesList = facesList;
	}

	public Repere() {
		this(new HashMap<Integer, Point>(), new ArrayList<Face>());
	}

	public Map<Integer, Point> getPointsMap() {
		return pointsMap;
	}

	public List<Face> getFacesList() {
		return facesList;
	}

	@Override
	public String toString() {
		return "Repere [pointsMap=" + this.pointsMap + ", facesList=" + this.facesList + "]";
	}
	
}