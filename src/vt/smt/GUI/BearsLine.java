package vt.smt.GUI;

/**
 * Ты был создан, чтобы восстановить равновесие силы, а обернуть его в прах
 * Ты должен быть одолеть ситхов, а не примкнуть к ним!
 * В общем, как понятно из контекста, этот класс - прослойска для взаимодействия Графического
 *  отображения (class Bear) и непосредственно управляемой коллекции
 */
import com.sun.istack.internal.NotNull;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import vt.smt.Home;
import vt.smt.PhysicalObject;
import vt.smt.Toy;


import java.io.File;
import java.util.List;


public class BearsLine extends HBox{
    // Зависим от абстракции collection
    private List<PhysicalObject> collection;
    private HBox mainLine;  // Полоска медведей
    private Titleses titleses; // Стих после удаления всего
    protected Alert confirmExit; // Сохранять ли файлы при выходе?
    private static String collectionXMLFile = System.getProperty("user.dir") +
                                    File.separator + "things" + File.separator + "BabykAndMotherThings.xml";
    // По-умолчанию - загружаем коллекцию из файла
    public BearsLine(){
        Home loader = new Home(); // Возможно, следовало бы добавить нечто вроде util.
       loader.loadThingsFromFile(collectionXMLFile);
       collection = loader.getThings();
        titleses = new Titleses();
        mainLine = new HBox();
       mainLine.setSpacing(20);
       refreshVisible();

       // Анимация для медведиков
       TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1800));
       translateTransition.setCycleCount(1);
       translateTransition.setAutoReverse(true);
       translateTransition.setNode(mainLine);

       // Яков рассказывал, как его друга на экзамене попросили доказать, что замыкание замкнуто
       class MouseDraggedWrapper{
           public double value;
       }
        MotionBlur blur = new MotionBlur();

        MouseDraggedWrapper mouseDragged = new MouseDraggedWrapper();
       // Ловим жест
       this.setOnMousePressed(start->{
           mouseDragged.value = start.getSceneX();
           mainLine.setEffect(blur);
           blur.setAngle(45);
           blur.setRadius(3);
       });
       this.setOnMouseReleased(end->{
           translateTransition.setFromX(mainLine.getTranslateX());
           translateTransition.setToX(
                   mainLine.getTranslateX() + end.getSceneX() - mouseDragged.value);
           translateTransition.play();
           translateTransition.setOnFinished(f->{
           });
           blur.setRadius(0);
           blur.setAngle(0);
       });
       this.getChildren().add(mainLine);

   }
    // Отображение коллекции в видимых медведей
    public void refreshVisible() {
        mainLine.getChildren().clear();

        for (int i = 0; i < collection.size(); i++) {
            Bear bear = new Bear(); // Спасибо огромное составителям JavaFX за возможность присудить ID!
            bear.setId(Integer.toString(i));
            bear.loadImgFromFile(System.getProperty("user.dir") +
                    File.separator + "things" + File.separator +
                    Integer.toString(collection.get(i).hashCode()));

            mainLine.getChildren().add(bear);
            if(!collection.get(i).isClean()){
                InnerShadow is = new InnerShadow();
                is.setRadius(20);
                is.setChoke(0.4);
                is.setBlurType(BlurType.GAUSSIAN);
                bear.setEffect(is);
            }

        }
    }
    public void insertElemtnt(int index, Toy element){
        if(index > collection.size())
            collection.add(element);
                else
            collection.add(index, element);
        refreshVisible();
    }
    public void removeElement(String index){
        removeElement(Integer.valueOf(index));
    }
    public void removeElement(int index){
        collection.remove(index);
        // Анимация исчезновения медведя
        FadeTransition fd = new FadeTransition(Duration.millis(200));
        fd.setNode(mainLine.getChildren().get(index));
        fd.setFromValue(1);
        fd.setToValue(0);
        fd.play();
        fd.setOnFinished(e->refreshVisible());
        if(collection.isEmpty()){
            this.getChildren().add(titleses);
            titleses.start(300,600); // Дописать
        }

    }
    public Toy getInfoAbout(int index){
        return index < collection.size() ? (Toy)collection.get(index) : null;
    }
}
