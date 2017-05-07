package vt.smt.GUI;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Random;

/**
 * Created by semitro on 07.05.17.
 */
public class FallingStar {
    private Stage stage;
    private ImageView image;
    private HBox pane;
    public FallingStar(){
        stage = new Stage(StageStyle.TRANSPARENT);
         pane = new HBox();
        Scene scene = new Scene(pane,60,30, Paint.valueOf("transparent"));
        stage.initModality(Modality.NONE);
        //scene.setFill(n ull);
        pane.setStyle("-fx-background-color: null;");
        stage.setScene(scene);
        stage.toFront();
        image = new ImageView(new Image(getClass().getResourceAsStream("img/smallStar.png")));
        GaussianBlur blur = new GaussianBlur(0.4);
        image.setOpacity(0.8);
        image.setEffect(blur);
        image.setOnMouseEntered(e->{win();});
        image.setOnMouseClicked((e)->{win();});
        image.setFocusTraversable(true);
        pane.getChildren().add(image);
        pane.setOnMouseEntered((e)->{
            win();
        });

        stage.setX(Screen.getPrimary().getVisualBounds().getMinX() + Screen.getPrimary().getVisualBounds().getWidth() - 400);
        stage.setY(Screen.getPrimary().getVisualBounds().getMinX() + Screen.getPrimary().getVisualBounds().getHeight() - 400);
        automaticFall();
    }
    public static void win(){
        System.out.println("win");
        Stage st = new Stage();
        BorderPane box = new BorderPane();

        box.setStyle("-fx-background-color: linear-gradient(rgba(53,210,222,0.81),rgba(42,222,156,0.81));");
        Label label = new Label("ЭТО ПОБЕДА!\n" +
                "Вы заторчали пышку разработчику\n (см. license.txt)");
        label.setStyle("-fx-text-fill: rgba(15,39,222,0.81);" +
                "-fx-font-size: 24pt");
        box.setCenter(label);

        Scene sc = new Scene(box,400,200);

        st.setScene(sc);
        Media media = new Media(FallingStar.class.getResource("img/win.mp3").toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
        st.show();
    }
    private void automaticFall(){
        Random rand = new Random(System.currentTimeMillis());
        Thread t = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(rand.nextInt()%10000 + 4000);
                }catch (Exception ex){
                    System.out.println("Не спится звёздам");
                }
                Platform.runLater(this::fall);

            }
        });
        t.setDaemon(true);
        t.start();
    }
    private void fall(){
        Random rand = new Random(System.currentTimeMillis());
        stage.show();
        stage.toFront();
        Thread t = new Thread(()->{
            float u = 1.20f;
            double a = 0.00000028;
            long deltaTime = 0;
            long lastTime = System.currentTimeMillis();
            stage.setX(Screen.getPrimary().getVisualBounds().getMinX()
                    + Screen.getPrimary().getVisualBounds().getWidth()-stage.getWidth()- Math.abs(rand.nextInt())%400);
            stage.setY(stage.getHeight() + rand.nextInt()%400);
            FadeTransition fade = new FadeTransition(Duration.millis(600));
            fade.setFromValue(0);
            fade.setToValue(0.8);
            fade.setNode(image);
            FadeTransition fadeAway = new FadeTransition(Duration.millis(200));
            fadeAway.setFromValue(0.8);
            fadeAway.setToValue(0);
            fadeAway.setNode(image);
            fadeAway.setDelay(Duration.millis(400));
            fade.setOnFinished((e)->fadeAway.play());
            fade.play();
            while(stage.getY() < Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight() -2){
                deltaTime = System.currentTimeMillis() - lastTime;
                lastTime = System.currentTimeMillis();
                u+= a*deltaTime;
                stage.setX(stage.getX() - 2/1.44828*u*deltaTime);
                stage.setY(stage.getY() + u*deltaTime);
                try {
                   Thread.sleep(0,4);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
        t.setPriority(5);
        t.setDaemon(true);
        t.start();
    }
}
