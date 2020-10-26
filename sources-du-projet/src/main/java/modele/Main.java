package modele;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Reader clebard = new Reader("C:\\Users\\Utilisateur\\Documents\\GitHub\\projetmode-h1\\sources-du-projet\\src\\main\\resources\\fichiers\\cube.ply");
		System.out.println(clebard.getRepere().getPointsMap());
		System.out.println(clebard.getRepere().getFacesList());
	}
}
