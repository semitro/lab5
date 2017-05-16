package vt.smt.GUI;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Уведомление о.о
 ***/
public class Notice extends Label implements vt.smt.Client.NoticeSystem{
    private String css = " -fx-font-size: 19px;\n" +
            " -fx-font-family: Comic Sans MS;" +
            "fx-border-color: white;" +
            "    -fx-text-fill: rgba(52,63,222,0.81);\n" +
            "   -fx-fill: lightseagreen;\n" +
            "-fx-stroke: firebrick;\n" +
            " -fx-stroke-width: 2px;" +
            " -fx-opacity: 0.9;";

    Notice(){
        super("");
        this.setStyle(css);

        fade.setNode(this);
        appiar.setNode(this);
        appiar.setFromValue(0);
        appiar.setToValue(1);
        appiar.setOnFinished((e)->fade.play());
        fade.setDelay(Duration.valueOf("4s"));
        fade.setToValue(0);
    }

    @Override
    public void notice(String message){
        Platform.runLater(()->{
            setText(message);
            appiar.play();
        });
    }
    private FadeTransition fade = new FadeTransition(Duration.valueOf("3s"));
    private FadeTransition appiar = new FadeTransition(Duration.valueOf("3s"));

}
