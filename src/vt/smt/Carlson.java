package vt.smt;
/*
* Карлсон - синглтон в самом расцвете сил
* При создании можно передать дом
*
*/
class Carlson extends Human{
    private static Carlson instance;// Экземпляр Карсона
    private Fan fan; // Экзмепляр пропеллера
    static Carlson get() { // Может быть создан
        if(instance==null)// Лишь однажды
            instance = new Carlson(new Home());
        return instance;
    }
    static void create(Home home) throws Exception{
        if(instance == null)
            instance = new Carlson(home);
        else
            throw new Exception("Карсон уже создан");
    }
    private Carlson(Home home){
        super(home);
        mood = Mood.GOOD;
        name = "Karlson";
        System.out.println("Karlson was born");
        fan = new Fan();
    }
    @Override
    public void cleanUp(Cleanable cleanable) {
        System.out.println("Я же Карлсон, какой убираться?");
    }
    public void fly(){
        fan.startRotate();
        class ThreadChecker{
            public void check(){
//                for(int i = 0; i< 1000;i++)
//                    System.out.print(" " + i);
            }
        }
        ThreadChecker checker = new ThreadChecker();
        checker.check();

    }
    private class Fan{
        private boolean isOn;
        private Fan(){
            System.out.println("Fan is created");
            isOn = true;
        }
        public boolean isOn(){
            return isOn;
        }
        public void startRotate(){
            isOn = true;
            System.out.println("Karlson's fan is on");

        }
        public void stopRotate(){
            isOn = false;
            System.out.println("Karlson's fan is off");
        }

    }
}
