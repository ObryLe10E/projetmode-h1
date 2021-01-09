package view;

import javafx.stage.Stage;
import modele.Repere;
import utils.Subject;

public class SecondViewController extends ViewController {
	public void initialize(Repere repere) {
		affichage.setManaged(false);
		gc = affichage.getGraphicsContext2D();	
		setComponents();
		this.repere = repere;
	}
	
	@Override
	protected void setComponents() {
		super.setComponents();
	}
	
	@Override
	public void detacher() {
		super.detacher();
		Stage stage = (Stage) filsDeFer.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	public void update(Subject subj){
		renderModel(repere.lightAngle);
	}
	
	@Override
	public void update(Subject subj, Object data){
		renderModel(repere.lightAngle);
	}
}