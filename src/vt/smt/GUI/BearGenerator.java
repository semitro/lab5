package vt.smt.GUI;


import vt.smt.Data.Toy;

/**
 * Окно генерации новых медведей
 */
import vt.smt.Client.Sender;
import vt.smt.Commands.InsertBear;

import java.io.IOException;

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
            try {
                Sender.getInstance().sendCommand(new InsertBear(newToy, 1 + Integer.parseInt(caller.getId())));
            }catch (IOException except){
                System.out.println("Ошибка при выводе нового медведя на орбиту");
                System.out.println(except.getMessage());
            }
            stage.close();
        });
    }

}
