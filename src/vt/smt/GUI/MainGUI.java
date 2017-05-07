package vt.smt.GUI;

import com.github.sarxos.webcam.Webcam;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


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
        inputStage.setMaxWidth(1280);
        inputStage.setMaxWidth(Screen.getPrimary().getBounds().getWidth());
        inputStage.setMaxHeight(Screen.getPrimary().getBounds().getHeight());
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
        primaryStage.setOnCloseRequest(e->{
            System.exit(0); });
        try {
            Scanner scanner = new Scanner(getClass().getResourceAsStream("img/star.settings"));
            if (scanner.hasNext() && scanner.next().equals("on")) {
                star = new FallingStar();
            }
        }catch (Exception e){
            System.out.println("Не удалось загрузить файл с настройками звёзд!");
            e.printStackTrace();
        }
    }

    private FallingStar star;
}