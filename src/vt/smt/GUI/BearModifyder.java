package vt.smt.GUI;

/**
 * Created by semitro on 25.03.17.
 */
public class BearModifyder extends  BearWindow{
    BearModifyder(Bear caller){super(caller);}
    @Override
    protected void initActions(){
        addButton.setOnAction(e->{
            caller.getInfo().setName(nameInput.getText());
            caller.getInfo().setCleaning(isCleanBox.selectedProperty().getValue());

            double weight = 0.5;
            try{weight = Double.parseDouble(weightInput.getText());}catch (Exception bad){}
            caller.getInfo().setWeight(weight);
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
