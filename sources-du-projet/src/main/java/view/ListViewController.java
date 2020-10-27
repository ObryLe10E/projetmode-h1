package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import modele.Face;
import modele.Point;
import modele.Reader;
import modele.Repere;

public class ListViewController {
	@FXML
	ListView<File> list;
	@FXML
	Group affichage;
	@FXML
	Button transMinusY;
	@FXML
	Button transPlusY;
	@FXML
	Button transMinusX;
	@FXML
	Button transPlusX;
	private Repere repere;

	private final double RATIOX = 50;
	private final double RATIOY = 50;

	public void initialize() {
		affichage.setManaged(false);
		Directory dir = new Directory("src/main/resources/fichiers/");
		list.getItems().addAll(dir.getListOfFiles());
		list.refresh();
		list.getSelectionModel().getSelectedItems().addListener(new FileListChangeListener());
		list.setCellFactory(param -> new Cell());
	}

	class FileListChangeListener implements ListChangeListener<File> {
		@Override
		public void onChanged(Change<? extends File> c) {
			// Point.resetID();
			// Face.resetID();
			if (!list.getItems().isEmpty()) {
				try {
					// resetSliders();
					File path = list.getSelectionModel().getSelectedItem().getAbsoluteFile();
					Reader reader = new Reader(path);
					repere = reader.getRepere();
					renderModel();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void renderModel() {
		affichage.getChildren().clear();
		for (Face face : this.repere.getFacesList()) {
			List<Point> list = face.getPoints();
			List<Double> listPoints = new ArrayList<>();
			for (Point p : list) {
				listPoints.add(p.getX());
				listPoints.add(p.getY());
			}
			Polygon polygon = new Polygon();
			polygon.getPoints().addAll(listPoints);
			polygon.setFill(Color.RED);
			polygon.setStrokeWidth(1.0);
			polygon.setStroke(Color.ALICEBLUE);
			affichage.getChildren().add(polygon);
		}
	}

	class Cell extends ListCell<File> {
		HBox hbox = new HBox();
		Button button = new Button("ok");
		Image img = new Image(
				"file:/C:/Users/duhem/Desktop/projetmode-h1/sources-du-projet/src/main/resources/img/icons8-file-25.png");
		ImageView imgv = new ImageView(img);

		public Cell() {
			super();
			hbox.getChildren().addAll(imgv);
			this.setStyle("-fx-background-color: transparent");
		}

		public void updateItem(File item, boolean empty) {
			super.updateItem(item, empty);
			setText(null);
			setGraphic(null);
			if (item != null && !empty) {
				this.setStyle("-fx-background-radius : 20px; -fx-border-color:ffffff; -fx-border-radius : 20px; ");
				try {
					new Reader(item.getAbsoluteFile());
					this.setStyle(this.getStyle() + "; -fx-background-color: green");
				} catch (Exception e) {
					this.setStyle(this.getStyle() + "; -fx-background-color: red");
				}
				setText(item.getName());
				setFont(new Font("Serif", 20.0)); // change la police
				setGraphic(hbox);
				setTextFill(Color.WHITE); // change la couleur du text dans la listview
			}
		}
	}

	public void translationMinusY() {
		this.repere.translation(0, -RATIOY);
		this.renderModel();
	}

	public void translationPlusY() {
		this.repere.translation(0, RATIOY);
		this.renderModel();
	}

	public void translationPlusX() {
		this.repere.translation(RATIOX, 0);
		this.renderModel();
	}

	public void translationMinusX() {
		this.repere.translation(-RATIOX, 0);
		this.renderModel();
	}
}