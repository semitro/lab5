package vt.smt;

public class Main {

    public static void main(String[] args) throws Exception{
        // Загрузка сюжета в консольном или не консольном режиме
        Plot plot = new Plot();
        if(args.length > 0 && args[0].contains("console"))
            plot.start(true);
        else
            plot.start(false);

    }
}
