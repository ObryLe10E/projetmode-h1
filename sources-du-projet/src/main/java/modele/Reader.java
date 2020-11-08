package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import exceptions.WrongFileFormatException;

/**
 * @author H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Reader {
	/** A BufferedReader to run through lines of the file */
	private BufferedReader reader;
	/**
	 * That's where faces and vertice are stored
	 * 
	 * @see Repere
	 */
	private Repere repere;
	/**
	 * Number of points of the PLY file, searched with "contains vertex" prefix in
	 * header
	 */
	private int nbPoints = 0;
	/**
	 * Number of faces of the PLY file, searched with "contains face" prefix in
	 * header
	 */
	private int nbFaces = 0;
	/** The author of the PLY file, searched with a "by" prefix in header */
	private String author;

	/**
	 * Try to read a PLY file,
	 * 
	 * @param f : A regular file
	 * @throws IOException              if the bufferedReader {@link #reader}
	 *                                  encounters a problem to read a line
	 * @throws NullPointerException     if the specified file does not exist
	 * @throws WrongFileFormatException if the specified file isn't a .ply file
	 */
	public Reader(File f) throws IOException, WrongFileFormatException {
		if (f == null)
			throw new NullPointerException("Fichier inexistant");
		this.repere = new Repere();
		try {
			reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			if (line == null)
				throw new NullPointerException("Fichier vide");
			if (!line.equals("ply")) {
				throw new WrongFileFormatException("Mauvais format de fichier : non \"ply\"");
			}
			this.propertySearch();
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
		}
	}

	/**
	 * 
	 * Takes a {@link String} <b>path</b> and invoke {@link #Reader(File)} creating
	 * a new {@link File} from the specified <b>path</b>
	 */
	public Reader(String path) throws IOException, WrongFileFormatException {
		this(new File(path));
	}

	public int getNbPoints() {
		return nbPoints;
	}

	public int getNbFaces() {
		return nbFaces;
	}

	public String getAuthor() {
		return author;
	}

	public Repere getRepere() {
		return this.repere;
	}

	/**
	 * Prerequisite for propertySearch function. Reads the header of the associate
	 * PLY file and try to get : {@link #author} {@link #nbPoints} {@link #nbFaces}
	 * 
	 * @see #propertySearch()
	 * @return <b>true</b> If the header of the ply file contains a correct number
	 *         of :
	 *         <li>{@link #nbPoints} (vertex)</li>
	 *         <li>{@link #nbFaces} (face)</li> else returns <b>false</b>
	 * @throws IOException
	 */
	public boolean headerCheck() throws IOException {
		for (String line = reader.readLine(); !line.equals("end_header"); line = reader.readLine()) {

			if (line.contains("by")) {
				String[] authorTab = line.substring(line.indexOf("by")).split(" ");
				this.author = authorTab[1];
			}
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

	/**
	 * Used in the {@link Reader} constructor Reads and store all vertex and faces
	 * of the PLY file inside {@link #repere}
	 * 
	 * @return <b>void</b> If the {@link #headerCheck()} function returns
	 *         <b>false</b>
	 * @see Repere
	 * @throws IOException
	 * @throws WrongFileFormatException
	 */
	public void propertySearch() throws IOException, WrongFileFormatException {
		if (!this.headerCheck())
			return;

		for (int i = 0; i < this.nbPoints; i++) {
			String line = reader.readLine();
			String[] splitted = line.split(" ");
			if (splitted.length < 3)
				throw new WrongFileFormatException("Mauvais format de point donné | Point n°" + (i + 1));
			Point p;
			try {
				p = new Point(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]),
						Double.parseDouble(splitted[2]));
			} catch (Exception e) {
				throw new WrongFileFormatException("Mauvais format de point donné | Point n°" + (i + 1));
			}
			this.repere.getPointsList().add(p);
		}

		for (int i = 0; i < this.nbFaces; i++) {
			String line = reader.readLine();
			String[] splitted = line.split(" ");
			int nbVertex = Integer.parseInt(splitted[0]);
			if (splitted.length - 1 == nbVertex) {
				List<Point> plist = new ArrayList<Point>();
				for (int j = 0; j < nbVertex; j++)
					try {
						plist.add(this.repere.getPointsList().get(Integer.parseInt(splitted[j + 1])));
					} catch (Exception e) {
						throw new WrongFileFormatException(
								"Mauvais format de face donné | Face n°" + (i + 1) + ", Point n°" + (j + 1));
					}
				this.repere.getFacesList().add(new Face(plist));
			} else
				throw new WrongFileFormatException("Mauvais format de face donné | Face n°" + (i + 1));
		}
	}
}