package view;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.WrongFileFormatException;
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
	private ToggleButton lightButon;
	@FXML
	private ToggleButton ombreButon;
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
	@FXML
	private Button duplicate;

	private Repere repere;

	@FXML
	private Slider sliderLight;
	private GraphicsContext gc;
	@FXML
	private ToggleButton nameButton;
	@FXML
	private ToggleButton faceButton;
	@FXML
	private ToggleButton pointsButton;


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
		Directory dir = new Directory("src/main/resources/fichiers/");
		list.getItems().addAll(dir.getListOfFiles());
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
		gc = affichage2.getGraphicsContext2D();
		sliderLight.setMax(Math.PI/2);
		sliderLight.setMin(-Math.PI/2);
		sliderLight.valueProperty().addListener((obs,old,n)->{
			renderOmbrage(gc, sliderLight.getValue());
			renderModel();
		});
		this.faceButton.setOnAction(e->{
			if(this.faceButton.isSelected()) {
				this.nameButton.setSelected(false);
				this.pointsButton.setSelected(false);
				this.triFaceList();
			}else {
				Collections.reverse(this.list.getItems());
			}
		});
		this.nameButton.setOnAction(e->{
			if(this.nameButton.isSelected()) {
				this.faceButton.setSelected(false);
				this.pointsButton.setSelected(false);
				this.triName();
			}else
				Collections.reverse(this.list.getItems());
		});
		this.pointsButton.setOnAction(e->{
			if(this.pointsButton.isSelected()) {
				this.faceButton.setSelected(false);
				this.nameButton.setSelected(false);
				this.triPointsList();
			}else {
				Collections.reverse(this.list.getItems());
			}
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
						while (repere.getMax() < affichage2.getWidth()-50)
							repere.scaling(SCALING);
						while (repere.getMax() > affichage2.getWidth()-affichage2.getWidth()/4)
							repere.scaling(UNSCALING);
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
	private double tmpZ = 0.0;
	private void mouseTranslate() {
		this.affichage2.setOnMousePressed(e->{
			tmpX = e.getX();
			tmpY = e.getY();
			tmpZ = e.getZ();
			drag = true;
		});
		this.affichage.setOnMouseReleased(e->{
			drag = false;
		});
		this.affichage2.setOnMouseDragged(e->{
			if(drag) {
				if(e.isPrimaryButtonDown()) {
					repere.translation(e.getX()-tmpX, e.getY()-tmpY);
					this.renderModel();
					tmpX=e.getX();
					tmpY=e.getY();
				}else {
					repere.rotateY((e.getX()-tmpX)/100);
					repere.rotateX((e.getY()-tmpY)/100);
					repere.rotateZ((e.getZ()-tmpZ)/100);
					this.renderModel();
					tmpX = e.getX();
					tmpY = e.getY();
					tmpZ = e.getZ();
				}
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
		if(!this.ombreButon.isSelected()) {
			this.renderOmbrage(gc,this.sliderLight.getValue());
		}

		if (this.filDeFer.isSelected())
			gc.setFill(Color.TRANSPARENT);
		else 
			gc.setFill(fillColor.getValue());
		if (this.afficherFils.isSelected())
			gc.setStroke(Color.TRANSPARENT);
		else
			gc.setStroke(strokeColor.getValue());
		Color c = fillColor.getValue();
		for (Face face : this.repere.getFacesList()) {
			int size = face.size();
			double[] xPoints = new double[size];
			double[] yPoints = new double[size];
			for (int i = 0; i < face.size() ; i++) {
				xPoints[i] = face.get(i).getX();
				yPoints[i] = face.get(i).getY();
			}
			if(!this.lightButon.isSelected()) {
				double coefLight = sliderLight.getValue();
				if(face.getColor(1, coefLight) <= 0) {
					gc.setFill(Color.BLACK);
				}else
					gc.setFill(Color.rgb(face.getColor(c.getRed(), coefLight), face.getColor(c.getGreen(),coefLight), face.getColor(c.getBlue(),coefLight)));
			}
			if(!this.afficherFils.isSelected())
				affichage2.getGraphicsContext2D().strokePolygon(xPoints, yPoints, size);
			if(!this.filDeFer.isSelected())
				affichage2.getGraphicsContext2D().fillPolygon(xPoints, yPoints, size);
			//}
		}
	}

	public void renderOmbrage(GraphicsContext gc, double grad) {
		Vecteur light = new Vecteur(0, 0, 1.25);
		light.rotationY(grad);
		for (Face face : this.repere.getFacesList()) {
			int size = face.size();
			double ombreX [] = new double [face.getPoints().size()];
			double ombreY [] = new double [face.getPoints().size()];
			for (int i = 0; i < size ; i++) {
				Point point = face.get(i);
				ombreX[i] = (point.getX()  / light.normeVectoriel());
				ombreY[i]= (point.getY() / light.normeVectoriel());
			}
			if(!filDeFer.isSelected()) {
				gc.setFill(Color.rgb(100, 100, 100));
				affichage2.getGraphicsContext2D().fillPolygon(ombreX, ombreY, size);
			}
			if(!afficherFils.isSelected() & filDeFer.isSelected()) {
				gc.setFill(Color.rgb(100,100,100));
				affichage2.getGraphicsContext2D().strokePolygon(ombreX, ombreY, size);
				affichage2.getGraphicsContext2D().setStroke(Color.rgb(100,100,100));
			}

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
	}

	/**
	 * Initialise la rotation du modèle à partir de sliders ou des touches X, Y et Z
	 */
	public void rotate() {
		vb.setOnKeyPressed(e-> {
			if (e.getCode().equals(KeyCode.Z)) 
				this.repere.rotateZ(Math.PI / 8);
			if (e.getCode().equals(KeyCode.Y))
				this.repere.rotateY(Math.PI / 8);
			if (e.getCode().equals(KeyCode.X))
				this.repere.rotateX((Math.PI / 8));
			this.renderModel();
		});
		sliderX.valueProperty().addListener((obs, old, n) -> {
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



	private void triFaceList() {
		HashMap<File, Integer> map = new HashMap<File, Integer>();
		for(File f : this.list.getItems()) {
			System.out.println(f);
			Reader r = null;
			try {
				r = new Reader(f);
				map.put(f, r.getNbFaces());
			} catch (IOException e) {
				map.put(f, 0);
				e.printStackTrace();
			} catch (WrongFileFormatException e) {
				map.put(f, 0);
				e.printStackTrace();
			}
		}
		System.out.println("liste tri�e :" + triSurValeur(map));
		TreeMap<File, Integer> map2 =triSurValeur(map);
		this.list.getItems().clear();
		this.list.getItems().addAll(map2.keySet());

	}

	private TreeMap<File,Integer> triSurValeur(HashMap<File, Integer> map) {

		ValueComparator comparateur = new ValueComparator(map);
		TreeMap<File,Integer> mapTriee = new TreeMap<File,Integer>(comparateur);
		mapTriee.putAll(map);
		return mapTriee;
	}
	class ValueComparator implements Comparator<File> {
		Map<File, Integer> base;
		public ValueComparator(Map<File, Integer> base) {
			this.base = base;
		}

		public int compare(File a, File b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			} else {
				return 1;
			}
		}
	}
	private void triName() {
		Object [] tri = this.list.getItems().toArray();
		Arrays.sort(tri);
		this.list.getItems().clear();
		for(Object o : tri) {
			this.list.getItems().add((File) o );
		}
	}
	private void triPointsList() {

		HashMap<File, Integer> map = new HashMap<File, Integer>();
		for(File f : this.list.getItems()) {
			System.out.println(f);
			Reader r = null;
			try {
				r = new Reader(f);
				map.put(f, r.getNbPoints());
			} catch (IOException e) {
				map.put(f, 0);
				e.printStackTrace();
			} catch (WrongFileFormatException e) {
				map.put(f, 0);
				e.printStackTrace();
			}
		}
		System.out.println("liste tri�e :" + triSurValeur(map));
		TreeMap<File, Integer> map2 =triSurValeur(map);
		this.list.getItems().clear();
		this.list.getItems().addAll(map2.keySet());

	}
	private void resetPosition() {

	}
	private void dupliquer() {

	}
}