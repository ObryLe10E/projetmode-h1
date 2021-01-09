package view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import modele.Face;
import modele.Point;
import modele.Repere;
import modele.Vecteur;
import utils.Observer;
import utils.Subject;

public class ViewController implements Observer{
	
	protected GraphicsContext gc;
	
	protected Repere repere;

	@FXML
	protected Canvas affichage;
	@FXML
	protected ToggleButton filsDeFer;
	@FXML
	protected ToggleButton fillFaces;
	@FXML
	protected ToggleButton setLight;
	@FXML
	protected ToggleButton setShadow;
	@FXML
	private Pane center;
	
	protected boolean stroke;
	protected boolean light;
	protected boolean shadow;
	protected boolean faces;
	
	protected void setComponents() {
		setBooleans();
		setLightButtons();
		setButtons();
	}
	
	public void setBooleans() {
		faces = true; stroke = true;
		light = false; shadow = false;
	}
	
	public void setLightButtons() {
		setLight.setOnAction(e -> {
			switchLight();
		});
		setShadow.setOnAction(e -> {
			switchShadow();
		});
	}
	
	/**
	 * Initialise les boutons permettant de choisir d'afficer ou non les arêtes
	 * et/ou de remplir les faces
	 */
	public void setButtons() {
		filsDeFer.setOnAction(e -> { switchFaces();});
		filsDeFer.setTooltip(new Tooltip("Fil de fer"));
		filsDeFer.getTooltip().setShowDelay(new Duration(0));
		
		fillFaces.setOnAction(e -> { switchStroke();});
		fillFaces.setTooltip(new Tooltip("Afficher les fils"));
		fillFaces.getTooltip().setShowDelay(new Duration(0));
		
		setLight.setOnAction(e -> { switchLight();});
		setLight.setTooltip(new Tooltip("Activer la source de lumière"));
		setLight.getTooltip().setShowDelay(new Duration(0));
		
		setShadow.setOnAction(e -> { switchShadow();});
		setShadow.setTooltip(new Tooltip("Activer la source de lumière"));
		setShadow.getTooltip().setShowDelay(new Duration(0));
	}
	
	public void switchLight() {
		if(light) light = false;
		else light = true;
		update(repere);
	}
	
	public void switchShadow() {
		if(shadow) shadow = false;
		else shadow = true;
		update(repere);
	}
	
	public void switchStroke() {
		if(stroke) stroke = false;
		else stroke = true;
		update(repere);
	}
	
	public void switchFaces() {
		if(faces) faces = false;
		else faces = true;
		update(repere);
	}
	
	public void attacher() {
		repere.attach(this);
	}
	
	public void redraw() {
		affichage.getGraphicsContext2D().clearRect(0, 0, affichage.getWidth(), affichage.getHeight());
	}
	
	public void renderModel(double lightRatio) {
		this.repere.sortFaces();
		redraw();
		if(!shadow)
			this.renderOmbrage(gc,lightRatio);
		if (!faces)
			gc.setFill(Color.TRANSPARENT);
		else 
			gc.setFill(repere.faceColor);
		if (!stroke)
			gc.setStroke(Color.TRANSPARENT);
		else
			gc.setStroke(repere.strokeColor);
		for (Face face : repere.getFacesList()) {
			int size = face.size();
			double[] xPoints = new double[size];
			double[] yPoints = new double[size];
			for (int i = 0; i < face.size() ; i++) {
				xPoints[i] = face.get(i).getX();
				yPoints[i] = face.get(i).getY();
			}
			if(!setLight.isSelected()) {
				double coefLight = lightRatio;
				if(face.getColor(1, coefLight) <= 0) {
					gc.setFill(Color.BLACK);
				}else
					gc.setFill(Color.rgb(face.getColor(repere.faceColor.getRed(), coefLight), face.getColor(repere.faceColor.getGreen(),coefLight), face.getColor(repere.faceColor.getBlue(),coefLight)));
			}
			if(stroke)
				affichage.getGraphicsContext2D().strokePolygon(xPoints, yPoints, size);
			if(faces)
				affichage.getGraphicsContext2D().fillPolygon(xPoints, yPoints, size);
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
			if(!filsDeFer.isSelected()) {
				gc.setFill(Color.rgb(100, 100, 100));
				affichage.getGraphicsContext2D().fillPolygon(ombreX, ombreY, size);
			}
			if(!fillFaces.isSelected() & filsDeFer.isSelected()) {
				gc.setFill(Color.rgb(100,100,100));
				affichage.getGraphicsContext2D().strokePolygon(ombreX, ombreY, size);
				affichage.getGraphicsContext2D().setStroke(Color.rgb(100,100,100));
			}
		}
	}
	
	@Override
	public void update(Subject subj){}
	
	@Override
	public void update(Subject subj, Object data){}
}
