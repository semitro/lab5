package vt.smt.GUI;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import vt.smt.Toy;

import java.awt.*;
import java.io.File;
import java.util.Enumeration;
/**
 * Created by semitro on 22.03.17.
 */
public class Bear extends javafx.scene.image.ImageView {
    private FallingList fallingList;
    private Image img;

    //GUI/img/*
    Bear(){
        this("img/defaultBear.png");
    }
    Bear(String imgPath){
        super();
        img = new Image(getClass().getResourceAsStream("img/defaultBear.png"));
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
            if(fallingList == null)
                fallingList = new FallingList(this);
            fallingList.show(this,e.getScreenX(),e.getScreenY());

        });

    }
    Toy getInfo(){
        if(this.getId() == null)
            return null;
        else // Модель медведя принадлежит главному боксу BearsLine, который сам является боксом
            return ((BearsLine)this.getParent().getParent()).getInfoAbout(Integer.valueOf(this.getId()));
    }
    public void loadImgFromFile(String path){
        File file = new File(path);
        if(!file.exists())
            return;
        img = new Image("file:" + path);
        System.out.println("path: " + path);
       this.setImage(img);
        if(this.getOwner() != null)
            this.getOwner().refreshVisible();

    }
    public BearsLine getOwner(){
        if( this.getParent() != null && (this.getParent().getParent() instanceof  BearsLine))
             return ((BearsLine) (this.getParent().getParent()));
        else {
            System.out.println("Ну ничего, разберёмся)");
            return null;
        }
    }
}
