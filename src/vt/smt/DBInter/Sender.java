package vt.smt.DBInter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

/**
 * Created by semitro on 17.04.17.
 */
public class Sender {
    private Socket socket;
    private ObjectOutputStream out;
    Sender(){
        try {
            socket = new Socket("127.0.0.1", 2055);
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void send(Object obj) throws IOException{
        out.writeObject(obj);
    }
//    public void send(List objects) throws IOException{
//       objects.forEach(e-> {
//           try { // Как реализовать это нормально?
//               send(e);
//           }catch (IOException ex){
//
//           }
//       });


}
