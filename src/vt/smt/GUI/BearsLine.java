package vt.smt.GUI;

/**
 * Ты был создан, чтобы восстановить равновесие силы, а обернуть его в прах
 * Ты должен быть одолеть ситхов, а не примкнуть к ним!
 * В общем, как понятно из контекста, этот класс - прослойска для взаимодействия Графического
 *  отображения (class Bear) и непосредственно управляемой коллекции
 */
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import vt.smt.Client.InputCommandsHandler;
import vt.smt.Client.Sender;
import vt.smt.Commands.GetAllBears;
import vt.smt.Data.Toy;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
public class BearsLine extends HBox implements vt.smt.Client.Executor{
    // Зависим от абстракции collection
    private List<Toy> collection = new LinkedList<>();
    private HBox mainLine = new HBox();  // Полоска медведей
    private Titleses titleses = new Titleses(); // Стих после удаления всего
    private ProgressIndicator waitingIndicator;

    public BearsLine(){
        InputCommandsHandler.initExecutor(this);
        waitingIndicator = new ProgressIndicator();
        waitingIndicator.setTooltip(new Tooltip("Ожидание соединения с cервером.."));
        try {
            restoreConnection();
        }catch (Exception e){
            System.out.println("Всё плохо");
            System.out.println(e.getMessage());
        }


        // Анимация для медведиков
       TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1800));
       translateTransition.setCycleCount(1);
       translateTransition.setAutoReverse(true);
       translateTransition.setNode(mainLine);

       // Яков рассказывал, как его друга на экзамене попросили доказать, что замыкание замкнуто
       class MouseDraggedWrapper{
           public double value;
           public double value_y;
       }
        MotionBlur blur = new MotionBlur();

        MouseDraggedWrapper mouseDragged = new MouseDraggedWrapper();
       // Ловим жест
       this.setOnMousePressed(start->{
           mouseDragged.value = start.getSceneX();
           mouseDragged.value_y = start.getSceneY();
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
           //Удаление при выбрасывании вверх
           if(mouseDragged.value_y - end.getSceneY() > 50 &&
                   Math.abs(mouseDragged.value - end.getSceneX()) < 25) {
               double right = mainLine.getTranslateX();
             //  System.out.println(end.getSceneX());
               // Расчёт текущего медведя
               for (int i = 0; i < mainLine.getChildren().size(); i++) {
                   right += mainLine.getChildren().get(i).prefWidth(-1);
                   System.out.println(right);
                   if ( end.getSceneX() <= right) {
                      // System.out.println(i);
                       try {
                           Sender.getInstance().sendCommand(new vt.smt.Commands.RemoveBear(i));
                       }catch (IOException ioe){

                       }
                       break;
                   }
               }
           }
       });
        mainLine.setSpacing(20);
        this.getChildren().add(mainLine);
        InputCommandsHandler.start();
    }
    // Не сосвем работает
    private void restoreConnection() {
        mainLine.getChildren().clear();
        mainLine.getChildren().add(waitingIndicator);
        waitingIndicator.setTranslateX(257);
        waitingIndicator.setTranslateY(-50);
        waitingIndicator.setVisible(true);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                try {
                     Thread.currentThread().sleep(500);
                     Sender.getInstance().sendCommand(new GetAllBears());
                     System.out.println("Отправил запрос");
                   // Platform.runLater(()->{refreshVisible();});
                    return;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        t.setDaemon(true);
        t.start();

    }

    // Отображение коллекции в видимых медведей
    public void refreshVisible() {
        System.out.println(collection);
        System.out.println("Refer visible");
        mainLine.getChildren().clear();
        //
        for (int i = 0; i < collection.size(); i++) {
            Bear bear = new Bear(); // Спасибо огромное составителям JavaFX за возможность присудить ID!
            bear.setId(Integer.toString(i));
            bear.loadImgFromFile(System.getProperty("user.dir") +
                    File.separator + "things" + File.separator +
                    Integer.toString(collection.get(i).hashCode()));

            mainLine.getChildren().add(bear);
            dirtyBear(i);
        }
    }

    public void insertElemtnt(int index, Toy element){
        if(index > collection.size())
            collection.add(element);
                else
            collection.add(index, element);


        Platform.runLater(()->{
            if(index > collection.size())
                mainLine.getChildren().add(new Bear());
            else
                mainLine.getChildren().add(index,new Bear());
            for(int i = 0; i< mainLine.getChildren().size(); i++)
                mainLine.getChildren().get(i).setId(Integer.toString(i));
            dirtyBear(index);
        });
    }
    private Object monitorChange = new Object();
    public void removeElement(int index){
        synchronized (monitorChange) {
            collection.remove(index);
        }
        System.out.println("removeElement in BearsLine: Element number " + index + " was deleted");
        // Анимация исчезновения медведя
        Platform.runLater(()->{
            FadeTransition fd = new FadeTransition(Duration.millis(200));
            fd.setNode(mainLine.getChildren().get(index));
            fd.setFromValue(1);
            fd.setToValue(0);
            fd.play();
            fd.setOnFinished(e-> {
                mainLine.getChildren().remove(index);
                for(int i = 0; i< mainLine.getChildren().size(); i++)
                    mainLine.getChildren().get(i).setId(Integer.toString(i));
            });
            if(collection.isEmpty()){
                this.getChildren().add(titleses);
                titleses.start(100,200); // Дописать
            }
        });
    }
    public void changeElement(int index, Toy element){
        synchronized (monitorChange) {
            collection.set(index, element);
        }
        Platform.runLater(()->{
            dirtyBear(index);
        });
    }
    // Отобразить грязного ли медведя?
    private void dirtyBear(int index){
        if(!collection.get(index).isClean()){
            InnerShadow sh = new InnerShadow();
            sh.setRadius(20.0);
            sh.setChoke(0.3999);
            sh.setBlurType(BlurType.GAUSSIAN);
            sh.setInput(new Reflection());
            mainLine.getChildren().get(index).setEffect(sh);
        }
        else {
            mainLine.getChildren().get(index).setEffect(new Reflection());
        }

    }
    public void setCollection(LinkedList<Toy> list){
        synchronized (monitorChange) {
            collection = list;
        }
        Platform.runLater(()->refreshVisible());
        System.out.println("ПРивет");
    }

    public Toy getInfoAbout(int index){
        return index < collection.size() ? (Toy)collection.get(index) : null;
    }

}
