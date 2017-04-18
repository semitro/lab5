package vt.smt.Server;

import java.io.IOException;

/**
 * Created by semitro on 18.04.17.
 */
public class Main {
    public static void main(String argv[]){
        Receiver reciever;
        try {
            reciever = new Receiver(2055);
        }catch (IOException e){
            e.printStackTrace();
        }
       // server.nextCommand()
    }
}
