package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Appli extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("model.fxml"));
        Parent root = loader.load();
        /*
         * Group group = new Group(); Polygon poly = new Polygon();
         * poly.getPoints().addAll(new Double[] { 0.0, 0.0, 20.0, 10.0, 10.0, 20.0 });
         * poly.setFill(Color.BLACK); group.getChildren().add(poly);
         * 
         * Group g = new Group(); for (int i = 0; i < 5; i++) { Rectangle r = new
         * Rectangle(); r.setY(i * 20); r.setWidth(100); r.setHeight(10);
         * r.setFill(Color.RED); g.getChildren().add(r); }
         */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("3D Generation Application");
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}