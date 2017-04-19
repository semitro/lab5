package vt.smt.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by semitro on 19.04.17.
 */
public class Client {
    private Socket socket;
    public Client(Socket socket){
        this.socket = socket;
        try {
            oos = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            System.out.println("Беда в конструкторе: ");
            System.out.println(e.getMessage());
        }
    }
    public Socket getSocket(){return socket;}
    private ObjectInputStream oos;
    public ObjectInputStream getObjectInputStream(){
        return oos;
    }
}
