package vt.smt.GUI;


import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Вывод стихов (ожидается при удалении всех медведей)
 */
public class Titleses extends VBox {
    Text text;
    private int stroke;
    // Каждые 4 строки записываем в одну строку, чтобы выводить четверостишье
    private LinkedList<String> strings;
    public Titleses(){
        this(System.getProperty("user.dir") +
                File.separator + "things" + File.separator  + "poem.txt");
    }
    Titleses(String pathToFile){
        text = new Text();
        text.setFont(Font.font("Ubuntu",26));
        text.setFill(Paint.valueOf("white"));
        try {
            strings = new LinkedList<>();
            StringBuilder temp = new StringBuilder();
            Scanner reader = new Scanner(new File(pathToFile));
            while (reader.hasNextLine()) {
                stroke++;
                temp.append(reader.nextLine() + '\n');
                if(stroke == 4 || !reader.hasNextLine()) {
                    strings.addLast(new String(temp));
                    temp = new StringBuilder();
                    stroke = 0;
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Файл " + pathToFile + " не найден.\n Стихов не будет.");
        }
        this.getChildren().add(text);
        fadeAway = new FadeTransition(Duration.seconds(3),this);
        fadeBack = new FadeTransition(Duration.seconds(3),this);
        fadeBack.setFromValue(0);
        fadeBack.setToValue(1);
        fadeAway.setFromValue(1);
        fadeAway.setToValue(0);
        fadeAway.setDelay(Duration.valueOf("2.8s"));
        fadeBack.setOnFinished(e -> {
            fadeAway.play();
        });

    }
    private FadeTransition fadeAway;
    private FadeTransition fadeBack;
    private DropShadow shadow;
    public void start(double height,double weight){
        shadow = new DropShadow();
        InnerShadow is = new InnerShadow();
        is.setInput(shadow);
        text.setEffect(is);
        text.setTranslateX(weight/4.8);
        text.setTranslateY(height/14);

        ListIterator<String>i = strings.listIterator();

            text.setText(i.next());
        fadeAway.setOnFinished(e->{
            if(i.hasNext()){ // Выводим четверостишье за четверостишьем.
                text.setText(i.next());
                fadeBack.play();
            }
        });

        fadeBack.play();

    }
}
