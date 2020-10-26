package view;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import modele.Reader;
import modele.Repere;

public class ListViewController {
	
	
	
	
	class CellFact extends ListCell<File> implements Serializable, Callback<ListView<File>, ListCell<File>>{

		@Override
		public void updateItem(File item, boolean b) {
			
			super.updateItem(item, b);
			if (item != null && !b) {
				Canvas c = new Canvas(400, 20);
				GraphicsContext gc = c.getGraphicsContext2D();
				gc.fillText(item.getName(), 25, 16);
				setGraphic(c);
			}else {
				setGraphic(null);
			}
		}

		@Override
		public ListCell<File> call(ListView<File> param) {
			// TODO Auto-generated method stub
			return new CellFact();
		}
	}
	
	
	
	
	
	
	@FXML
	ListView<File> list;
	@SuppressWarnings("unused")
	private Repere repere;

	public void initialize() {
		Directory dir = new Directory("src/main/resources/fichiers/");
		list.getItems().addAll(dir.getListOfFiles());
		list.refresh();
		list.getSelectionModel().getSelectedItems().addListener(new FileListChangeListener());
		list.setCellFactory(new CellFact());

	}
	class FileListChangeListener implements ListChangeListener<File> {
		@Override
		public void onChanged(Change<? extends File> c) {
			if (!list.getItems().isEmpty()) {
				try {
					// resetSliders();
					File path = list.getSelectionModel().getSelectedItem().getAbsoluteFile();
					Reader reader = new Reader(path);
					repere = reader.getRepere();
					System.out.println(repere);
					// renderModel();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void renderModel() {

	}
}