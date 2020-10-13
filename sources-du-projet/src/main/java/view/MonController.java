package view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
public class MonController {
	@FXML ListView <String > list;

	public void initialize() {
		Directory dir = new Directory("src/main/resources/fichiers/");
		list.getItems().addAll(dir.getListOfFiles());
		list.refresh();
	}

}