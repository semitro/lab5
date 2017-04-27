package vt.smt.Client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import vt.smt.Data.Toy;

import java.nio.channels.AlreadyConnectedException;
import java.util.LinkedList;

import vt.smt.Commands.*;
/**
 * Created by semitro on 17.04.17.
 */
public class Sender {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static Sender instance;
    private String host;
    private int port;
    private Sender(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        Thread t = new Thread(()->{
            while (true) {
                try {
                    retryConect();
                    System.out.println("Коннект восстановлен.");
                    return;
                }catch (IOException exc){
                    try {
                        Thread.currentThread().sleep(120);
                    }catch (InterruptedException pppp){

                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
    public static Sender getInstance() throws IOException{
        if(instance == null)
            instance = new Sender("127.0.0.1",2552);
        return instance;
    }
    private void retryConect() throws  IOException{
        if(socket.isConnected())
            return;
        socket = new Socket(host,port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
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
    public ServerAnswer nextCommand(){
        try {
            return (ServerAnswer)in.readObject();
        }catch (Exception e){
            System.out.println("Сервер попытался прислать фигню");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

}
