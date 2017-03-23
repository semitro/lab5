package vt.smt.GUI;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.*;


/**
 * Created by semitro on 22.03.17.
 */
public class Bear extends javafx.scene.image.ImageView {
    private FallingList fallingList;
    private Image img;
    Bear(){
        this("defaultBear.png");
    }
    Bear(String imgPath){
        super();
        fallingList = new FallingList();

        img = new Image(getClass().getResourceAsStream("defaultBear.png"));
        this.setImage(img);
        initAnim();
    }
    void initAnim(){
        // Scaling grow
        ScaleTransition scaling = new ScaleTransition();
        scaling.setToX(1.3);
        scaling.setToY(1.3);
        scaling.setDuration(Duration.valueOf("0.25s"));
        scaling.setAutoReverse(true);
        scaling.setNode(this);
        setOnMouseEntered(e->scaling.play());
        // Scaling ungrow
        ScaleTransition reScaling = new ScaleTransition();
        reScaling.setToX(1);
        reScaling.setToY(1);
        reScaling.setDuration(Duration.valueOf("0.25s"));
        reScaling.setNode(this);
        setOnMouseExited(e->reScaling.play());
        //
        Reflection reflection = new Reflection();
        this.setEffect(reflection);
        //
        setOnContextMenuRequested(e->{
            fallingList.show(this,e.getScreenX(),e.getScreenY());

        });

    }
}