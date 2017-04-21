package vt.smt.Client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import vt.smt.Toy;
import java.util.LinkedList;

import vt.smt.Commands.*;
/**
 * Created by semitro on 17.04.17.
 */
public class Sender {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public Sender(String host, int port){
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /** Для общения с сервером используется паттерн "Команда" (ну, почти)
     *  Если Вы хотите отправить на сервер данные,
     *  должна существовать команда, принимающая в конструктор необходимые данные
     *  @param command <b> команда для отправки на сервер</b>
     */
    public void sendCommand(ServerCommand command) throws IOException{
        out.writeObject(command);
        out.flush();
    }
    LinkedList<Toy> getBearsFromServer(){
        try {
            sendCommand(new GetBearsFromServer());
            Thread.currentThread().sleep(2000);
            return (LinkedList<Toy>)in.readObject();
        }catch (IOException e){
            System.out.println("Неудачная попытка получения всех медведей Sender::getBears");
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println("Sender:getBearsFromServer(): сервер прислал не тот тип данных");
            System.out.println(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    };
}
