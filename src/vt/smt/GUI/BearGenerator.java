package vt.smt.GUI;


import vt.smt.Data.Toy;

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
