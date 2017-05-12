package vt.smt.GUI;


import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import vt.smt.Client.Sender;
import vt.smt.Commands.RemoveBear;

import java.io.IOException;

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
            Language.addListener(new MenuItemAdapter(add,"FallingList.add"));
            add.setOnAction(e->bearGenerator.show());
            this.getItems().add(add);

            MenuItem modify = new MenuItem("Изменить");
            Language.addListener(new MenuItemAdapter(modify,"FallingList.change"));
            modify.setOnAction(e->bearModifyder.show());
            this.getItems().add(modify);

            MenuItem delete = new MenuItem("Удалить");
            Language.addListener(new MenuItemAdapter(delete,"FallingList.remove"));
            delete.setOnAction(e-> {
                try {
                    Sender.getInstance().sendCommand(new RemoveBear(Integer.parseInt(bearCaller.getId())) );
                }catch (IOException excepiton){
                    // Можно запилить установление режима ожидания
                    System.out.println("FallingList: удаление медведя не прошло успешно");
                    System.out.println(excepiton.getMessage());
                }
            }
            );
            this.getItems().add(delete);

        }

}
