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
    protected void initTitles(){

    }
    BearGenerator(Bear caller){super(caller);}
    @Override
    protected void initActions(){
        addButton.setOnAction(e->{
            Toy newToy;
            try {
                newToy = new Toy(nameInput.getText(),
                        Double.parseDouble(weightInput.getText()), isCleanBox.isSelected());
                renameImage(Integer.toString(newToy.hashCode()));
            }catch (Exception bad){
                newToy = new Toy(nameInput.getText(),0.5,isCleanBox.isSelected());
            }
            caller.getOwner().insertElemtnt(
                    Integer.valueOf(caller.getId())+1, // вставляем после
                    newToy
            );
            stage.close();
        });
    }

}
