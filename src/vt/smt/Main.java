package vt.smt;

import sun.security.ssl.Debug;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


            Home home = new Home(); // У Мамы и Малыша один дом
            Mother mother = new Mother(home);
            Babyk babyk = new Babyk(home);
            home = new Home();
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

            String str = new String();
            Home currentHome = new Home();
            java.util.Scanner sc = new Scanner(System.in);
            System.out.println("Интерактивный режим.");
            mother.getHome().clear();
        try {
            XmlParser parser = new XmlParser("src/vt/smt/PhysicalObjects.xml");
            while (parser.hasNext())
                System.out.println("Parser: " + parser.getNext().getWeight());
            while (str.equals("exit") == false) {
                str = sc.next();
                switch (str) {
                    case "reorder":
                        currentHome.reorder();
                        break;
                    case "clear":
                        currentHome.clear();
                        break;
                    default:
                        break;
                }

                if (str.contains("switch")) {
                    if (str.contains("carlson")) currentHome = carlson.getHome();
                    if (str.contains("mother")) currentHome = mother.getHome();
                    if (str.contains("babyk")) currentHome = babyk.getHome();
                }
            }
        }catch (FileNotFoundException e){
            Debug.println("Xml: ", "File not found");
        }

    }
}
