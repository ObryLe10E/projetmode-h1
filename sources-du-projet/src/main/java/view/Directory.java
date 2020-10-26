package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.Subject;

public class Directory extends Subject{
	private List<String> listOfFiles = new ArrayList<String>(); 
	private File path;

	public Directory(String f){
		this.path = new File(f);
		for(String s : this.path.list()) {
			File file = new File(s);
			if(!file.isDirectory()){
				listOfFiles.add(s);
			}
		}
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public List<String> getListOfFiles() {
		return listOfFiles;
	}
}