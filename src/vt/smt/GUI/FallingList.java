package vt.smt.GUI;


import javafx.animation.FadeTransition;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


/**
 * Created by semitro on 23.03.17.
 */
public class FallingList extends ContextMenu{
    BearGenerator bearGenerator;
    // Нуждается в информации о том, какой медведь его вызывает, потомушто модифицирует именно его.
        FallingList(Bear bearCaller){
            super();
            bearGenerator = new BearGenerator(bearCaller);
            MenuItem add = new MenuItem("Добавить");
            add.setOnAction(e->bearGenerator.show());
            this.getItems().add(add);
            this.getItems().add(new MenuItem("Изменить"));

        }

}
