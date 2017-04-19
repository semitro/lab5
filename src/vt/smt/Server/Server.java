package vt.smt.Server;

import org.bridj.ann.Runtime;
import vt.smt.Server.Commands.Command;
import vt.smt.Server.Commands.ServerCommand;

import java.io.IOException;

/**
 * Created by semitro on 18.04.17.
 */
public class Server {
    private Receiver receiver;
    public Server(){
        try {
            receiver = new Receiver(2552);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void start(){
        Thread t = Thread.currentThread();
        while(true){
            try {
                t.sleep(2000);

            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
            ServerCommand command = receiver.nextCommand();
            if(command != null)
                command.execute(this);
        }

    }

}
