package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.Subject;

/**
 * @author H1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class Directory extends Subject {
	private List<File> listOfFiles = new ArrayList<>();
	private File path;

	/**
	 * Constructeur d'un Directory à partir d'un chemin
	 * 
	 * @param path Chemin à partir duquel créer le Directory
	 */
	public Directory(String path) {
		this.path = new File(path);
		for (File fi : this.path.listFiles()) {
			if (!fi.isDirectory()) {
				listOfFiles.add(fi);
			}
		}
	}

	/**
	 * @return Chemin du Directory
	 */
	public File getPath() {
		return path;
	}

	/**
	 * @return Liste des fichiers contenus dans le Directory
	 */
	public List<File> getListOfFiles() {
		return listOfFiles;
	}
}