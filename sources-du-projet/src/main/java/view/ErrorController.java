package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author h1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class ErrorController {
	@FXML
	Label lab;
	public void init(Exception e) {
		if (e.getMessage() != null)
			this.lab.setText(e.getMessage());
		else
			this.lab.setText("Il manque un message dans le catch :)");
	}
}
