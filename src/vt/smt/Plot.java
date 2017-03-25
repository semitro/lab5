package vt.smt;

/**
 * Created by semitro on 23.03.17.
 */

import javafx.application.Application;
import org.codehaus.jackson.map.ObjectMapper;
import vt.smt.GUI.MainGUI;

import java.io.File;
import java.util.Scanner;

/**
 Сюжет лабораторной из текста
 */
public class Plot {
    private Mother mother;
    private Babyk babyk;
    private Carlson carlson;
    Plot(){
        Home home = new Home(); // У Мамы и Малыша один дом
        home.loadThingsFromFile(fileMotherAndBabyksThings); // Подгрузка коллекции из файла
        mother = new Mother(home);
        babyk = new Babyk(home);
        home = new Home();
        home.loadThingsFromFile(fileCarlsonsThings);
        try {
            // Давний костыль ещё из первой лабы
            Carlson.create(home);
        }catch (Exception e){
            System.out.println("Карлсон не роился :(");
        }
        carlson = Carlson.get();
    };
    void start(boolean consoleMode){
        /// Сюжет из лабораторной работы номер давно
        while (!babyk.getHome().isClean())
            mother.remind(babyk);
        babyk.cleanUp(carlson.getHome());
        //Запуск консольного мода из первой весенней лабы при необходимости
        if(consoleMode)
            startConsoleMode();
        else
            startGUI();
    }
    private void startConsoleMode(){
        ObjectMapper jMapper = new ObjectMapper();
        java.util.Scanner sc = new Scanner(System.in);

        String str = new String();
        Home currentHome = carlson.getHome();
        boolean commandFind = false;
        try {
        while (str.equals("exit") == false) {
                    commandFind = false;
                    str = sc.nextLine();
                    if(str.contains("exit"))
                        commandFind = true;
                    switch (str) {
                        case "?": {
                            System.out.println(interactiveModeHelp);
                            commandFind = true;
                        }
                        break; // Не было написано break;
                        case "reorder": {
                            currentHome.reorder();
                            System.out.println("reorder: выполнено");
                            commandFind = true;
                        }
                        break;
                        case "clear": {
                            currentHome.clear();
                            System.out.println("clear: выполнено");
                            commandFind = true;
                        }
                        break;
                        case "sort": {
                            currentHome.sortThings();
                            System.out.println("sort: выполнено");
                            commandFind = true;
                        }
                        break;
                        default:
                            break;
                    }
                    if(commandFind)
                        continue;
                    // Переключатель текущей коллекции
                    if (str.contains("switch")) {
                        commandFind = true;

                        if (str.contains("carlson")) {
                            currentHome = carlson.getHome();
                            System.out.println("Switch: выполнено");
                            continue;
                        }

                        else
                        if (str.contains("mother")){
                            currentHome = mother.getHome();
                            System.out.println("Switch: выполнено");// У Мамы и Малыша
                            continue;
                        }
                        else
                        if (str.contains("babyk")) {
                            currentHome = babyk.getHome();
                            System.out.println("Switch: выполнено");
                            continue;
                        }// Один и тот же дом
                        System.out.println("Switch.. куда?");
                    }
                    else
                    if(str.contains("show")) {
                        commandFind = true;
                        currentHome.showCollection();
                    } else
                    if (str.contains("insert")){
                        str = str.replace("insert ", "");
                        try {
                            String temp[] = str.substring(0,5).split(" ");
                            temp[1] = str.substring(str.indexOf(" "),str.length());
                            commandFind = true;
                            Toy toyParse = jMapper.readValue(temp[1], Toy.class);
                            currentHome.insert(Integer.parseInt(temp[0])-1,toyParse);
                            System.out.println("Insert: выполнено");
                        }
                        catch (IndexOutOfBoundsException e){
                            System.out.println("Указанная позиция несуществует");
                        }
                        catch (Exception e) {
                            System.out.println("Скорее всего, вы ввели неправильный jSon-код");
                            System.out.println(
                                    "Example: insert 2 {\"weight\":25.5,\"name\":\"Iwan\",\"isCleaning\":true}");
                        }
                    }
                    else
                    if(str.contains("add_if_max")){
                        str = str.replace("add_if_max ", "");
                        try {
                            commandFind = true;
                            currentHome.addIfMax(jMapper.readValue(str, Toy.class));
                            System.out.println("add_if_max: выполнено");
                        } catch (Exception e) {
                            System.out.println("Скорее всего, вы ввели неправильный jSon-код");
                        }
                    }
                    if(!commandFind)
                        System.out.println("Запрашиваемая команда не существует!");
                }
        }
        finally {
            carlson.getHome().saveThingsToFile(fileCarlsonsThings);
            babyk.getHome().saveThingsToFile(fileMotherAndBabyksThings);
        }
    }
    private void startGUI(){
        Application.launch(MainGUI.class,null);
    }
    static final String fileMotherAndBabyksThings =
            System.getProperty("user.dir") +
                    File.separator + "things" + File.separator + "BabykAndMotherThings.xml";

    static       String fileCarlsonsThings = System.getProperty("user.dir") +
            File.separator + "things" + File.separator + "CarlsonsThings.xml";

    static final String interactiveModeHelp = " exit  - выход\n" +
            "switch carlson/mother/babyk - смена дома, чьими вещами сейчас управляем\n" +
            "clear - очистка вещей текущего дома\n" +
            "reorder - реверс вещей текущего дома\n" +
            "sort - сортировка коллекции\n" +
            "add_if_max {\"weight\":double,\"name\":\"String\",\"isCleaning\":bool} - добавить, если тяжелейший\n" +
            "insert {index} {element} - аккуратно и точно вставить в коллекцию.";

}
