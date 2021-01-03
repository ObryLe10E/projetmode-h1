package view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.Face;
import modele.Point;
import modele.Reader;
import modele.Repere;
import modele.Vecteur;

public class ListViewController {
	@FXML
	private Pane center;
	@FXML
	private ListView<File> list;
	@FXML
	private Group affichage;
	@FXML
	private Canvas affichage2;
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
	@FXML
	private Button posInit;

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
		affichage2.setManaged(false);
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
		this.mouseTranslate();
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
						resetSliders();
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
						center.setCursor(Cursor.CROSSHAIR);
						repere.center();				
						repere.translation(affichage2.getWidth()/2, affichage2.getHeight()/2); // centrage de la figure approximatif
						while (repere.getMax() < affichage2.getWidth()-50) {
							repere.scaling(SCALING);
						}
						while (repere.getMax() > affichage2.getWidth()-affichage2.getWidth()/4) {
							repere.scaling(UNSCALING);
						}
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
	
	private boolean drag = false;
	private double tmpX = 0.0;
	private double tmpY = 0.0;
	private void mouseTranslate() {
		this.affichage2.setOnMousePressed(e->{
			tmpX = e.getX();
			tmpY = e.getY();
			drag = true;
		});
		this.affichage.setOnMouseReleased(e->{
			drag = false;
		});
		this.affichage2.setOnMouseDragged(e->{
			if(drag) {
				repere.translation(e.getX()-tmpX, e.getY()-tmpY);
				this.renderModel();
				tmpX=e.getX();
				tmpY=e.getY();
			}
		});
	}

	/**
	 * Réinitialise l'affichage du modèle précédent Trie les faces du modèle selon Z
	 * décroissant Crée un Polygon pour chaque face du modèle et l'affiche avec ses
	 * arêtes
	 */
	public void renderModel() {
		this.repere.sortFaces();
		redraw();
		GraphicsContext gc = affichage2.getGraphicsContext2D();
		if (this.filDeFer.isSelected())
			gc.setFill(Color.TRANSPARENT);
		else
			gc.setFill(fillColor.getValue());

		if (this.afficherFils.isSelected()) {
			gc.setStroke(Color.TRANSPARENT);
			//gc.setStrokeWidth(0.0);
		} else {
			gc.setStroke(strokeColor.getValue());
			//gc.setStrokeWidth(0.5);
		}
		this.renderOmbrage(gc);
		for (Face face : this.repere.getFacesList()) {
			if(face.getColor(1) >= 0) { 
				double xPoints[] = new double[] { face.getPoints().get(0).getX(), face.getPoints().get(1).getX(), face.getPoints().get(2).getX()};
				double yPoints[] = new double[] { face.getPoints().get(0).getY(), face.getPoints().get(1).getY(), face.getPoints().get(2).getY()};
				gc.setFill(Color.rgb(face.getColor(c.getRed()), face.getColor(c.getGreen()), face.getColor(c.getBlue())));
				affichage2.getGraphicsContext2D().strokePolygon(xPoints, yPoints, 3);
				affichage2.getGraphicsContext2D().fillPolygon(xPoints, yPoints, 3);
			}
	}
}

	public void renderOmbrage(GraphicsContext gc) {
		final Vecteur leftLight = new Vecteur(0, 0, 1.25);
		for (Face face : this.repere.getFacesList()) {
			double OmbreX [] = new double [face.getPoints().size()];
			double OmbreY [] = new double [face.getPoints().size()];
			for (int i = 0; i < face.getPoints().size() ; i++) {

				Point point = face.getPoints().get(i);
				OmbreX [i] = (int) (point.getX()  / leftLight.normeVectoriel());
				OmbreY [i]= (int) (point.getY() / leftLight.normeVectoriel());
			}
			gc.setFill(Color.rgb(100, 100, 100));
			gc.setStroke(Color.rgb(100, 100, 100));
			affichage2.getGraphicsContext2D().strokePolygon(OmbreX, OmbreY, 3);
			affichage2.getGraphicsContext2D().fillPolygon(OmbreX, OmbreY, 3);


		}
	}
	public void redraw() {
		affichage2.getGraphicsContext2D().clearRect(0, 0, affichage2.getWidth(), affichage2.getHeight());
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
	 * ajuste le model a la taille de la fenetre
	 */
	public void ajuster() {

	}


	/**
	 * remettre les sliders a 180 (au milieu)
	 */
	public void resetSliders() {
		sliderX.setValue(0);
		sliderY.setValue(0);
		sliderZ.setValue(0);
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

		vb.setOnKeyPressed(e-> {
			System.out.println("key");
			if (e.getCode().equals(KeyCode.Z)) 
				this.repere.rotateZ(Math.PI / 8);
			if (e.getCode().equals(KeyCode.Y))
				this.repere.rotateY(Math.PI / 8);
			if (e.getCode().equals(KeyCode.X))
				this.repere.rotateX((Math.PI / 8));
			this.renderModel();
		});
		sliderX.valueProperty().addListener((obs, old, n) -> {
			System.out.println("slider");
			this.repere.rotateX(((Double) n - (Double) old));
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

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
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