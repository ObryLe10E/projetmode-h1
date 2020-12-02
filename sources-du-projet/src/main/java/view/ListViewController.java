package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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
	private Pane center;
	@FXML
	private ListView<File> list;
	@FXML
	private Group affichage;
	@FXML
	private Button transMinusY;
	@FXML
	private Button transPlusY;
	@FXML
	private Button transMinusX;
	@FXML
	private Button transPlusX;
	@FXML
	private Button rotationX;
	@FXML
	private Button rotationY;
	@FXML
	private Button rotationZ;
	@FXML
	private VBox vb;
	@FXML
	private Slider sliderX;
	@FXML
	private Slider sliderY;
	@FXML
	private Slider sliderZ;
	@FXML
	private ColorPicker strokeColor;
	@FXML
	private ColorPicker fillColor;
	@FXML
	private ToggleButton filDeFer;
	@FXML
	private ToggleButton afficherFils;
	@FXML
	private Label nbPointsLabel;
	@FXML
	private Label nbFacesLabel;
	@FXML
	private Label authorLabel;
	@FXML
	private Button help;
	@FXML
	private Button info;

	private Repere repere;

	private static final double RATIOX = 50;
	private static final double RATIOY = 50;
	private static final double SCALING = 1.10;
	private static final double UNSCALING = 0.9;

	/**
	 * Initialise la fenêtre de l'application et ses fonctionnalités, à partir d'une
	 * bibliothèque par défaut
	 */
	public void initialize() {
		affichage.setManaged(false);
		//Directory dir = new Directory("src/main/resources/fichiers/");
		//list.getItems().addAll(dir.getListOfFiles());
		list.refresh();
		list.getSelectionModel().getSelectedItems().addListener(new FileListChangeListener());
		list.setCellFactory(param -> new Cell());
		this.setSliders();
		this.setZoom();
		this.rotate();
		this.setDefaultsColors();
		this.setStrokeButtons();
		help.setOnAction(e->{
			afficherAide();
		});
		info.setOnAction(e->{
			afficherInfo();
		});
	}

	/**
	 * Initialise les boutons permettant de choisir d'afficer ou non les arêtes
	 * et/ou de remplir les faces
	 */
	private void setStrokeButtons() {
		fillColor.setOnAction(e -> {
			this.renderModel();
		});
		strokeColor.setOnAction(e -> {
			this.renderModel();
		});
		filDeFer.setOnAction(e -> {
			this.renderModel();
		});
		filDeFer.setTooltip(new Tooltip("Fil de fer"));
		filDeFer.getTooltip().setShowDelay(new Duration(0));
		afficherFils.setTooltip(new Tooltip("Afficher les fils"));
		afficherFils.getTooltip().setShowDelay(new Duration(0));
	}

	/**
	 * Initialise les couleurs par défaut des arêtes et des faces du modèle
	 */
	private void setDefaultsColors() {
		fillColor.setValue(Color.DARKGRAY);
		strokeColor.setValue(Color.ANTIQUEWHITE);
	}

	/**
	 * Initialise les sliders de rotation du modèle
	 */
	private void setSliders() {
		sliderX.setMax(Math.PI);
		sliderX.setMin(-Math.PI);
		sliderX.setValue(0);
		sliderY.setMax(Math.PI);
		sliderY.setMin(-Math.PI);
		sliderY.setValue(0);
		sliderZ.setMax(Math.PI);
		sliderZ.setMin(-Math.PI);
		sliderZ.setValue(0);
	}

	/**
	 * ChangeListener qui ouvre le fichier sur lequel on clique dans la
	 * bibliothèque, ou une popup d'erreur s'il n'est pas valide
	 */
	class FileListChangeListener implements ListChangeListener<File> {
		@Override
		public void onChanged(Change<? extends File> c) {
			try {
				if (!list.getItems().isEmpty()) {
					try {
						// resetSliders();
						this.resetLabel();
						File path = list.getSelectionModel().getSelectedItem().getAbsoluteFile();
						Reader reader = new Reader(path);
						repere = reader.getRepere();
						if (reader.getAuthor() == null)
							authorLabel.setText(authorLabel.getText() + "INCONNU");
						else
							authorLabel.setText(authorLabel.getText() + reader.getAuthor());
						nbPointsLabel.setText(nbPointsLabel.getText() + reader.getNbPoints());
						nbFacesLabel.setText(nbFacesLabel.getText() + reader.getNbFaces());
						repere.translation(-repere.getCentreX(), -repere.getCentreY());
						// centrage de la figure approximatif
						// repere.scaling((center.getHeight()/10)/repere.getMax()); mise a la bonne
						// taille de la figure
						center.setCursor(Cursor.CROSSHAIR);
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
						stageError.setTitle("ERREUR");
						stageError.show();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		/**
		 * Réinitialise les infos du modèle
		 * 
		 * @nbAuthorLabel Afficher le nom de l'auteur
		 * @nbPointsLabel Afficher le nombre de points du modèle
		 * @nbFacesLabel Afficher le Nombre de faces du modèle
		 */
		private void resetLabel() {
			authorLabel.setText("Auteur :");
			nbPointsLabel.setText("Nombre de points :");
			nbFacesLabel.setText("Nombre de faces :");
		}
	}

	/**
	 * Réinitialise l'affichage du modèle précédent Trie les faces du modèle selon Z
	 * décroissant Crée un Polygon pour chaque face du modèle et l'affiche avec ses
	 * arêtes
	 */
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
			polygon.getPoints().addAll(listPoints); //methode délégué addall (cleancode)
			if (this.filDeFer.isSelected())
				polygon.setFill(Color.TRANSPARENT);
			else
				polygon.setFill(fillColor.getValue());
			if (this.afficherFils.isSelected()) {
				polygon.setStroke(Color.TRANSPARENT);
				polygon.setStrokeWidth(0.0);
			} else {
				polygon.setStroke(strokeColor.getValue());
				polygon.setStrokeWidth(0.5);
			}
			affichage.getChildren().addAll(polygon);
		}
	}

	/**
	 * Cellule de la liste des mod�les
	 */
	class Cell extends ListCell<File> {
		HBox hbox = new HBox();

		public Cell() {
			super();
			this.setStyle("-fx-background-color: transparent");
		}

		/**
		 * Affiche le fichier dans une cellule verte s'il est valide, rouge sinon
		 * 
		 * @param Fichier que la cellule doit tester et afficher
		 * @param empty
		 */
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
				setFont(new Font(20.0));
				setGraphic(hbox);
				setTextFill(Color.WHITE);
			}else {
				this.setStyle("-fx-background-color: transparent");
			}
		}
	}

	/**
	 * Laisse l'utilisateur choisir le dossier de sa bibliothèque de fichiers à
	 * ouvrir
	 */
	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("ply Files", "*.ply"));
		List<File> f = fc.showOpenMultipleDialog(null);
		list.getItems().clear();
		list.getItems().addAll(f);
	}

	/**
	 * Déplace le modèle vers le bas
	 */
	public void translationMinusY() {
		this.repere.translation(0, -RATIOY);
		this.renderModel();
	}

	/**
	 * Déplace le modèle vers le haut
	 */
	public void translationPlusY() {
		this.repere.translation(0, RATIOY);
		this.renderModel();
	}

	/**
	 * Déplace le modèle vers la droite
	 */
	public void translationPlusX() {
		this.repere.translation(RATIOX, 0);
		this.renderModel();
	}

	/**
	 * Déplace le modèle vers la gauche
	 */
	public void translationMinusX() {
		this.repere.translation(-RATIOX, 0);
		this.renderModel();
	}

	/**
	 * Initialise le zoom sur le modèle ave la molette
	 */
	public void setZoom() {
		center.setOnScroll(e -> {
			if (e.getDeltaY() > 0)
				this.repere.scaling(ListViewController.SCALING);
			else
				this.repere.scaling(ListViewController.UNSCALING);
			this.renderModel();
		});
		/*
		 * zoom.valueProperty().addListener((obs,old,n)->{ Double scale = (Double) n -
		 * (Double) old; if(scale < 0) { this.repere.scaling(UNSCALING); } else
		 * this.repere.scaling(SCALING); this.renderModel(); });
		 */
	}

	/**
	 * Initialise la rotation du modèle à partir de sliders ou des touches X, Y et Z
	 */
	public void rotate() {
		vb.setOnKeyReleased(e -> {
		    System.out.println("key");
			if (e.getCode().equals(KeyCode.Z)) 
				this.repere.rotateZ(Math.PI / 8);
			if (e.getCode().equals(KeyCode.Y))
				this.repere.rotateY(Math.PI / 8);
			if (e.getCode().equals(KeyCode.X))
				this.repere.rotateX(Math.PI / 8);
			this.renderModel();
		});
		sliderX.valueProperty().addListener((obs, old, n) -> {
			System.out.println("slider");
			this.repere.rotateX((Double) n - (Double) old);
			this.renderModel();
		});
		sliderY.valueProperty().addListener((obs, old, n) -> {
			this.repere.rotateY((Double) n - (Double) old);
			this.renderModel();
		});
		sliderZ.valueProperty().addListener((obs, old, n) -> {
			this.repere.rotateZ((Double) n - (Double) old);
			this.renderModel();
		});
	}
	private void afficherAide() {
		Stage stageHelp = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("aide.fxml"));
		Pane root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			HelpController controller = loader.<HelpController>getController();
			stageHelp.setScene(scene);
			stageHelp.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void afficherInfo() {
		Stage stageInfo = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("info.fxml"));
		Pane root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			InfoController controller = loader.<InfoController>getController();
			stageInfo.setScene(scene);
			stageInfo.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}