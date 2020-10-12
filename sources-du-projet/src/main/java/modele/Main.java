package modele;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		Reader clebard = new Reader("C:\\Users\\Thomas\\Desktop\\COVID-IUT\\S3\\projetModel\\projetmode-h1\\src\\main\\java\\modele\\cube.ply");
		System.out.println(clebard.getRepere().getPointsMap());
		System.out.println(clebard.getRepere().getFacesMap());
	}
}
