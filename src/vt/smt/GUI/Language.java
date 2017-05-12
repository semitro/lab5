package vt.smt.GUI;


import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
/**
 * Класс для интернационализации приложения
 * Использование - добавляешь с помощью метода addListener то, у чего можно менять текст,
 * устанавливаешь у этого чего-то в качестве ID properties name, а дальше класс всё сделает сам
 */
public  class Language {
    static {
        observers = new LinkedList<>();
        resourceBundle = ResourceBundle.getBundle("vt/smt/GUI/languages",new Locale("ru"));
    }
    // Текста, жаждущие меняться
    private static List<TextSettable > observers;

    private static ResourceBundle resourceBundle;
    public static void setLanguage(Locale locale){
        resourceBundle = ResourceBundle.getBundle("vt/smt/GUI/languages",locale);
        noticeAll();
    }
    // Юзай этот метод для добавления слушающего текста
    public static void addListener(TextSettable label){
        observers.add(label);
        label.setText(resourceBundle.getString(label.getAlias()));
    }
    public void addStageListener(Stage stage){

    }
    private static void noticeAll(){
        observers.forEach(current->{
            if(current == null) // Проблема убывших наблюдателей решается здесь
                observers.remove(current);
                else
            current.setText(resourceBundle.getString(current.getAlias()));
        });
    }
}
interface TextSettable{
    void setText(String value);
    String getAlias();
}
abstract class AbstractAdapter implements TextSettable{
    private String alias;
    public AbstractAdapter(String alias){
        this.alias = alias;
    }
    public String getAlias(){return alias;}
}
class MenuItemAdapter extends AbstractAdapter{
    private MenuItem menuItem;
    public MenuItemAdapter(MenuItem item,String property){
        super(property);
        this.menuItem = item;
        }
    public void setText(String value){
        menuItem.setText(value);
    }
}

class StageAdapter extends AbstractAdapter{
    private Stage menuItem;
    public StageAdapter(Stage stage,String property){
        super(property);
        this.menuItem = stage;
    }
    public void setText(String value){
        menuItem.setTitle(value);
    }
}

class CheckBoxAdapter extends AbstractAdapter{
    private CheckBox menuItem;
    public CheckBoxAdapter(CheckBox checkBox,String property){
        super(property);
        this.menuItem = checkBox;
    }
    public void setText(String value){
        menuItem.setText(value);
    }
}

class ButtonAdapter extends AbstractAdapter{
    private Button menuItem;
    public ButtonAdapter(Button button, String property){
        super(property);
        this.menuItem = button;
    }
    public void setText(String value){
        menuItem.setText(value);
    }
}

class LabelAdapter extends AbstractAdapter{
    private Label label;
    public LabelAdapter(Label label,String property){
        super(property);
        this.label = label;
    }
    public void setText(String value){label.setText(value);}
}