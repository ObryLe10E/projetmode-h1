package view;


import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MonController {
	@FXML ListView <String > list;

	public void initialize() {
		Directory dir = null;
		try{
			dir = new Directory("C:\\Users\\duhem\\Desktop\\projetmode-h1\\sources-du-projet\\src\\main\\resources\\fichiers");
			list = new ListView<String>();
			list.getItems().addAll(dir.getListOfFiles());
			list.notify();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	public void afficher() {
		list.getItems().add("coucou");
		System.out.println(list.getItems());
	}

}
