package vt.smt.Client;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import vt.smt.Commands.*;
/**
 * Created by semitro on 17.04.17.
 */
public class Sender {
    private Socket socket;
    private ObjectOutputStream out;
    public Sender(String host, int port){
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /** Для общения с сервером используется паттерн "Команда"
     *  Если Вы хотите отправить на сервер данные,
     *  должна существовать команда, принимающая в конструктор необходимые данные
     *  @param command <b> команда для отправки на сервер</b>
     */
    public void sendCommand(ServerCommand command) throws IOException{
        out.writeObject(command);
        out.flush();
    }
}
