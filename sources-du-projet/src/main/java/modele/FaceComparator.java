package modele;

import java.util.Comparator;

public class FaceComparator implements Comparator<Face>{

	@Override
	public int compare(Face arg0, Face arg1) {
		return Double.compare(arg0.average(), arg1.average());
	}

}
