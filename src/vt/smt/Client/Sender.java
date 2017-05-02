package vt.smt.Client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import vt.smt.Data.Toy;

import java.nio.channels.AlreadyBoundException;
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
    private static String host_ = "127.0.0.1";
    private static int port_ = 2552;
    private Sender(String host, int port) throws IOException {
        Thread t = new Thread(()->{
            while (true) {
                try {
                    retryConect();
                    System.out.println("Коннект восстановлен.");
                    sendCommand(new GetAllBears());
                    return;
                }catch (IOException exc){
                    try {
                        Thread.currentThread().sleep(880);
                    }catch (InterruptedException pppp){

                    }
                }
            }
        });
        t.setDaemon(true);
        host_ = host;
        port_ = port;
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        }catch (IOException e){
            t.start();
        }
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

    }
    public static void initAddr(String host,int port) throws AlreadyConnectedException{
        if(instance != null)
            throw new AlreadyBoundException();
       host_ = host;
       port_ = port;
    }
    public static Sender getInstance() throws IOException{
        if(instance == null)
            instance = new Sender(host_,port_);
        return instance;
    }
    private void retryConect() throws  IOException{
        if(socket.isConnected())
            return;
        socket = new Socket(host_,port_);
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
