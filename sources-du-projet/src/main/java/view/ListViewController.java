package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import modele.Face;
import modele.Point;
import modele.Reader;
import modele.Repere;

public class ListViewController {
    class Cell extends ListCell<File>{
    	HBox hbox = new  HBox();
    	Button button = new Button("ok");
    	Image img = new Image("https://img.icons8.com/color/72/file.png");
    	ImageView imgv = new ImageView(img);
    
    	
    	
    	public Cell() {
    		super();
    		button.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
    		imgv.maxWidth(1);
    		imgv.maxHeight(1);
    		hbox.getChildren().addAll(imgv,button);
    		this.setStyle("-fx-background-color: transparent");
    	}
    	public void updateItem(File item, boolean empty) {
    		super.updateItem(item, empty);
    		setText(null);
    		setGraphic(null);
    		if(item != null && !empty) {
        		this.setStyle("-fx-background-color: darkslateblue; -fx-background-radius : 20px; -fx-border-color:ffffff; -fx-border-radius : 20px");
    			setText(item.getName());
    			setGraphic(hbox);
    		}
    	}
    }

    @FXML
    ListView<File> list;
    @FXML
    Group affichage;
    private Repere repere;

    public void initialize() {
        Directory dir = new Directory("src/main/resources/fichiers/");
        list.getItems().addAll(dir.getListOfFiles());
        list.refresh();
        list.getSelectionModel().getSelectedItems().addListener(new FileListChangeListener());
        list.setCellFactory(param-> new Cell());
    }

    class FileListChangeListener implements ListChangeListener<File> {
        @Override
        public void onChanged(Change<? extends File> c) {
            Point.resetID();
            Face.resetID();
            if (!list.getItems().isEmpty()) {
                try {
                    // resetSliders();
                    File path = list.getSelectionModel().getSelectedItem().getAbsoluteFile();
                    Reader reader = new Reader(path);
                    repere = reader.getRepere();
                    System.out.println(repere);
                    renderModel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void renderModel() {
        affichage.getChildren().clear();
        for (Face face : this.repere.getFacesList()) {
            List<Point> list = face.getPoints();
            List<Double> listPoints = new ArrayList<>();
            for (Point p : list) {
                listPoints.add(p.getX());
                listPoints.add(p.getY());
            }
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(listPoints);
            polygon.setFill(Color.RED);
            affichage.getChildren().add(polygon);
        }
    }
}