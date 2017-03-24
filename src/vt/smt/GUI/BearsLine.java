package vt.smt.GUI;

/**
 * Ты был создан, чтобы восстановить равновесие силы, а обернуть его в прах
 * Ты должен быть одолеть ситхов, а не примкнуть к ним!
 * В общем, как понятно из контекста, этот класс - прослойска для взаимодействия Графического
 *  отображения (class Bear) и непосредственно управляемой коллекции
 */
import com.sun.istack.internal.NotNull;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import vt.smt.Home;
import vt.smt.PhysicalObject;


import java.io.File;
import java.util.List;


public class BearsLine {
    // Зависим от абстракции collection
    private List<PhysicalObject> collection;
    private HBox mainLine;  // Полоска медведей
    private HBox outerLine; // В нём будет ездить основная полоска. Зачем? Для корректой обработки жестов
                            // Особенно, при выезде за края
    private static String collectionXMLFile = System.getProperty("user.dir") +
                                    File.separator + "things" + File.separator + "BabykAndMotherThings.xml";
    // По-умолчанию - загружаем коллекцию из файла
   public BearsLine(){
        Home loader = new Home(); // Возможно, следовало бы добавить нечто вроде util.
        loader.loadThingsFromFile(collectionXMLFile);
        collection = loader.getThings();
       mainLine = new HBox();
       mainLine.setSpacing(20);
       outerLine = new HBox();
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
       MouseDraggedWrapper mouseDragged = new MouseDraggedWrapper();
       // Ловим жест
       outerLine.setOnMousePressed(start->{
           mouseDragged.value = start.getSceneX();
       });
       outerLine.setOnMouseReleased(end->{
           translateTransition.setFromX(mainLine.getTranslateX());
           translateTransition.setToX(
                   mainLine.getTranslateX() + end.getSceneX() - mouseDragged.value);
           translateTransition.play();

       });
       mainLine.getChildren().add(new Bear());
       outerLine.getChildren().add(mainLine);

   }

    // Интерфейс для графики
    public Node getNode(){
        return outerLine;
    }
    // Отображение коллекции в видимых медведей
    public void refreshVisible() {
        System.out.println(collection.size() + " elements");
        for (int i = 0; i < collection.size(); i++) {
            mainLine.getChildren().add(new Bear());
        }
    }

}
