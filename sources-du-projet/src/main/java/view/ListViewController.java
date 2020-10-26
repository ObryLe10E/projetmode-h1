package view;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Callback;
import modele.Face;
import modele.Point;
import modele.Reader;
import modele.Repere;

public class ListViewController {

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
        list.setCellFactory(new CellFact());
    }

    class CellFact extends ListCell<File> implements Serializable, Callback<ListView<File>, ListCell<File>> {
        private static final long serialVersionUID = 1L;

        @Override
        public void updateItem(File item, boolean b) {

            super.updateItem(item, b);
            if (item != null && !b) {
                Canvas c = new Canvas(400, 20);
                GraphicsContext gc = c.getGraphicsContext2D();
                gc.fillText(item.getName(), 25, 16);
                setGraphic(c);
            } else {
                setGraphic(null);
            }
        }

        @Override
        public ListCell<File> call(ListView<File> param) {
            return new CellFact();
        }
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
        Polygon poly = new Polygon();
        poly.getPoints().addAll(new Double[] { 0.0, 0.0, 2.0, 1.0, 1.0, 2.0 });
        poly.setFill(Color.BLACK);
        affichage.getChildren().add(poly);
        /*
         * for (Face face : this.repere.getFacesList()) { List<Point> list =
         * face.getPoints(); List<Double> listPoints = new ArrayList<>(); for (Point p :
         * list) { listPoints.add(p.getX()); listPoints.add(p.getY()); } Polygon polygon
         * = new Polygon(); polygon.getPoints().addAll(listPoints);
         * polygon.setFill(Color.RED); affichage.getChildren().add(polygon); }
         */
    }
}