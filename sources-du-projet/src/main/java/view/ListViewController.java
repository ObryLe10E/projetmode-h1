package view;

import java.io.IOException;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import modele.Reader;
import modele.Repere;
public class ListViewController {
	@FXML ListView <String > list;
	private Repere repere;

	public void initialize() {
		Directory dir = new Directory("src/main/resources/fichiers/");
		list.getItems().addAll(dir.getListOfFiles());
		list.refresh();
		list.getSelectionModel().getSelectedItems().addListener(new FileListChangeListener());
	}
	
    class FileListChangeListener implements ListChangeListener<String> {
    	@Override
        public void onChanged(Change<? extends String> c) {
            if (!list.getItems().isEmpty()) {
                try {
                    //resetSliders();
                    repere = new Reader(list.getSelectionModel().getSelectedItem()).getRepere();
                    //renderModel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

}