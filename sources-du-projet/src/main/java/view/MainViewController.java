package view;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import exceptions.WrongFileFormatException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Reader;
import utils.Subject;

public class MainViewController extends ViewController{
	@FXML
	protected Pane center;
	@FXML
	protected ListView<File> list;	
	@FXML
	private Button transMinusY;
	@FXML
	private Button transPlusY;
	@FXML
	private Button transMinusX;
	@FXML
	private Button transPlusX;
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
	protected Label nbPointsLabel;
	@FXML
	protected Label nbFacesLabel;
	@FXML
	protected Label authorLabel;
	@FXML
	private Button help;
	@FXML
	private Button info;
	@FXML
	private Button posInit;
	@FXML
	private Button duplicate;
	@FXML
	private Slider sliderLight;
	@FXML
	private ToggleButton nameButton;
	@FXML
	private ToggleButton faceButton;
	@FXML
	private ToggleButton pointsButton;
	protected static final double RATIOX = 50;
	protected static final double RATIOY = 50;
	protected static final double SCALING = 1.10;
	protected static final double UNSCALING = 0.9;
	private boolean drag = false;
	private double tmpX = 0.0;
	private double tmpY = 0.0;
	private double tmpZ = 0.0;

	/**
	 * Initialise la fenêtre de l'application et ses fonctionnalités, à partir d'une
	 * bibliothèque par défaut
	 */
	public void initialize() {
		affichage.setManaged(false);
		Directory dir = new Directory("src/main/resources/fichiers/");
		list.getItems().addAll(dir.getListOfFiles());
		list.refresh();
		list.getSelectionModel().getSelectedItems().addListener(new FileListChangeListener(this));
		list.setCellFactory(param -> new Cell());
		gc = affichage.getGraphicsContext2D();	
		this.setComponents();
	}
	
	/**
	 * Initialise tous les éléments de la fenêtre
	 */
	@Override
	protected void setComponents() {
		super.setComponents();
		this.setSliders();
		this.setZoom();
		this.setRotation();
		this.setDefaultsColors();
		this.setStrokeButtons();
		this.setInfos();
		this.setDuplicate();
		this.setLightSliders();
		this.setMouseTranslate();
		this.setSortButtons();
	}
	
	/**
	 * Initialise les boutons permettant de trier la liste des fichiers de la bibliothèque
	 */
	private void setSortButtons() {
		this.faceButton.setOnAction(e->{								//
			if(this.faceButton.isSelected()) {							//
				this.nameButton.setSelected(false);						//
				this.pointsButton.setSelected(false);					//
				this.triFaceList();										//
			}else														//
				Collections.reverse(this.list.getItems());});			//
		this.nameButton.setOnAction(e->{								//
			if(this.nameButton.isSelected()) {							//
				this.faceButton.setSelected(false);						//  	A FAIRE DANS
				this.pointsButton.setSelected(false);					//		LE FXML
				this.triName();											//
			}else														//
				Collections.reverse(this.list.getItems());});			//
		this.pointsButton.setOnAction(e->{								//		
			if(this.pointsButton.isSelected()) {						//
				this.faceButton.setSelected(false);						//
				this.nameButton.setSelected(false);						//
				this.triPointsList();									//
			}else														//
				Collections.reverse(this.list.getItems());});			//
	}
	
	/**
	 * Affiche les infos du fichier choisi dans les zones de texte appropriées
	 */
	private void setInfos() {
		help.setOnAction(e->{
			afficherAide();});
		info.setOnAction(e->{
			afficherInfo();});
	}
	
	/**
	 * Initialise le bouton permettant de dupliquer la fenêtre actuelle
	 */
	private void setDuplicate() {
		duplicate.setOnAction(e->{
			dupliquer();});
	}

	/**
	 * Initialise les sliders qui agissent sur les sources de lumière
	 */
	private void setLightSliders() {
		sliderLight.setMax(Math.PI/2);
		sliderLight.setMin(-Math.PI/2);
		sliderLight.valueProperty().addListener((obs,old,n)->{
			repere.setLightAngle(sliderLight.getValue());});
	}
	
	/**
	 * Initialise les boutons permettant de choisir d'afficer ou non les arêtes
	 * et/ou de remplir les faces
	 */
	private void setStrokeButtons() {
		super.setButtons();
		fillColor.setOnAction(e -> {
			repere.setFaceColor(fillColor.getValue());});
		strokeColor.setOnAction(e -> {
			repere.setStrokeColor(strokeColor.getValue());});
	}

