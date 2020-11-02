package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorController {
	@FXML
	Label lab;

	public void init(Exception e) {
		if(e.getMessage() != null)
			this.lab.setText(e.getMessage());
		else this.lab.setText("Il manque un message dans le catch :)");
	}
}
