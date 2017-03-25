package vt.smt.GUI;


import javafx.animation.FadeTransition;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


/**
 * Created by semitro on 23.03.17.
 */
public class FallingList extends ContextMenu{
    BearGenerator bearGenerator;
    BearModifyder bearModifyder;
    // Нуждается в информации о том, какой медведь его вызывает, потомушто модифицирует именно его.
        FallingList(Bear bearCaller){
            super();
            bearGenerator = new BearGenerator(bearCaller);
            bearModifyder = new BearModifyder(bearCaller);
            MenuItem add = new MenuItem("Добавить");
            add.setOnAction(e->bearGenerator.show());
            this.getItems().add(add);

            MenuItem modify = new MenuItem("Изменить");
            modify.setOnAction(e->bearModifyder.show());
            this.getItems().add(modify);

            MenuItem delete = new MenuItem("Удалить");
            delete.setOnAction(e-> {
                ((BearsLine) (bearCaller.getParent().getParent())).removeElement(bearCaller.getId());
                ((BearsLine)(bearCaller.getParent().getParent())).refreshVisible();
                    }
            );
            this.getItems().add(delete);

        }

}
