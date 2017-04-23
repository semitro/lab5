package vt.smt.GUI;

/**
 * Created by semitro on 25.03.17.
 */
import vt.smt.Data.Toy;
import vt.smt.Client.Sender;
import vt.smt.Commands.ChangeBear;

import java.io.IOException;

public class BearModifyder extends  BearWindow{
    BearModifyder(Bear caller){super(caller);}
    @Override
    protected void initActions(){
        addButton.setOnAction(e->{
            double weight = 0.5;
            try{weight = Double.parseDouble(weightInput.getText());}catch (Exception bad){}
            try {
            Sender.getInstance().sendCommand(new ChangeBear(
                     new Toy(nameInput.getText(),weight,isCleanBox.isSelected()),
                    Integer.parseInt(caller.getId())
            ));
            }catch (IOException exception){
                System.out.println("BearModifyder::Ошибка при отправке");
                System.out.println(exception.getMessage());
            }
            // Внимание! Сначала нужно изменить поля объекта, чтобы получить действительный хэш-код
            this.renameImage(Integer.toString(caller.getInfo().hashCode()));
            caller.loadImgFromFile(Integer.toString(caller.getInfo().hashCode()));
            caller.getOwner().refreshVisible();
               stage.close();
        });
    }
    protected void initTitles(){
        addButton.setText("Преобразовать медедя!");
        stage.setTitle("Медведопреобразоватор три тысячи");
    }
}
