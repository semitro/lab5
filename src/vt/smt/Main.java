package vt.smt;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.File;
import java.util.Scanner;
public class Main {


    public static void main(String[] args){
        // Загрузка сюжета в консольном или не консольном режиме
        Plot plot = new Plot();
        if(args.length > 0 && args[0].contains("console"))
            plot.start(true);
        else
            plot.start(false);

    }
}
