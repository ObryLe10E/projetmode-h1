package modele;

import java.util.ArrayList;
import java.util.List;

public class Repere
{
	private Map<Integer,Point> pointsMap;
	//private PointMap pointsMap;
	private List<Face> facesList;
	
	public Repere(Map<Integer,Point> pointsMap, List<Face> facesList)
	{
		this.pointsMap = pointsMap;
		this.facesList = facesList;
	}
	
	public Repere()
	{
		this(new PointMap(), new ArrayList<Face>());
	}

	public Map<Integer,Point> getPointsMap()
	{
		return pointsMap;
	}

	public List<Face> getFacesList()
	{
		return facesList;
	}
}