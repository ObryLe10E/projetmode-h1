package modele;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Reader clebard = new Reader("C:\\Users\\Thomas\\Desktop\\covid-iut\\s3\\projetModel\\sources-du-projet\\src\\main\\resources\\fichiers\\cube.ply");
		System.out.println(clebard.getRepere().getPointsMap());
		System.out.println(clebard.getRepere().getFacesList());
	}
}
