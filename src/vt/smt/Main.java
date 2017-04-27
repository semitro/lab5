package vt.smt;

public class Main {

    public static void main(String[] args) throws Exception{
        Plot plot = new Plot();
        if((args.length > 0 && args[0].contains("console")))
            plot.startConsoleMode();
        else
            plot.startGUI();
    }
}
