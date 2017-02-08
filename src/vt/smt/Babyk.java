package vt.smt;

/**
 * Created by semitro on 03.12.16.
 */
class Babyk extends Human{
    public Babyk(Home home){
        super(home);
        name = "Малыш";
        mood = Mood.GOOD;
        System.out.println(name + " was born");
        java.util.Random rand = new java.util.Random();
        minAttempts = rand.nextInt(7)+1;
    }
    @Override
    public void cleanUp(Cleanable toClean){
        // Если убрать надо дом Карслона
        if(Carlson.get().getHome().equals(toClean)){
            System.out.println("Малыш: О, у Карлсона весело. Сейчас уберусь.");
            try {
                toClean.cleanUp();
            }catch(AlreadyCleanException e){
                System.out.println("Малыш: исключительно не желаю убирать чистое!");
            }

            System.out.println("Clean is down.");
            return;
        }
        if(--minAttempts <= 0){
            java.util.Random r = new java.util.Random();
            minAttempts = r.nextInt(5);
            System.out.println("Малыш: Ну, вот я и убираюсь.");
            try {
                toClean.cleanUp();
            }catch (Exception e){
                System.out.println("Малыш: исключительно не хочу убирать убранное!");
            }
            System.out.println("Clean is down.");

        }
        else
            System.out.println("Малыш: Не хочу убираться!");


    }
    // Сколько малышу нужно напоминать, чтобы он убрался
    private int minAttempts;
}