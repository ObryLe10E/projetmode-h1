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
	/**
	 * Un entier correspondant a la présence de couleur <br>0 si non <br>3 si oui
	 *
	 */
	private int colorful = 0;
	/** The author of the PLY file, searched with a "by" prefix in header */
	private String author;
	private static final String FORMAT = "ply";
	private static final String AUTHOR = "by";
	private static final String ELEMENT = "element";
	private static final String VERTEX = "vertex";
	private static final String FACE = "face";
	private static final String COLOR = "blue";

	/**
	 * Try to read a PLY file,
	 * 
	 * @param fichier : A regular file
	 * @throws IOException              if the bufferedReader {@link #reader}
	 *                                  encounters a problem to read a line
	 * @throws NullPointerException     if the specified file does not exist
	 * @throws WrongFileFormatException if the specified file isn't a .ply file
	 */
	public Reader(File fichier) throws IOException, WrongFileFormatException {
		if (fichier == null)
			throw new NullPointerException("Fichier inexistant");
		this.repere = new Repere();
		try {
			reader = new BufferedReader(new FileReader(fichier));
			String line = reader.readLine();
			if (line == null)
				throw new NullPointerException("Fichier vide");
			if (!line.equals(FORMAT)) {
				throw new WrongFileFormatException("Mauvais format de fichier : non \"ply\"");
			}
			this.propertySearch();
		} catch (FileNotFoundException e) {
			throw new NullPointerException("Fichier introuvable");
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
	/**
	 * 
	 * @return le nombre de points dans le fichier
	 */
	public int getNbPoints() {
		return nbPoints;
	}
	/**
	 *
	 * @return le nombre de faces dans le fichier
	 */
	public int getNbFaces() {
		return nbFaces;
	}
	/**
	 * 
	 * @return l'auteur du fichier
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 
	 * @return le repere du fichier
	 */
	public Repere getRepere() {
		return this.repere;
	}
	
	/**
	 * 
	 * @return true si le fichier .ply est coloré, false sinon.
	 */
	public boolean isColorful() {
		return this.colorful == 3;
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
		String end = "end_header";
		for (String line = reader.readLine(); !line.equals(end); line = reader.readLine()) {
			if (line.contains(AUTHOR)) {
				String subLine = line.substring(line.indexOf(AUTHOR));
				String[] authorTab = subLine.split(" ");
				this.author = authorTab[1];
			}
			if (line.startsWith(ELEMENT)) {
				String[] splitted = line.split(" ");
				if (line.contains(VERTEX))
					this.nbPoints = Integer.parseInt(splitted[splitted.length - 1]);
				if (line.contains(FACE))
					this.nbFaces = Integer.parseInt(splitted[splitted.length - 1]);
			}
			if(line.contains(COLOR))
				this.colorful = 3;
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
			if (splitted.length < Face.MINIMAL_SIZE)
				throw new WrongFileFormatException("Mauvais format de point donné | Point n°" + (i + 1));
			Point point;
			try {
				point = new Point(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]),
						Double.parseDouble(splitted[2]));
			} catch (Exception e) {
				throw new WrongFileFormatException("Mauvais format de point donné | Point n°" + (i + 1));
			}
			this.repere.getPointsList().add(point);
		}
		for (int i = 0; i < this.nbFaces; i++) {
			String line = reader.readLine();
			String[] splitted = line.split(" ");
			int nbVertex = Integer.parseInt(splitted[0]);
			if (splitted.length - 1 == nbVertex + colorful) {
				List<Point> plist = new ArrayList<>();
				for (int j = 0; j < nbVertex; j++)
					try {
						plist.add(this.repere.getPointsList().get(Integer.parseInt(splitted[j + 1])));
					} catch (Exception e) {
						throw new WrongFileFormatException("Mauvais format de face donné | Face n°" + (i + 1) + ", Point n°" + (j + 1));
					}
				Face currentFace = new Face(plist);
				List<Integer> rgb = new ArrayList<>();
				for(int j = nbVertex;j < nbVertex + colorful; j++) {
					rgb.add(Integer.parseInt(splitted[j+1]));
				}
				currentFace.setRGB(rgb);
				this.repere.getFacesList().add(currentFace);
			} else
				throw new WrongFileFormatException("Mauvais format de face donné | Face n°" + (i + 1));
		}
	}
}