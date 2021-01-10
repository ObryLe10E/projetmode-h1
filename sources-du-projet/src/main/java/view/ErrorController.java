package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author h1 : DELOBEL Jeremy, DUHEM Alexis, OBRY Thomas, BONNET Tanguy
 */
public class ErrorController {
	@FXML
	Label lab;
	public void init(Exception exception) {
		if (exception.getMessage() != null)
			this.lab.setText(exception.getMessage());
		else exception.printStackTrace();
	}
}
