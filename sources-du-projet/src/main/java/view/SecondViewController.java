package view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class SecondViewController extends ListViewController {
	@FXML
	Canvas canvas;
	
	public void initialize() {
		
	}
	public void attache() {
		this.repere.attach(this);
	}
}