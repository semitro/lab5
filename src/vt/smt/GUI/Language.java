package vt.smt.GUI;


import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import vt.smt.Commands.ChangeLocale;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Класс для интернационализации приложения
 * Использование - добавляешь с помощью метода addListener то, у чего можно менять текст,
 * устанавливаешь у этого чего-то в качестве ID properties name, а дальше класс всё сделает сам
 */
public  class Language {
    static {
        observers = new LinkedList<>();
        resourceBundle = ResourceBundle.getBundle("vt/smt/GUI/languages",new Locale("ru"));
        currentLocale = Locale.getDefault();
    }
    // Текста, жаждущие меняться
    private static List<TextSettable> observers;
    private static ResourceBundle resourceBundle;
    private static Locale currentLocale;

    public static void setLanguage(Locale locale){
        resourceBundle = ResourceBundle.getBundle("vt/smt/GUI/languages",locale);
        currentLocale = locale;
        try {
            vt.smt.Client.Sender.getInstance().sendCommand(new ChangeLocale(currentLocale));
        }catch (IOException e){
            System.out.println("Не удалось попросить у сервера сменить нам локаль");
            e.printStackTrace();
        }
        noticeAll();
    }

    public static String getString(String key){
        return resourceBundle.getString(key);
    }

    public static Locale getLocale(){
        return currentLocale;
    }

    // Юзай этот метод для добавления слушающего текста
    public static void addListener(TextSettable label){
        observers.add(label);
        label.setText(resourceBundle.getString(label.getAlias()));
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
/**
 * JavaFx не имеет интерфейса textSettable,
 * поэтому пришлось реализовать много адаптеров
 */

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
    private Stage stage;
    public StageAdapter(Stage stage,String property){
        super(property);
        this.stage= stage;
    }
    public void setText(String value){
        this.stage.setTitle(value);
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

class DataZoneAdapter extends AbstractAdapter {
    private Label label;
    // Медведь - владелец времён,
    // в нём хранится дата, которую нужно выводить на лейбл в разных форматах
    private Bear timeOwner;

    public DataZoneAdapter(Label label, Bear timeOwner) {
        // Быстрый костыль
        super("BearsWindow.BirthDay");
        this.label = label;
        dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy  hh:mm a");

        this.timeOwner = timeOwner;
    }

    public void setText(String value) {
        if(Language.getLocale().getLanguage().equals("ru")) {
            dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM YYY");
        }
        dateTimeFormatter.withLocale(Language.getLocale());
       try {
            label.setText(value + ' ' + timeOwner.getInfo().getCreationTime().format(dateTimeFormatter.withLocale(Language.getLocale())));
        }catch(NullPointerException e){
            System.out.println("Откуда мир в setText?");
        }
    }

    private DateTimeFormatter dateTimeFormatter;
}