package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import modele.Face;
import modele.Point;
import modele.Reader;
import modele.Repere;

public class ListViewController {
	@FXML
	Pane center;
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
	@FXML
	Button rotationX;
	@FXML
	Button rotationY;
	@FXML
	Button rotationZ;

	private Repere repere;

	private final double RATIOX = 50;
	private final double RATIOY = 50;
	private final double SCALING = 1.10;
	private final double UNSCALING = 0.9;

	public void initialize() {
		affichage.setManaged(false);
		Directory dir = new Directory("src/main/resources/fichiers/");
		list.getItems().addAll(dir.getListOfFiles());
		list.refresh();
		list.getSelectionModel().getSelectedItems().addListener(new FileListChangeListener());
		list.setCellFactory(param -> new Cell());
		this.zoom();
	}

	class FileListChangeListener implements ListChangeListener<File> {
		@Override
		public void onChanged(Change<? extends File> c) {
			try {
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
			} catch (Exception e) {
				if (!list.getItems().isEmpty()) {
					Stage stageError = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("error.fxml"));
					Parent root;
					try {
						root = loader.load();
						Scene scene = new Scene(root, 600, 250);
						ErrorController controller = loader.<ErrorController>getController();
						controller.init(e);
						stageError.initModality(Modality.APPLICATION_MODAL);
						stageError.setScene(scene);
						stageError.setResizable(false);
						stageError.setTitle("New SubTask");
						stageError.show();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public void renderModel() {
		affichage.getChildren().clear();
		this.repere.sortFaces();
		for (Face face : this.repere.getFacesList()) {
			List<Point> list = face.getPoints();
			List<Double> listPoints = new ArrayList<>();
			for (Point p : list) {
				listPoints.add(p.getX());
				listPoints.add(p.getY());
			}
			Polygon polygon = new Polygon();
			polygon.getPoints().addAll(listPoints);
			polygon.setFill(Color.ANTIQUEWHITE);
			polygon.setStrokeWidth(1.0);
			polygon.setStroke(Color.DARKGRAY);
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
				setFont(new Font(20.0)); // change la taille de la police
				setGraphic(hbox);
				setTextFill(Color.WHITE); // change la couleur du text dans la listview
			}
		}
	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("ply Files", "*.ply"));
		List<File> f = fc.showOpenMultipleDialog(null);
		list.getItems().clear();
		list.getItems().addAll(f);
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

	public void zoom() {
		center.setOnScroll(e -> {
			if (e.getDeltaY() > 0)
				this.repere.scaling(this.SCALING);
			else
				this.repere.scaling(this.UNSCALING);
			this.renderModel();
		});
	}

	public void rotateX() {
	}

}