package vt.smt.GUI;

/**
 * Created by semitro on 25.03.17.
 */
public class BearModifyder extends  BearWindow{
    BearModifyder(Bear caller){super(caller);}
    @Override
    void initActions(){
        addButton.setOnAction(e->{
            caller.loadImgFromFile(Integer.toString(caller.getInfo().hashCode()));
               caller.getOwner().refreshVisible();
               stage.close();
        });
    }
    void initTitles(){
        addButton.setText("Преобразовать медедя!");
        stage.setTitle("Медведопреобразоватор три тысячи");
    }
}
