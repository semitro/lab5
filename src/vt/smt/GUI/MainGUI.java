package vt.smt.GUI;

import com.github.sarxos.webcam.Webcam;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.effect.*;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;


public class MainGUI extends Application {
    public static void main(String[] args) throws Exception{

//        File f = new File("snap");
//
//        f.createNewFile();
        launch(args);
    }
    private HBox outerLine;
    private HBox mainLine;
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        outerLine = new HBox();
        mainLine = new HBox();
        mainLine.setSpacing(20);
        ContextMenu cm = new ContextMenu();
        MenuItem mi = new MenuItem("sgs");
        cm.getItems().add(mi);

      //  mainLine.getChildren().add();

        mainLine.getChildren().add(new Bear());
        for (int i = 0; i < 1; i++) {
            mainLine.getChildren().add(new Bear());
        }

        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(1800));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.setNode(mainLine);

        class MouseDraggedWrapper{
            public double value;
        }
        MouseDraggedWrapper mouseDragged = new MouseDraggedWrapper();

        outerLine.setOnMousePressed(start->{
            mouseDragged.value = start.getSceneX();

        });
        outerLine.setOnMouseDragged(drag->{

        });
        outerLine.setOnMouseReleased(end->{
            translateTransition.setFromX(mainLine.getTranslateX());
            translateTransition.setToX(
                    mainLine.getTranslateX() + end.getSceneX() - mouseDragged.value);
            translateTransition.play();

        });

        outerLine.getChildren().add(mainLine);
        pane.setBackground(
                new Background(
                new BackgroundImage(
                new Image(
                        getClass().getResourceAsStream("fone.jpg")),
                        BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

        pane.setCenter(outerLine);

        primaryStage.setScene(new Scene(pane,600,250));
        primaryStage.setTitle("Медведики сущие");
        pane.getCenter().setTranslateY(pane.getHeight()/2+mainLine.getHeight());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}