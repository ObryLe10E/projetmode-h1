package modele;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FaceMap
{
	private Map<Integer,Face> ensembleFaces;
	
	public FaceMap()
	{
		this.ensembleFaces = new HashMap<Integer, Face>();
	}

	public Map<Integer, Face> getEnsembleFaces()
	{
		return ensembleFaces;
	}
	
	public boolean add(Face f) 
	{
		if(this.ensembleFaces.containsKey(f.getID())) return false;
		else
		{
			this.ensembleFaces.put(f.getID(),f);
			return true;
		}
	}
	
	public boolean addAll(Collection<Face> c)
	{
		boolean ans = false;
		for (Face f : c) ans = this.add(f);
		return ans;
	}
	
	public String toString() 
	{
		StringBuilder sb = new StringBuilder("Faces :\n"); 
		for (Map.Entry<Integer,Face> entry : ensembleFaces.entrySet()) 
		{
			sb.append(entry.getValue().toString()+"\n");
		}
		return sb.toString();
	}
}
