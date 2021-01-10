package view;

import java.io.File;
import java.util.Comparator;
import java.util.Map;

class ValueComparator implements Comparator<File> {
	Map<File, Integer> base;
	public ValueComparator(Map<File, Integer> base) {
		this.base = base;
	}

	public int compare(File fichier, File other) {
		if (base.get(fichier) >= base.get(other)) return -1;
		else return 1;
	}
}