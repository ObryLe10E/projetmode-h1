package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {
	private BufferedReader reader;

	private Repere repere;

	private int nbPoints = -1;
	private int nbFaces = -1;

	public Reader(File f) throws IOException {
		if (f == null)
			throw new NullPointerException("Fichier inexistant");
		this.repere = new Repere();
		try {
			reader = new BufferedReader(new FileReader(f));
			if (!reader.readLine().equals("ply")) {

				throw new IllegalArgumentException("Mauvais format de fichier : not \"ply\"");
			}
			this.propertySearch();
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
		}
	}

	public Reader(String path) throws IOException {
		this(new File(path));
	}

	public int getNbPoints() {
		return nbPoints;
	}

	public int getNbFaces() {
		return nbFaces;
	}

	public Repere getRepere() {
		return this.repere;
	}

	public boolean headerCheck() throws IOException {
		for (String line = reader.readLine(); !line.equals("end_header"); line = reader.readLine()) {
			if (line.startsWith("element")) {
				String[] splitted = line.split(" ");
				if (line.contains("vertex")) {
					this.nbPoints = Integer.parseInt(splitted[splitted.length - 1]);
				}
				if (line.contains("face")) {
					this.nbFaces = Integer.parseInt(splitted[splitted.length - 1]);
				}
			}

		}
		return this.nbFaces > 0 && this.nbPoints > 0;
	}

	public void propertySearch() throws IOException {
		if (!this.headerCheck())
			return;

		for (int i = 0; i < this.nbPoints; i++) {
			String line = reader.readLine();
			String[] splitted = line.split(" ");
			// check si autant de points que de "property" dans header
			Point p = new Point(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]),
					Integer.parseInt(splitted[2]));
			this.repere.getPointsMap().put(p.getID(), p);
		}

		for (int i = 0; i < this.nbFaces; i++) {
			String line = reader.readLine();
			String[] splitted = line.split(" ");
			// check si autant de points que de "property" dans header
			int nbVertex = Integer.parseInt(splitted[0]);
			if (nbVertex == Face.TAILLE && splitted.length - 1 == nbVertex) {
				List<Point> plist = new ArrayList<Point>();
				for (int j = 0; j < nbVertex; j++)
					plist.add(this.repere.getPointsMap().get(Integer.parseInt(splitted[j + 1])));
				this.repere.getFacesList().add(new Face(plist));
			}
		}
	}
}