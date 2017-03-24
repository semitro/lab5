package vt.smt.GUI;


import com.github.sarxos.webcam.Webcam;
import com.sun.javafx.iio.common.ImageTools;
import com.sun.javafx.tk.*;
import com.sun.javafx.tk.Toolkit;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sun.text.normalizer.UTF16;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.net.URI;

/**
 * Окно генерации новых медведей
 */
public class BearGenerator {
    Stage stage;
    Pane pane;
    Button imageAvatar;
    Button addButton;
    ImageView imageView;


    VBox rightBox;
    HBox centerBox;
    CheckBox isCleanBox;
    TextField nameInput;
    Image defaultImage;
    BearGenerator(){
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
        rightBox.getChildren().add(new Label("Ggg"));

        nameInput = new TextField("Имя медведя");
        rightBox.getChildren().add(nameInput);

        isCleanBox = new CheckBox("Чистый");
        rightBox.getChildren().add(isCleanBox);

        addButton = new Button("Сотворить медведя");
        addButton.setOnAction(e->{
            stage.close();

        });
        rightBox.getChildren().add(addButton);

        centerBox.getChildren().add(imageAvatar);
        centerBox.getChildren().add(rightBox);
        centerBox.setSpacing(8);
        rightBox.setSpacing(8);

       // centerBox.setMaxSize(centerBox.getWidth(),centerBox.getMaxHeight());
        pane.getChildren().add(centerBox);
        stage.setTitle("Медведогенератор");
        stage.setScene(new Scene(pane,380,120));
        stage.setOnHiding(e->{
            imageView.setImage(defaultImage);

        });
    }
    public void show(){
        stage.show();
        stage.toFront();

    }
    // Класс, поставляемый библиотекой
    private Webcam webcam;
    // Довольно долго записать в файл и получить изображение, чтобы окно не висло, сделаем отдельный поток
    private Thread camSnapshot;
    // Вызывается потоком camSnapshot
    private void camSnapshot(){
        try {

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
