package vt.smt;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import sun.org.mozilla.javascript.json.JsonParser;
import sun.security.ssl.Debug;

import java.io.File;
import java.util.Scanner;
import org.codehaus.jackson.*;
// Подумать, как правильнее работать с файлом
public class Main {

    static final String fileMotherAndBabyksThings =
            System.getProperty("user.dir") + File.separator + "things" + File.separator + "BabykAndMotherThings.xml";
    static       String fileCarlsonsThings = System.getProperty("user.dir") + File.separator + "things" + File.separator + "CarlsonsThings.xml";
    static final String interactiveModeHelp = "exit  - выход\n" +
            "switch carlson/mother/babyk - смена дома, чьими вещами сейчас управляем\n" +
            "clear - очистка вещей текущего дома\n" +
            "reorder - реверс вещей текущего дома\n" +
            "add_if_max {\"weight\":double,\"name\":\"String\",\"isCleaning\":bool} - добавить, если тяжелейший\n" +
            "insert {index} {element} - аккуратно и точно вставить в коллекцию.";


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
        ObjectMapper jMapper = new ObjectMapper();

            String str = new String();
            Home currentHome = carlson.getHome();
            java.util.Scanner sc = new Scanner(System.in);

            System.out.println("Интерактивный режим.\nДом Карлсона \nВводи ? для посказки\n");
        try { // Считаю это решение грамотным. Коллекции должны быть сохранены гарантированно.
            while (str.equals("exit") == false) {
                str = sc.nextLine();
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
                if (str.contains("insert")){
                    str = str.replace("insert ", "");
                    try {
                        String temp[] = str.split(" ");
                        if(temp.length != 2){
                            System.out.println("Некорректная команда");
                            break;
                        }
                        Toy toyParse = jMapper.readValue(temp[1], Toy.class);
                        currentHome.insert(Integer.parseInt(temp[0])-1,toyParse);
                    }
                    catch (Exception e) {
                        System.out.println("Скорее всего, вы ввели неправильный jSon-код");
                    }
                }
                else
                    if(str.contains("add_if_max")){
                        str = str.replace("add_if_max ", "");
                        try {
                            currentHome.addIfMax(jMapper.readValue(str, Toy.class));
                        } catch (Exception e) {
                            System.out.println("Скорее всего, вы ввели неправильный jSon-код");
                        }
                    }
            }
        }
        finally {
            carlson.getHome().saveThingsToFile(fileCarlsonsThings);
            babyk.getHome().saveThingsToFile(fileMotherAndBabyksThings);
        }


    }
}
