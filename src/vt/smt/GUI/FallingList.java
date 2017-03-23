package vt.smt.GUI;


import javafx.animation.FadeTransition;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


/**
 * Created by semitro on 23.03.17.
 */
public class FallingList extends ContextMenu{
    BearGenerator bearGenerator;
        FallingList(){
            super();
            bearGenerator = new BearGenerator();
            MenuItem add = new MenuItem("Добавить");
            add.setOnAction(e->bearGenerator.show());
            this.getItems().add(add);

            this.getItems().add(new MenuItem("Добавить"));
        }

}
