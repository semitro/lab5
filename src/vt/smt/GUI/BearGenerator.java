package vt.smt.GUI;

import com.github.sarxos.webcam.Webcam;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sun.nio.cs.Surrogate;
import sun.text.normalizer.UTF16;
import vt.smt.Toy;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URI;

/**
 * Окно генерации новых медведей
 */
public class BearGenerator extends BearWindow {

    @Override
    void initTitles(){

    }
    BearGenerator(Bear caller){super(caller);}
    @Override
    void initActions(){
        addButton.setOnAction(e->{
            ((BearsLine) (caller.getParent().getParent())).insertElemtnt(
                    Integer.valueOf(caller.getId())+1,
                    new Toy(nameInput.getText())
            );
            stage.close();
        });
    }

}
