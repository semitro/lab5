package vt.smt.GUI;

import com.github.sarxos.webcam.Webcam;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import javax.imageio.ImageIO;
import javax.xml.soap.Text;
import java.io.File;

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
    BearWindow(Bear caller){
        this.caller = caller;
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
        defaultImage = new Image(getClass().getResourceAsStream("defaultBear.png"));
        imageView = new ImageView(defaultImage);

        imageAvatar.setGraphic(imageView);
        imageAvatar.setOnAction(e-> {
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
        rightBox.getChildren().add(new Label("Я не знал, что здесь написать.."));
        nameInput = new TextField(
                caller.getInfo().getName()
        );
        rightBox.getChildren().add(nameInput);

        weightInput = new TextField("Вес");
//        weightInput.setTextFormatter(new TextFormatter<String>(
//               new DoubleStringConverter(),
//                "Вес",
//
//
//        )

        rightBox.getChildren().add(weightInput);
        isCleanBox = new CheckBox("Чистый");
        isCleanBox.setSelected(caller.getInfo().isClean());
        rightBox.getChildren().add(isCleanBox);
        addButton = new Button("Сотворить медведя!");
        addButton.setOnAction(e->{
            stage.close();

        });
        rightBox.getChildren().add(addButton);
        centerBox.getChildren().add(imageAvatar);
        centerBox.getChildren().add(rightBox);
        centerBox.setSpacing(8);
        rightBox.setSpacing(8);
        pane.getChildren().add(centerBox);
        stage.setTitle("Медведогенератор 2.0");
        stage.setScene(new Scene(pane,430,155));
        stage.setResizable(false);
        stage.setOnHiding(e->{
            imageView.setImage(defaultImage);
        });
        initTitles();
        initActions();
    }
    abstract void initActions();
    abstract void initTitles();
    public void show(){
        stage.show();
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
                            System.getProperty("user.dir") + File.separator + "things" + File.separator + "shot.png"
                    )
            );
            webcam.close();
            imageView.setImage(new Image("file:" +
                    System.getProperty("user.dir") + File.separator + "things" + File.separator + "shot.png" )
            );


        }
        catch (java.io.IOException e){
            System.out.println("Беда с вебкамерой.");
            return;
        }

    }
}
