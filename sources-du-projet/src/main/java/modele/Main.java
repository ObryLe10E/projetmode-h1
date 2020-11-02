package modele;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Reader clebard = new Reader("C:\\Users\\duhem\\Desktop\\projetmode-h1\\sources-du-projet\\src\\main\\resources\\fichiers\\cube.ply");
		System.out.println(clebard.getRepere());
		Repere r = clebard.getRepere();
		r.sortFaces();
		r.printAvg();
		//System.out.println(clebard.getRepere().getFacesList());
	}
}
