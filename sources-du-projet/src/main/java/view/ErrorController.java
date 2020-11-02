package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorController {
	@FXML
	Label lab;

	public void init(Exception e) {
		this.lab.setText(e.getMessage());
	}
}
