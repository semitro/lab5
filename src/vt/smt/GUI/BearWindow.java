package vt.smt.GUI;

import com.github.sarxos.webcam.Webcam;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javax.imageio.ImageIO;
import java.io.File;
import vt.smt.Data.Toy;
/**
 * Абстрактное окно взаимодействия с медведями (в выпадающем списке вызваются реализации)
 */
public abstract class BearWindow {
    protected Stage stage;
    protected Pane pane;
    protected Button imageAvatar;
    protected Button addButton;
    protected ImageView imageView;
    protected VBox rightBox;
    protected HBox centerBox;
    protected CheckBox isCleanBox;
    protected TextField weightInput;
    protected TextField nameInput;
    protected Image defaultImage;
    // Нужно знать, какой медведь нас вызвал
    protected Bear caller;
    // Для синхронизации алгоритмом CAS
    protected Toy cas_caller;
    BearWindow(Bear caller){
        this.caller = caller;
        this.cas_caller = new Toy(caller.getInfo());
        camSnapshot = new Thread(new Runnable() {
            @Override
            public void run() {
                // Фотография с камеры устанавливается на кнопку
                camSnapshot();
                return;
            }
        });
        stage = new Stage();
        pane = new Pane();
        rightBox = new VBox();
        centerBox = new HBox();

        imageAvatar = new Button();
        defaultImage = new Image(getClass().getResourceAsStream("img/defaultBear.png"));
        imageView = new ImageView(defaultImage);

        imageAvatar.setGraphic(imageView);
        imageAvatar.setOnAction(e->{
                    // Адекватен ли этот код? + повторение в начале конструктора.
                    // Ну, зато работает)0
                    if(!camSnapshot.isAlive())
                        camSnapshot = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Фотография с камеры устанавливается на кнопку
                                camSnapshot();
                            }
                        });
                    camSnapshot.start();
                }
        );

        pane.getChildren().add(imageAvatar);
        Label topLabel = new Label("Я не знал, что здесь написать..");
        rightBox.getChildren().add(topLabel);
        Language.addListener(new LabelAdapter(topLabel,"BearsWindow.IDidntKnowWhatToWrite"));

        nameInput = new TextField();
        rightBox.getChildren().add(nameInput);

        weightInput = new TextField();
        weightInput.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter()));
        rightBox.getChildren().add(weightInput);
        isCleanBox = new CheckBox("Чистый");
        Language.addListener(new CheckBoxAdapter(isCleanBox,"BearsWindow.isClean"));

        rightBox.getChildren().add(isCleanBox);
        addButton = new Button("Сотворить медведя!");
        addButton.setOnAction(e->{stage.close();});
        rightBox.getChildren().add(addButton);
        centerBox.getChildren().add(imageAvatar);
        centerBox.getChildren().add(rightBox);
        centerBox.setSpacing(8);
        rightBox.setSpacing(8);
        pane.getChildren().add(centerBox);
        stage.setTitle("Медведогенератор 2.0");
        stage.setScene(new Scene(pane,430,155));
        stage.setResizable(false);
        stage.setOnHiding(e->{imageView.setImage(defaultImage);});
        initTitles();
        loadInfo();
        initActions();
    }
    protected void loadInfo(){
        this.cas_caller = new Toy(caller.getInfo());
        isCleanBox.setSelected(cas_caller.isClean());
        nameInput.setText(cas_caller.getName());
        weightInput.setText(Double.toString(cas_caller.getWeight()));
    }
    abstract protected void initActions();
    abstract protected void initTitles();
    public void show(){
        loadInfo();
        stage.show();
    }

    // Алгоритм Compare and swap
    protected boolean CAS(){
        if(this.caller == null)
            return false;
        try {
            if (this.caller.getInfo().getName().equals(cas_caller.getName()) &&
                    this.caller.getInfo().getWeight() == cas_caller.getWeight() &&
                    this.caller.getInfo().isClean() == cas_caller.isClean()
                    )
                return true;
        }
        catch(NullPointerException np){
            return false;
        }
        return false;
    }
    // Класс, поставляемый библиотекой
    private Webcam webcam;
    // Довольно долго записать в файл и получить изображение, чтобы окно не висло, сделаем отдельный поток
    private Thread camSnapshot;
    // Вызывается потоком camSnapshot
    private void camSnapshot(){
        try {
            if(webcam == null)
                webcam = Webcam.getDefault();
            webcam.open();

            ImageIO.write(webcam.getImage(), "PNG", new File(
                            System.getProperty("user.dir") + File.separator + "things" + File.separator + "temp"
                    )
            );
            webcam.close();
            imageView.setImage(new Image("file:" +
                    System.getProperty("user.dir") + File.separator + "things" + File.separator + "temp" )
            );
        }
        catch (java.io.IOException e){
            System.out.println("Беда с вебкамерой.");
            return;
        }

    }
    /**
        Камера сохраняет в темп-файл.
        При закрытии окна нужно определиться,
        к какому медведю относится данное изображение
     */
    protected void renameImage(String newName){
        File file = new File(System.getProperty("user.dir") + File.separator + "things" + File.separator + "temp");
        if(file.exists())
            file.renameTo(new File(System.getProperty("user.dir") + File.separator + "things" + File.separator + newName));
    }
}
