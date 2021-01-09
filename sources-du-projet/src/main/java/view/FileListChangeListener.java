package view;

import java.io.File;
import java.io.IOException;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Reader;

public class FileListChangeListener implements ListChangeListener<File> {
	private MainViewController controller;
	
	public FileListChangeListener(MainViewController controller){
		this.controller = controller;
	}
	
	@Override
	public void onChanged(Change<? extends File> c) {
		try {
			if (!controller.list.getItems().isEmpty()) {
				try {
					controller.resetSliders();
					this.resetLabel();
					File path = controller.list.getSelectionModel().getSelectedItem().getAbsoluteFile();
					Reader reader = new Reader(path);
					controller.repere = reader.getRepere();
					if (reader.getAuthor() == null)
						controller.authorLabel.setText(controller.authorLabel.getText() + "INCONNU");
					else
						controller.authorLabel.setText(controller.authorLabel.getText() + reader.getAuthor());
					controller.nbPointsLabel.setText(controller.nbPointsLabel.getText() + reader.getNbPoints());
					controller.nbFacesLabel.setText(controller.nbFacesLabel.getText() + reader.getNbFaces());
					controller.center.setCursor(Cursor.CROSSHAIR);
					if(controller.daughter != null)
						controller.daughter.detacher();
					controller.attacher();
					controller.repere.center();				
					controller.repere.translation(controller.affichage.getWidth()/2, controller.affichage.getHeight()/2);
					controller.repere.frame(controller.affichage.getWidth(), controller.affichage.getHeight() / 2);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			if (!controller.list.getItems().isEmpty()) {
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
	 * Réinitialise les infos du modèle
	 * 
	 * @nbAuthorLabel Afficher le nom de l'auteur
	 * @nbPointsLabel Afficher le nombre de points du modèle
	 * @nbFacesLabel Afficher le Nombre de faces du modèle
	 */
	private void resetLabel() {
		controller.authorLabel.setText("Auteur :");
		controller.nbPointsLabel.setText("Nombre de points :");
		controller.nbFacesLabel.setText("Nombre de faces :");
	}
}