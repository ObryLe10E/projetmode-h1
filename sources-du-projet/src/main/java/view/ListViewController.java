package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import utils.Observer;
import utils.Subject;

public class ListViewController implements Observer{
	@FXML
	private Pane center;
	@FXML
	private ListView<File> list;
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

	protected Repere repere;

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
	 * Initialise la fenÃªtre de l'application et ses fonctionnalitÃ©s, Ã  partir d'une
	 * bibliothÃ¨que par dÃ©faut
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
		duplicate.setOnAction(e->{
			dupliquer();
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
			}else
				Collections.reverse(this.list.getItems());
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
			}else
				Collections.reverse(this.list.getItems());
		});
	}


	/**
	 * Initialise les boutons permettant de choisir d'afficer ou non les arÃªtes
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

		sliderLight.setTooltip(new Tooltip("Varie la lumiére de π à -π "));
		sliderLight.getTooltip().setShowDelay(new Duration(0));

		info.setTooltip(new Tooltip("Informations"));
		info.getTooltip().setShowDelay(new Duration(0));

		help.setTooltip(new Tooltip("Aide"));
		help.getTooltip().setShowDelay(new Duration(0));
		
		duplicate.setTooltip(new Tooltip("Ouvre le modele dans une nouvelle fenetre"));
		duplicate.getTooltip().setShowDelay(new Duration(0));

		ombreButon.setTooltip(new Tooltip("Affiche l'ombre"));
		ombreButon.getTooltip().setShowDelay(new Duration(0));
		
		lightButon.setTooltip(new Tooltip("Affiche la lumiére"));
		lightButon.getTooltip().setShowDelay(new Duration(0));
		
		
		
	}

	/**
	 * Initialise les couleurs par dÃ©faut des arÃªtes et des faces du modÃ¨le
	 */
	private void setDefaultsColors() {
		fillColor.setValue(Color.DARKGRAY);
		strokeColor.setValue(Color.ANTIQUEWHITE);
	}

	/**
	 * Initialise les sliders de rotation du modÃ¨le
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

	private void attache() {
		repere.attach(this);
	}

	/**
	 * ChangeListener qui ouvre le fichier sur lequel on clique dans la
	 * bibliothÃ¨que, ou une popup d'erreur s'il n'est pas valide
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
						attache();
						repere.center();				
						repere.translation(affichage2.getWidth()/2, affichage2.getHeight()/2); // centrage de la figure approximatif
						/*while (repere.getMax() < affichage2.getWidth()-50)
							repere.scaling(SCALING);
						while (repere.getMax() > affichage2.getWidth()-affichage2.getWidth()/4)
							repere.scaling(UNSCALING);*/
						repere.frame(affichage2.getWidth(), affichage2.getHeight() / 2);
					} catch (IOException e) {
						System.out.println(e.getMessage());
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
		 * RÃ©initialise les infos du modÃ¨le
		 * 
		 * @nbAuthorLabel Afficher le nom de l'auteur
		 * @nbPointsLabel Afficher le nombre de points du modÃ¨le
		 * @nbFacesLabel Afficher le Nombre de faces du modÃ¨le
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
		this.affichage2.setOnMouseReleased(e->{
			drag = false;
		});
		this.affichage2.setOnMouseDragged(e->{
			if(drag) {
				double x = e.getX();
				double y = e.getY();
				double z = e.getZ();
				if(e.isPrimaryButtonDown()) {
					repere.translation(x-tmpX, y-tmpY);
					tmpX=x;
					tmpY=y;
				}else {
					repere.rotateY((x-tmpX)/100);
					repere.rotateX((y-tmpY)/100);
					repere.rotateZ((z-tmpZ)/100);
					tmpX = x;
					tmpY = y;
					tmpZ = z;
				}
			}
		});
	}

	/**
	 * RÃ©initialise l'affichage du modÃ¨le prÃ©cÃ©dent Trie les faces du modÃ¨le selon Z
	 * dÃ©croissant CrÃ©e un Polygon pour chaque face du modÃ¨le et l'affiche avec ses
	 * arÃªtes
	 */
	public void activerFDF() {
		if(this.filDeFer.isSelected())
			gc.setFill(Color.TRANSPARENT);
		else 
			gc.setFill(fillColor.getValue());
	}
	public void activerFace() {
		if (this.afficherFils.isSelected())
			gc.setStroke(Color.TRANSPARENT);
		else
			gc.setStroke(strokeColor.getValue());
	}
	public void renderModel() {
		//int nbDessins = 0;
		boolean filSelected = this.filDeFer.isSelected();
		int comp =0;
		this.repere.sortFaces();
		redraw();	
		comp++;
		if(!this.ombreButon.isSelected()) {
			this.renderOmbrage(gc,this.sliderLight.getValue());
		}

		Color c = fillColor.getValue();
		double xPoints[];
		double yPoints[];
		List<Point> l;
		for (Face face : this.repere.getFacesList()) {

			int size = face.size();
			l = face.getPoints();
			if(size == 3) {
				xPoints = new double[] { l.get(0).getX(), l.get(1).getX(), l.get(2).getX()};
				yPoints = new double[] { l.get(0).getY(), l.get(1).getY(), l.get(2).getY()};
			}else {
				xPoints = new double[] { l.get(0).getX(), l.get(1).getX(), l.get(2).getX(), l.get(3).getX()};
				yPoints = new double[] { l.get(0).getY(), l.get(1).getY(), l.get(2).getY(), l.get(3).getY()};
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
		}
		/*//System.out.println(nbDessins++);
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
		}*/

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
	 * Cellule de la liste des modï¿½les
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
					this.setStyle(this.getStyle() + "; -fx-background-color:  #6F6F6F");
				} catch (Exception e) {
					this.setStyle(this.getStyle() + "; -fx-background-color:  #9B5D43");
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
	 * Laisse l'utilisateur choisir le dossier de sa bibliothÃ¨que de fichiers Ã 
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
	 * DÃ©place le modÃ¨le vers le bas
	 */
	public void translationMinusY() {
		this.repere.translation(0, -RATIOY);
		//		this.renderModel();
	}

	/**
	 * DÃ©place le modÃ¨le vers le haut
	 */
	public void translationPlusY() {
		this.repere.translation(0, RATIOY);
		//		this.renderModel();
	}

	/**
	 * DÃ©place le modÃ¨le vers la droite
	 */
	public void translationPlusX() {
		this.repere.translation(RATIOX, 0);
		//		this.renderModel();
	}

	/**
	 * DÃ©place le modÃ¨le vers la gauche
	 */
	public void translationMinusX() {
		this.repere.translation(-RATIOX, 0);
		//		this.renderModel();
	}

	/**
	 * Initialise le zoom sur le modÃ¨le ave la molette
	 */
	public void setZoom() {
		center.setOnScroll(e -> {
			if (e.getDeltaY() > 0)
				this.repere.scaling(ListViewController.SCALING);
			else
				this.repere.scaling(ListViewController.UNSCALING);
			//			this.renderModel();
		});
	}

	/**
	 * Initialise la rotation du modÃ¨le Ã  partir de sliders ou des touches X, Y et Z
	 */
	public void rotate() {
		vb.setOnKeyPressed(e-> {
			if (e.getCode().equals(KeyCode.Z)) 
				this.repere.rotateZ(Math.PI / 8);
			if (e.getCode().equals(KeyCode.Y))
				this.repere.rotateY(Math.PI / 8);
			if (e.getCode().equals(KeyCode.X))
				this.repere.rotateX((Math.PI / 8));
			//			this.renderModel();
		});
		sliderX.valueProperty().addListener((obs, old, n) -> {
			this.repere.rotateX(((Double) n - (Double) old));
			//			this.renderModel();
		});
		sliderY.valueProperty().addListener((obs, old, n) -> {
			this.repere.rotateY((Double) n - (Double) old);
			//			this.renderModel();
		});
		sliderZ.valueProperty().addListener((obs, old, n) -> {
			this.repere.rotateZ((Double) n - (Double) old);
			//			this.renderModel();
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
		System.out.println("liste triï¿½e :" + triSurValeur(map));
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
				//			} catch (WrongFileFormatException e) {
				//				map.put(f, -1);
				//				e.printStackTrace();
			}
		}
		System.out.println("liste triï¿½e :" + triSurValeur(map));
		TreeMap<File, Integer> map2 =triSurValeur(map);
		this.list.getItems().clear();
		this.list.getItems().addAll(map2.keySet());
	}

	private void resetPosition() {

	}

	private void dupliquer() {
		Stage secondStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("secondModel.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root, 600, 250);
			SecondViewController controller = loader.getController();
			controller.initialize();
			secondStage.setScene(scene);
			secondStage.setResizable(false);
			secondStage.setTitle("Affichage secondaire");
			secondStage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void update(Subject subj){
		this.renderModel();
	}

	@Override
	public void update(Subject subj, Object data){
		this.renderModel();
	}
}