	/**
	 * Initialise les couleurs par défaut des arêtes et des faces du modèle
	 */
	private void setDefaultsColors() {
		fillColor.setValue(Color.GRAY);
		strokeColor.setValue(Color.BLACK);
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
	
	private void setMouseTranslate() {
		this.affichage.setOnMousePressed(e->{
			tmpX = e.getX();
			tmpY = e.getY();
			tmpZ = e.getZ();
			drag = true;});
		this.affichage.setOnMouseReleased(e->{
			drag = false;});
		this.affichage.setOnMouseDragged(e->{
			if(drag) {
				if(e.isPrimaryButtonDown()) {
					repere.translation(e.getX()-tmpX, e.getY()-tmpY);
					tmpX=e.getX();
					tmpY=e.getY();
				}else {
					repere.rotateY((e.getX()-tmpX)/100);
					repere.rotateX((e.getY()-tmpY)/100);
					repere.rotateZ((e.getZ()-tmpZ)/100);
					tmpX = e.getX();
					tmpY = e.getY();
					tmpZ = e.getZ();
				}
			}
		});
	}

	public void redraw() {
		affichage.getGraphicsContext2D().clearRect(0, 0, affichage.getWidth(), affichage.getHeight());
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
	}

	/**
	 * Déplace le modèle vers le haut
	 */
	public void translationPlusY() {
		this.repere.translation(0, RATIOY);
	}

	/**
	 * Déplace le modèle vers la droite
	 */
	public void translationPlusX() {
		this.repere.translation(RATIOX, 0);
	}

	/**
	 * Déplace le modèle vers la gauche
	 */
	public void translationMinusX() {
		this.repere.translation(-RATIOX, 0);
	}

	/**
	 * Initialise le zoom sur le modèle ave la molette
	 */
	public void setZoom() {
		center.setOnScroll(e -> {
			if (e.getDeltaY() > 0)
				this.repere.scaling(MainViewController.SCALING);
			else
				this.repere.scaling(MainViewController.UNSCALING);});
	}

	/**
	 * Initialise la rotation du modèle à partir de sliders ou des touches X, Y et Z
	 */
	public void setRotation() {
		vb.setOnKeyPressed(e-> {
			if (e.getCode().equals(KeyCode.Z)) 
				this.repere.rotateZ(Math.PI / 8);
			if (e.getCode().equals(KeyCode.Y))
				this.repere.rotateY(Math.PI / 8);
			if (e.getCode().equals(KeyCode.X))
				this.repere.rotateX((Math.PI / 8));
		});
		sliderX.valueProperty().addListener((obs, old, n) -> {
			this.repere.rotateX(((Double) n - (Double) old));});
		sliderY.valueProperty().addListener((obs, old, n) -> {
			this.repere.rotateY((Double) n - (Double) old);});
		sliderZ.valueProperty().addListener((obs, old, n) -> {
			this.repere.rotateZ((Double) n - (Double) old);});
	}
	
	public void ajuster() {
		
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
	
	private void triName() {
		Object [] tri = this.list.getItems().toArray();
		Arrays.sort(tri);
		this.list.getItems().clear();
		for(Object o : tri)
			this.list.getItems().add((File) o );
	}
	
	private void triPointsList() {
		HashMap<File, Integer> map = new HashMap<File, Integer>();
		for(File f : this.list.getItems()) {
			System.out.println(f);
			Reader r = null;
			try {
				r = new Reader(f);
				map.put(f, r.getNbPoints());
			} catch (Exception e) {
				map.put(f, -1);
				e.printStackTrace();
			}
		}
		TreeMap<File, Integer> map2 =triSurValeur(map);
		this.list.getItems().clear();
		this.list.getItems().addAll(map2.keySet());
	}
	
	private void dupliquer() {
		Stage secondStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("secondModel.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root, 600, 800);
			SecondViewController controller = loader.getController();
			controller.initialize(repere);
			controller.attacher();
			secondStage.setScene(scene);
			secondStage.setResizable(false);
			secondStage.setTitle("Affichage secondaire");
			secondStage.show();
		} catch (Exception e) {
			Stage stageError = new Stage();
			FXMLLoader errorLoader = new FXMLLoader();
			errorLoader.setLocation(getClass().getResource("error.fxml"));
			Parent errorRoot;
			try {
				errorRoot = errorLoader.load();
				Scene scene = new Scene(errorRoot, 600, 250);
				ErrorController controller = errorLoader.<ErrorController>getController();
				controller.init(e);
				stageError.initModality(Modality.APPLICATION_MODAL);
				stageError.setScene(scene);
				stageError.setResizable(false);
				stageError.setTitle("ERREUR");
				stageError.show();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(Subject subj){
		fillColor.setValue(repere.faceColor);
		strokeColor.setValue(repere.strokeColor);
		renderModel(repere.lightAngle);
	}
	
	@Override
	public void update(Subject subj, Object data){
		fillColor.setValue(repere.faceColor);
		strokeColor.setValue(repere.strokeColor);
		renderModel(repere.lightAngle);
	}
}