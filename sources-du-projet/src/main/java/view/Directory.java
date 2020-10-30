package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.Subject;

public class Directory extends Subject{
	private List<File> listOfFiles = new ArrayList<File>(); 
	private File path;

	public Directory(String f){
		this.path = new File(f);
		for(File fi: this.path.listFiles()) {
			if(!fi.isDirectory()){
				listOfFiles.add(fi);
			}
		}
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public List<File> getListOfFiles() {
		return listOfFiles;
	}
}