package modele;

public class Repere
{
	private PointMap pointsMap;
	private FaceMap facesMap;
	
	public Repere(PointMap pointsMap, FaceMap facesMap)
	{
		this.pointsMap = pointsMap;
		this.facesMap = facesMap;
	}
	
	public Repere()
	{
		this(new PointMap(), new FaceMap());
	}

	public PointMap getPointsMap()
	{
		return pointsMap;
	}

	public FaceMap getFacesMap()
	{
		return facesMap;
	}
	
	
}
