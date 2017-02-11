package vt.smt;
import sun.security.ssl.Debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// Подумать, как правильнее работать с файлом
public class Main {
    static final String fileMotherAndBabyksThings =
            System.getProperty("user.dir") + File.separator + "things" + File.separator + "BabykAndMotherThings.xml";
    static       String fileCarlsonsThings = System.getProperty("user.dir") + File.separator + "things" + File.separator + "CarlsonsThings.xml";
    static final String interactiveModeHelp = "exit  - выход\n" +
            "switch carlson/mother/babyk - смена дома, чьими вещами сейчас управляем\n" +
            "clear - очистка вещей текущего дома\n" +
            "reorder - реверс вещей текущего дома";


    public static void main(String[] args) throws Exception{


              Home home = new Home(); // У Мамы и Малыша один дом
        Mother mother = new Mother(home);
        Babyk babyk = new Babyk(home);

        home.loadThingsFromFile(fileMotherAndBabyksThings); // Подгрузка коллекции из файла
        home = new Home();
        // Вещи Карлсона загружаются из командной строки, поскольку Карлсон летает, где хочет
        // И берёт всё, что пожелает
        if(args.length >0 && args[0].length() > 0)
            fileCarlsonsThings = new String(System.getProperty("user.dir") + File.separator + "things" + File.separator + args[0]);
        home.loadThingsFromFile(fileCarlsonsThings );
        try {
            Carlson.create(home);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Carlson carlson = Carlson.get();

            Thread t = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            carlson.fly();
                        }
                    }
            );

            while (!babyk.getHome().isClean())
                mother.remind(babyk);

            t.start();
            babyk.cleanUp(carlson.getHome());
///////////////////////////////////////////////////////////////////lab5///////////////////////
            String str = new String();
            Home currentHome = carlson.getHome();
            java.util.Scanner sc = new Scanner(System.in);

            System.out.println("Интерактивный режим.\n Дом Карлсона \n Вводи ? для посказки\n");
            while (str.equals("exit") == false) {
                str = sc.next();
                switch (str) {
                    case "?":
                        System.out.println(interactiveModeHelp);
                    case "reorder":
                        currentHome.reorder();
                        break;
                    case "clear": {
                        currentHome.clear();
                    }
                        break;
                    default:
                        break;
                }
                // Переключатель текущей коллекции
                if (str.contains("switch")) {
                    if (str.contains("carlson")) currentHome = carlson.getHome();
                    if (str.contains("mother")) currentHome = mother.getHome(); // У Мамы и Малыша
                    if (str.contains("babyk")) currentHome = babyk.getHome(); // Один и тот же дом
                }
            }
        carlson.getHome().saveThingsToFile(fileCarlsonsThings);
        babyk.getHome().saveThingsToFile(fileMotherAndBabyksThings);

    }
}
