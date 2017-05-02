package vt.smt.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.InputStreamReader;


public class MainGUI extends Application {
    // BearsLine является HBox'ом
    private BearsLine bearsLine;
    private Stage primaryStage;
    private BorderPane pane;
    protected Alert confirmExit; // Сохранять ли файлы при выходе?
    private ServerButton serverButton;
    // Ответы, приходящие с сервера
    private Notice noticer = new Notice();
    @Override
    public void start(Stage inputStage) {
        vt.smt.Client.InputCommandsHandler.initNoticeSystem(noticer);
        primaryStage = inputStage;
        bearsLine = new BearsLine();
        pane = new BorderPane();
        ContextMenu cm = new ContextMenu();
        MenuItem mi = new MenuItem("sgs");
        cm.getItems().add(mi);
        serverButton = new ServerButton();
        pane.setBackground(
                new Background(
                new BackgroundImage(
                new Image(
                        getClass().getResourceAsStream("img/fone.jpg")),
                        BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

        pane.setCenter(bearsLine);
        pane.setRight(serverButton);
        pane.setBottom(noticer);
        pane.getBottom().setTranslateX(40);
        Scene scene = new Scene(pane,600,250);
        primaryStage.setScene(scene);
        primaryStage.getScene().setFill(Paint.valueOf("black"));
        primaryStage.setTitle("Медведики сущие, себя сквозь тьму космоса несущие");
        pane.getCenter().setTranslateY(pane.getHeight()/4);
        pane.getRight().setTranslateY(pane.getHeight()-40);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}