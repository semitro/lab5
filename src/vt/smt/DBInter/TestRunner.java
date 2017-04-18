package vt.smt.DBInter;

import vt.smt.Toy;

import java.io.IOException;

/**
 * Created by semitro on 18.04.17.
 */
public class TestRunner {
    public static void main(String argv[]){
        Sender sender = new Sender();
        Toy toy = new Toy("Hello");
        try {
            sender.send(toy);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
