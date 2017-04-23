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
    public static void startConsoleMode(){
        ObjectMapper jMapper = new ObjectMapper();
        java.util.Scanner sc = new Scanner(System.in);
        String str = new String();
        boolean commandFind = false;
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

                            System.out.println("reorder: выполнено");
                            commandFind = true;
                        }
                        break;
                        case "clear": {
                            System.out.println("clear: выполнено");
                            commandFind = true;
                        }
                        break;
                        case "sort": {
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
                            System.out.println("Switch: выполнено");
                            continue;
                        }

                        else
                        if (str.contains("mother")){
                            System.out.println("Switch: выполнено");// У Мамы и Малыша
                            continue;
                        }
                        else
                        if (str.contains("babyk")) {

                            System.out.println("Switch: выполнено");
                            continue;
                        }// Один и тот же дом
                        System.out.println("Switch.. куда?");
                    }
                    else
                    if(str.contains("show")) {
                        commandFind = true;

                    } else
                    if (str.contains("insert")){
                        str = str.replace("insert ", "");
                        try {
                            String temp[] = str.substring(0,5).split(" ");
                            temp[1] = str.substring(str.indexOf(" "),str.length());
                            commandFind = true;
                            Toy toyParse = jMapper.readValue(temp[1], Toy.class);
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

                            System.out.println("add_if_max: выполнено");
                        } catch (Exception e) {
                            System.out.println("Скорее всего, вы ввели неправильный jSon-код");
                        }
                    }
                    if(!commandFind)
                        System.out.println("Запрашиваемая команда не существует!");
                }


    }
    public static void startGUI(){
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
