package vt.smt.Client;

import vt.smt.Server.Commands.GetBearsFromServer;
import vt.smt.Toy;

import java.io.IOException;

/**
 * Created by semitro on 18.04.17.
 */
public class TestRunner {
    public static void main(String argv[]){
        Sender sender = new Sender("127.0.0.1",2552);
        Toy toy = new Toy("Hello");
        try {
            while (true)
            sender.sendCommand(new GetBearsFromServer());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
