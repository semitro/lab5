package vt.smt.GUI;

import javafx.animation.ScaleTransition;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Locale;

/**
 * Кнопка, в которой выбираются языки
 */
public class LanguagesButton extends ImageView {

    private Image img;
    private ContextMenu contextMenu;

    public LanguagesButton(){
        super();
        img = new Image(getClass().getResourceAsStream("img/planet.png"));
        this.setImage(img);
        MenuItem item_en_CA = new MenuItem("english (CA)");
        item_en_CA.setOnAction((e)->Language.setLanguage(new Locale("en","CA")));

        MenuItem item_rus = new MenuItem("русский");
        item_rus.setOnAction((e)->Language.setLanguage(new Locale("ru")));

        MenuItem item_mk = new MenuItem("македонски");
        item_mk.setOnAction((e)->Language.setLanguage(new Locale("mk")));

        MenuItem item_pl = new MenuItem("polski");
        item_pl.setOnAction((e)->Language.setLanguage(new Locale("pl")));
        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(item_en_CA,item_mk,item_pl,item_rus);
        initEffects();
        this.setOnMouseClicked((e)->
                contextMenu.show(this,e.getScreenX(),e.getScreenY()) );

    }

    private void initEffects(){
        ScaleTransition scaling = new ScaleTransition(Duration.valueOf("0.25s"),this);
        scaling.setAutoReverse(true);
        scaling.setToX(1.3); scaling.setToY(1.3);
        this.setOnMouseEntered(e->scaling.play());
        ScaleTransition reScale = new ScaleTransition(Duration.millis(250),this);
        reScale.setToY(1); reScale.setToX(1);
        this.setOnMouseExited(e->reScale.play());
    }
}
