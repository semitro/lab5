package vt.smt.GUI;

import com.github.sarxos.webcam.Webcam;
import com.sun.istack.internal.NotNull;
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
import vt.smt.Home;
import vt.smt.Main;
import vt.smt.PhysicalObject;
import vt.smt.Toy;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;


public class MainGUI extends Application {
    // BearsLine является HBox'ом
    private BearsLine bearsLine;
    @Override
    public void start(Stage primaryStage) {
        bearsLine = new BearsLine();
        BorderPane pane = new BorderPane();
        ContextMenu cm = new ContextMenu();
        MenuItem mi = new MenuItem("sgs");
        cm.getItems().add(mi);

        pane.setBackground(
                new Background(
                new BackgroundImage(
                new Image(
                        getClass().getResourceAsStream("fone.jpg")),
                        BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

        pane.setCenter(bearsLine);
        primaryStage.setScene(new Scene(pane,600,250));
        primaryStage.setTitle("Медведики сущие");
        pane.getCenter().setTranslateY(pane.getHeight()/2+bearsLine.getLayoutY());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}