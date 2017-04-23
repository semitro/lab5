package vt.smt.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;



public class MainGUI extends Application {
    // BearsLine является HBox'ом
    private BearsLine bearsLine;
    private Stage primaryStage;
    private BorderPane pane;
    protected Alert confirmExit; // Сохранять ли файлы при выходе?
    @Override
    public void start(Stage inputStage) {
        primaryStage = inputStage;
        bearsLine = new BearsLine();
        pane = new BorderPane();
        ContextMenu cm = new ContextMenu();
        MenuItem mi = new MenuItem("sgs");
        cm.getItems().add(mi);

        pane.setBackground(
                new Background(
                new BackgroundImage(
                new Image(
                        getClass().getResourceAsStream("img/fone.jpg")),
                        BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

        pane.setCenter(bearsLine);
        primaryStage.setScene(new Scene(pane,600,250));
        primaryStage.getScene().setFill(Paint.valueOf("black"));
        primaryStage.setTitle("Медведики сущие, себя сквозь тьму космоса несущие");
        pane.getCenter().setTranslateY(pane.getHeight()/5);
        primaryStage.setResizable(false);
        primaryStage.show();

       // primaryStage.setOnHiding(e->confirmExit.show());

    }
}