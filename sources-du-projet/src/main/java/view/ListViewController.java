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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
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
	@FXML
	VBox vb;
	@FXML
	Slider sliderX;
	@FXML
	Slider sliderY;
	@FXML
	Slider sliderZ;
	@FXML
	ColorPicker strokeColor;
	@FXML
	ColorPicker fillColor;
	@FXML
	ToggleButton filDeFer;
	@FXML
	ToggleButton afficherFiles;
	@FXML
	Label nbPointsLabel;
	@FXML
	Label nbFacesLabel;
	@FXML
	Label authorLabel;

	private Repere repere;

	private static final double RATIOX = 50;
	private static final double RATIOY = 50;
	private static final double SCALING = 1.10;
	private static final double UNSCALING = 0.9;

	public void initialize() {
		affichage.setManaged(false);
		Directory dir = new Directory("src/main/resources/fichiers/");
		list.getItems().addAll(dir.getListOfFiles());
		list.refresh();
		list.getSelectionModel().getSelectedItems().addListener(new FileListChangeListener());
		list.setCellFactory(param -> new Cell());
		sliderX.setMax(Math.PI);
		sliderX.setMin(-Math.PI);
		sliderX.setValue(0);
		sliderY.setMax(Math.PI);
		sliderY.setMin(-Math.PI);
		sliderY.setValue(0);
		sliderZ.setMax(Math.PI);
		sliderZ.setMin(-Math.PI);
		sliderZ.setValue(0);
		this.setZoom();
		this.rotate();
		fillColor.setValue(Color.DARKGRAY);
		strokeColor.setValue(Color.ANTIQUEWHITE);
		fillColor.setOnAction(e->{
			this.renderModel();
		});
		strokeColor.setOnAction(e->{
			this.renderModel();
		});
		filDeFer.setOnAction(e->{
			this.renderModel();
		});
		filDeFer.setTooltip(new Tooltip("Fil de fer"));
		filDeFer.getTooltip().setShowDelay(new Duration(0));

		afficherFiles.setTooltip(new Tooltip("Afficher les fils"));
		afficherFiles.getTooltip().setShowDelay(new Duration(0));

	}

	class FileListChangeListener implements ListChangeListener<File> {
		@Override
		public void onChanged(Change<? extends File> c) {
			try {
				if (!list.getItems().isEmpty()) {
					try {
						// resetSliders();
						this.resetLabel();
						File path = list.getSelectionModel().getSelectedItem().getAbsoluteFile();
						System.out.println(list.getSelectionModel().getSelectedItem().getAbsoluteFile());
						Reader reader = new Reader(path);
						repere = reader.getRepere();
						if(reader.getAuthor() == null) authorLabel.setText(authorLabel.getText() + "INCONNU");
						else authorLabel.setText(authorLabel.getText() + reader.getAuthor());
							nbPointsLabel.setText(nbPointsLabel.getText() + reader.getNbPoints());
							nbFacesLabel.setText(nbFacesLabel.getText() + reader.getNbFaces());
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
		/** Clean model label fields 
		 * @nbAuthorLabel
		 * @nbPointsLabel
		 * @nbFacesLabel
		 * */
		private void resetLabel() {
			authorLabel.setText("Auteur :");
			nbPointsLabel.setText("Nombre de points :");
			nbFacesLabel.setText("Nombre de faces :");
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
			if(this.filDeFer.isSelected()) {
				polygon.setFill(Color.TRANSPARENT);
			}else 
				polygon.setFill(fillColor.getValue());
			if(this.afficherFiles.isSelected()) {
				polygon.setStroke(Color.TRANSPARENT);
				polygon.setStrokeWidth(0.0);
			}else {
				polygon.setStroke(strokeColor.getValue());
				polygon.setStrokeWidth(0.5);
			}
			affichage.getChildren().addAll(polygon);

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

	public void setZoom() {
		center.setOnScroll(e -> {
			if (e.getDeltaY() > 0)
				this.repere.scaling(ListViewController.SCALING);
			else
				this.repere.scaling(ListViewController.UNSCALING);
			this.renderModel();
		});
		/*zoom.valueProperty().addListener((obs,old,n)->{
			Double scale = (Double) n - (Double) old; 
			if(scale < 0) {
				this.repere.scaling(UNSCALING);
			}
			else this.repere.scaling(SCALING);
			this.renderModel();
		});*/
	}

	public void rotate() {
		vb.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.Z))
				this.repere.rotateZ(Math.PI/8);
			if (e.getCode().equals(KeyCode.Y))
				this.repere.rotateY(Math.PI/8);
			if (e.getCode().equals(KeyCode.X))
				this.repere.rotateX(Math.PI/8);
			this.renderModel();
		});
		sliderX.valueProperty().addListener((obs,old,n)->{
			this.repere.rotateX((Double) n - (Double) old);
			this.renderModel();
		});		
		sliderY.valueProperty().addListener((obs,old,n)->{
			this.repere.rotateY((Double) n - (Double) old);
			this.renderModel();
		});
		sliderZ.valueProperty().addListener((obs,old,n)->{
			this.repere.rotateZ((Double) n - (Double) old);
			this.renderModel();
		});
	}
}