package vt.smt;

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

    }
}
