package vt.smt.GUI;


import javafx.animation.ScaleTransition;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import vt.smt.Client.Sender;
import vt.smt.Commands.CommitChanges;
import vt.smt.Commands.SortBears;

import java.io.IOException;

public class ServerButton extends ImageView {
    ContextMenu  menu = new ContextMenu();
    Image img;

    ServerButton(){
        super();
        img = new Image(getClass().getResourceAsStream("img/serverButtonSmall.png"));
        this.setImage(img);

        MenuItem sortBears = new MenuItem("Отсортировать медведей");
        sortBears.setOnAction(e->{
            try {
                Sender.getInstance().sendCommand(new SortBears(null));
            }catch (IOException exception){
                System.out.println("Не удалось отправить просьбу о сортировке");
                exception.printStackTrace();
            }
        });

        menu.getItems().add(sortBears);
        MenuItem commitChanges = new MenuItem("Зафиксировать изменения в базе данных");
        String style = //"-fx-background-color: light-blue;" +
                "-fx-font-size: 10pt;" +
                "" +
                "";
        menu.getItems().add(commitChanges);
        menu.setStyle(style);
        commitChanges.setOnAction(e->{
            try {
                Sender.getInstance().sendCommand(new CommitChanges(null));
            }catch (IOException exception){
                System.out.println("Не удалось отправить запрос на коммит");
                exception.printStackTrace();
            }
        });
        setOnMouseClicked(e->{
            menu.show(this,e.getScreenX()-180,e.getScreenY()+5);
        });
        MenuItem license = new MenuItem("Я прочёл лицензию");
        license.setOnAction(e->FallingStar.win());
        menu.getItems().add(license);

        initEffects();
    }

    private void initEffects(){
        ScaleTransition scale = new ScaleTransition(Duration.valueOf("0.25s"),this);
        scale.setToX(1.3); scale.setToY(1.3);
        scale.setAutoReverse(true);
        this.setOnMouseEntered(e->scale.play());

        ScaleTransition reScale = new ScaleTransition(Duration.millis(250),this);
        reScale.setToY(1); reScale.setToX(1);
        this.setOnMouseExited(e->reScale.play());
    }
}
