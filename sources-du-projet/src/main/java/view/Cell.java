package view;

import java.io.File;

import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import modele.Reader;

/**
 * Cellule de la liste des mod�les
 */
public class Cell extends ListCell<File> {
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