package vt.smt.GUI;

/**
 * Created by semitro on 25.03.17.
 */
public class BearModifyder extends  BearWindow{
    BearModifyder(Bear caller){super(caller);}
    @Override
    void initActions(){

    }
    void initTitles(){
        addButton.setText("Преобразовать медедя!");
        stage.setTitle("Медведопреобразоватор три тысячи");
    }
}
