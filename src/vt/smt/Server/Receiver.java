package vt.smt.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
/**
 * Created by semitro on 18.04.17.
 */
public class Receiver {
    ServerSocket socket;
    // Потокобезопасной коллекция б
    // ыть не обязана, поскольку список меняется лишь из одного места
//    List<Socket> clients;
//    List<ObjectInputStream> inputStreams;
    public Receiver(int port) throws IOException{
        socket = new ServerSocket(port);
//        clients = new LinkedList<>();
//        inputStreams = new LinkedList<>();
        Thread t = new Thread(this::listen);
        t.start();
    }
    public Command nextCommand(){
        return new SaveOnServer();
    }
    // Ждём новых клиентов
    private Socket client;
    private void listen(){
        try {
            client = socket.accept();
            System.out.println("Подключился.");
        }catch (IOException e){
            e.printStackTrace();
        }
//        while (true){
//            try {
//                Socket newClient = socket.accept();
//                clients.add(newClient);
//                System.out.println("Клиент " + clients.size() +
//                        " " + socket.toString() " подключён");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }
}
