package modele;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PointMap
{
	private Map<Integer,Point> ensemblePoints;
	
	public PointMap() 
	{
		this.ensemblePoints = new HashMap<Integer, Point>();
	}

	public Map<Integer, Point> getEnsemblePoints()
	{
		return ensemblePoints;
	}
	
	public boolean add(Point p) 
	{
		if(this.ensemblePoints.containsKey(p.getID())) return false;
		else
		{
			this.ensemblePoints.put(p.getID(),p);
			return true;
		}
	}
	
	public boolean addAll(Collection<Point> c)
	{
		boolean ans = false;
		for (Point p : c) ans = this.add(p);
		return ans;
	}
	
	public String toString() 
	{
		StringBuilder sb = new StringBuilder("Points :\n"); 
		for (Map.Entry<Integer,Point> entry : ensemblePoints.entrySet()) 
		{
			sb.append(entry.getValue().getID()+"\n");
		}
		return sb.toString();
	}
}